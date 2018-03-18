package com.example.captain.compass.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.captain.compass.SampleItemDecoration;
import com.example.captain.compass.util.ThreadPoolManager;
import com.example.captain.compass.activity.BaseActivity;
import com.example.captain.compass.adapter.FormListAdapter;
import com.example.captain.compass.util.LogTag;
import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;
import com.example.captain.compass.database.FormDb;
import com.example.captain.compass.event.AddFormEvent;
import com.example.captain.compass.event.UpdateFormCountEvent;
import com.qmuiteam.qmui.util.QMUIDirection;
import com.qmuiteam.qmui.util.QMUIViewHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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

    private final List<Form> forms = new ArrayList<>();
    private FormListAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.layout_operate)
    LinearLayout llOperate;

    @BindView(R.id.btn_delete)
    Button btnDelete;

    @BindView(R.id.btn_send_sms)
    Button btnSendSms;

    @BindView(R.id.btn_mark_client)
    Button btnMarkClient;

    private View rootView = null;
    private BaseActivity activity = null;

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

        activity = (BaseActivity) getActivity();

        adapter = new FormListAdapter(getActivity(), forms);
        adapter.setOnStateChangeListener(this);
        adapter.setOnEditModeChangeListener(isEditMode -> {
            if (isEditMode) {
                QMUIViewHelper.slideIn(llOperate, 500, null, true, QMUIDirection.BOTTOM_TO_TOP);
            } else {
                QMUIViewHelper.slideOut(llOperate, 500, null, true, QMUIDirection.TOP_TO_BOTTOM);
            }
        });
        adapter.setOnOperateClickableChangeListener(isClickable -> {
            btnSendSms.setEnabled(isClickable);
            btnDelete.setEnabled(isClickable);
            btnMarkClient.setEnabled(isClickable);
        });
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
            recyclerView.addItemDecoration(new SampleItemDecoration());
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
        EventBus.getDefault().post(new UpdateFormCountEvent());
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

    @OnClick({R.id.btn_delete, R.id.btn_send_sms, R.id.btn_mark_client})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_delete:
                activity.showLoading("删除中...");
                Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        subscriber.onNext(FormDb.getInstance().deleteForms(adapter.getCheckedForms()));
                        subscriber.onCompleted();
                    }
                })
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Integer>() {
                            @Override
                            public void onCompleted() {
                                activity.hideLoading();
                            }

                            @Override
                            public void onError(Throwable e) {
                                activity.showToast("出现异常");
                            }

                            @Override
                            public void onNext(Integer i) {
                                if (i > 0) {
                                    activity.showToast("删除成功");
                                    forms.removeAll(adapter.getCheckedForms());
                                    adapter.setEditMode(false);
                                    EventBus.getDefault().post(new UpdateFormCountEvent());

                                }
                            }
                        });
                break;
            case R.id.btn_send_sms:
                ((BaseActivity) getActivity()).showToast("短信已发送");
                break;
            case R.id.btn_mark_client:
                String[] items2 = new String[]{"取件时间限定客户", "VIP客户", "黑名单客户", "其他客户",};
                new AlertDialog.Builder(getActivity())
                        .setItems(items2, (dialog, which) -> ((BaseActivity) getActivity()).showToast(items2[which]))
                        .create().show();
                break;
            default:
                break;
        }
    }

    public boolean onBackPressed() {
        if (adapter.isEditMode()) {
            adapter.setEditMode(false);
            adapter.setOperateClickable(true);
            return true;
        }
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d(LogTag.FRAGMENT, "fragment " + s + " onDestroy()");
    }
}
