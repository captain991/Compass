package com.example.captain.compass.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captain.compass.ThreadPoolManager;
import com.example.captain.compass.adapter.FormListAdapter;
import com.example.captain.compass.LogTag;
import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;
import com.example.captain.compass.database.FormDb;
import com.example.captain.compass.event.AddFormEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class FormListFragment extends Fragment implements FormListAdapter.OnStateChangeListener {
    private static final String PARAM_LATITUDE = "PARAM_LATITUDE";
    private static final String PARAM_LONGITUDE = "PARAM_LONGITUDE";
    private static final String PARAM_STATE = "PARAM_STATE";

    private double latitude;
    private double longitude;
    private int state;
    private int s;

    private List<Form> forms = new ArrayList<>();
    private FormListAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private View rootView = null;

    public FormListFragment() {
        // Required empty public constructor
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public static FormListFragment newInstance(int state) {
        return newInstance(0, 0, state);
    }

    public static FormListFragment newInstance(double latitude, double longitude, int state) {
        FormListFragment fragment = new FormListFragment();
        Bundle args = new Bundle();
        args.putDouble(PARAM_LATITUDE, latitude);
        args.putDouble(PARAM_LONGITUDE, longitude);
        args.putInt(PARAM_STATE, state);
        fragment.setArguments(args);
        fragment.setS(state);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            latitude = getArguments().getDouble(PARAM_LATITUDE);
            longitude = getArguments().getDouble(PARAM_LONGITUDE);
            state = getArguments().getInt(PARAM_STATE);
        }

        adapter = new FormListAdapter(getActivity(), forms);
        adapter.setOnStateChangeListener(this);
        EventBus.getDefault().register(this);
        getForms();
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onCreate()");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_form_list, container, false);
            ButterKnife.bind(this, rootView);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        }
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onCreateView()");
        adapter.notifyDataSetChanged();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onAttach()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onStop()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onDetach()");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onDestroyView()");
    }

    public void getForms() {
        Observable.create(new Observable.OnSubscribe<List<Form>>() {
            @Override
            public void call(Subscriber<? super List<Form>> subscriber) {
                FormDb formDb = FormDb.getInstance();
                List<Form> forms = null;
                if (latitude != 0 && longitude != 0) {
                    forms = formDb.queryForms(latitude, longitude, state);
                } else {
                    forms = formDb.queryForms(state);
                }
                subscriber.onNext(forms);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Form>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LogTag.DB, "getForms " + e.toString());
                    }

                    @Override
                    public void onNext(List<Form> f) {
                        forms.clear();
                        forms.addAll(f);
                        if (f.size() > 0) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }


    @Override
    public void onStateChanged(Form form, int state, int position) {
        form.setState(state);
        forms.remove(position);
        adapter.notifyItemRemoved(position);
//        BroadcastUtil.addForm(getActivity(), form);
        EventBus.getDefault().post(new AddFormEvent(form));
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleAddForm(AddFormEvent event) {
        //不是本类型的form忽略掉
        Form form = event.getForm();
        if (form == null || form.getState() != state)
            return;
        forms.add(0, form);
        adapter.notifyItemInserted(0);
        ThreadPoolManager.fixedThreadPoolExecute(
                () -> FormDb.getInstance().updateFormState(form.getFormId(), form.getState()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onDestroy()");
    }
}
