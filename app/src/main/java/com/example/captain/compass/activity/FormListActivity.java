package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.captain.compass.adapter.FormListAdapter;
import com.example.captain.compass.LogTag;
import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;
import com.example.captain.compass.database.FormDb;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FormListActivity extends BaseActivity implements FormListAdapter.OnStateChangeListener {

    private static final String INTENT_KEY_LATITUDE = "INTENT_KEY_LATITUDE";
    private static final String INTENT_KEY_LONGITUDE = "INTENT_KEY_LONGITUDE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private double latitude = 0;
    private double longitude = 0;

    private List<Form> forms = new ArrayList<>();
    private FormListAdapter adapter;


    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, FormListActivity.class);
        context.startActivity(intent);
    }

    public static void launchActivity(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, FormListActivity.class);
        intent.putExtra(INTENT_KEY_LATITUDE, latitude);
        intent.putExtra(INTENT_KEY_LONGITUDE, longitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list);
        ButterKnife.bind(this);
        initData();
        initToolbar();
        getForms();
    }

    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            latitude = intent.getDoubleExtra(INTENT_KEY_LATITUDE, 0);
            longitude = intent.getDoubleExtra(INTENT_KEY_LONGITUDE, 0);
        }
        adapter = new FormListAdapter(this, forms);
        adapter.setOnStateChangeListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("快递单列表");
    }

    public void getForms() {
        showLoading("加载数据中...");
        Observable.create(new Observable.OnSubscribe<List<Form>>() {
            @Override
            public void call(Subscriber<? super List<Form>> subscriber) {
                FormDb formDb = FormDb.getInstance();
                List<Form> forms = null;
                if (latitude != 0 && longitude != 0) {
                    forms = formDb.queryForms(latitude, longitude);
                } else {
                    forms = formDb.queryForms();
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
                        hideLoading();
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
                            toolbar.setTitle("快递单列表(" + f.size() + ")");
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public void onStateChanged(Form form, int state, int position) {

    }
}
