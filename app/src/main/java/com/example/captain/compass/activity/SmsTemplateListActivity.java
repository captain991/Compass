package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.example.captain.compass.R;
import com.example.captain.compass.SampleItemDecoration;
import com.example.captain.compass.adapter.SmsTemplateListAdapter;
import com.example.captain.compass.bean.SmsTemplate;
import com.example.captain.compass.database.SmsTemplateDb;
import com.example.captain.compass.event.UpdateSmsTemplateEvent;
import com.qmuiteam.qmui.widget.QMUIEmptyView;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SmsTemplateListActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.empty_view)
    QMUIEmptyView emptyView;

    private List<SmsTemplate> smsTemplates = new ArrayList<>();

    private SmsTemplateListAdapter adapter;

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, SmsTemplateListActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_template_list);
        ButterKnife.bind(this);
        initToolbar();
        setDisplayHomeAsUpEnabled();
        initData();
        initListener();
        EventBus.getDefault().register(this);
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    public void initData() {
        adapter = new SmsTemplateListAdapter(smsTemplates);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new SampleItemDecoration());
        emptyView.show(true);
        getSmsTemplates();
    }

    public void initListener() {
        adapter.setOnItemClickListener(
                ((smsTemplate, position) -> SmsTemplateActivity.launchActivity(this, smsTemplate)));
        adapter.setOnItemLongClickListener(
                ((smsTemplate, position) -> {
                    new QMUIDialog.MessageDialogBuilder(this)
                            .setTitle("")
                            .setMessage("确定要删除该模板么？")
                            .addAction("取消", ((dialog, index) -> dialog.dismiss()))
                            .addAction("确定", ((dialog, index) -> {
                                dialog.dismiss();
                                int result = SmsTemplateDb.getInstance().deleteSmsTemplate(smsTemplate);
                                if (result > 0) {
                                    showToast("删除成功");
                                    this.smsTemplates.remove(position);
                                    adapter.notifyItemRemoved(position);
                                }
                            }))

                            .show();
                    return true;
                }));
    }

    public void getSmsTemplates() {
        Observable.create(new Observable.OnSubscribe<List<SmsTemplate>>() {
            @Override
            public void call(Subscriber<? super List<SmsTemplate>> subscriber) {
                subscriber.onNext(SmsTemplateDb.getInstance().querySmsTemplates());
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<SmsTemplate>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        emptyView.show(false);
                        emptyView.setDetailText("出现错误,请重新尝试..." + e.toString());
                    }

                    @Override
                    public void onNext(List<SmsTemplate> templates) {
                        if (templates.size() > 0) {
                            smsTemplates.clear();
                            smsTemplates.addAll(templates);
                            adapter.notifyDataSetChanged();
                            emptyView.hide();
                        } else {
                            emptyView.show(false);
                            emptyView.setDetailText("暂无数据");
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sms_template_list, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add_template) {
            SmsTemplateActivity.launchActivity(this, null);
        }else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(UpdateSmsTemplateEvent event) {
        getSmsTemplates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
