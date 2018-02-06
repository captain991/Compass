package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.captain.compass.R;
import com.example.captain.compass.bean.SmsTemplate;
import com.example.captain.compass.database.SmsTemplateDb;
import com.example.captain.compass.event.UpdateSmsTemplateEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SmsTemplateActivity extends BaseActivity {

    public static final String INTENT_KEY_SMS_TEMPLATE = "INTENT_KEY_SMS_TEMPLATE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_title)
    EditText etTitle;

    @BindView(R.id.et_content)
    EditText etContent;

    private SmsTemplate smsTemplate = null;

    public static void launchActivity(Context context, SmsTemplate smsTemplate) {
        Intent intent = new Intent(context, SmsTemplateActivity.class);
        intent.putExtra(INTENT_KEY_SMS_TEMPLATE, smsTemplate);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_template);
        ButterKnife.bind(this);
        initToolbar();
        initData();
        initView();
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    public void initData() {
        smsTemplate = (SmsTemplate) getIntent().getSerializableExtra(INTENT_KEY_SMS_TEMPLATE);
    }

    public void initView() {
        if (smsTemplate != null) {
            etTitle.setText(smsTemplate.getTitle());
            etContent.setText(smsTemplate.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sms_template, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_save) {
            saveSmsTemplate();
        }
        return true;
    }

    public void saveSmsTemplate() {
        showLoading("保存中...");
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                long result;
                if (smsTemplate != null) {
                    smsTemplate.setTitle(etTitle.getText().toString());
                    smsTemplate.setContent(etContent.getText().toString());
                    smsTemplate.setUpdateTime(System.currentTimeMillis());
                    result = SmsTemplateDb.getInstance().updateSmsTemplate(smsTemplate);
                } else {
                    SmsTemplate st = new SmsTemplate();
                    st.setTitle(etTitle.getText().toString());
                    st.setContent(etContent.getText().toString());
                    st.setUpdateTime(System.currentTimeMillis());
                    result = SmsTemplateDb.getInstance().insertSmsTemplate(st);
                }
                subscriber.onNext(result);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Long result) {
                        if (result > 0) {
                            EventBus.getDefault().post(new UpdateSmsTemplateEvent());
                            showToast("保存成功");
                            finish();
                        } else
                            showToast("保存失败");
                    }
                });

    }
}
