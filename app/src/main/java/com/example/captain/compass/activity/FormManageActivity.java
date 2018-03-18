package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.captain.compass.util.LogTag;
import com.example.captain.compass.database.FormDb;
import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FormManageActivity extends BaseActivity {

    @BindView(R.id.et_sender_tel)
    EditText etSenderTel;

    @BindView(R.id.et_receiver_name)
    EditText etReceiverName;

    @BindView(R.id.et_receiver_address)
    EditText etReceiverAddress;

    @BindView(R.id.et_receiver_tel)
    EditText etReceiverTel;

    @BindView(R.id.et_receiver_longitude)
    EditText etReceiverLongitude;

    @BindView(R.id.et_receiver_latitude)
    EditText etReceiverLatitude;

    @BindView(R.id.et_mark)
    EditText etMark;

    String senderTel;
    String receiverName;
    String receiverAddress;
    String receiverTel;
    double receiverLongitude;
    double receiverLatitude;
    String mark;


    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, FormManageActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_manage);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_add_form, R.id.btn_add_forms})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_add_form:
                senderTel = etSenderTel.getText().toString();
                receiverName = etReceiverName.getText().toString();
                receiverAddress = etReceiverAddress.getText().toString();
                receiverTel = etReceiverTel.getText().toString();
                receiverLongitude = Double.valueOf(etReceiverLongitude.getText().toString());
                receiverLatitude = Double.valueOf(etReceiverLatitude.getText().toString());
                mark = etMark.toString();
                if (TextUtils.isEmpty(senderTel) || TextUtils.isEmpty(receiverName) ||
                        TextUtils.isEmpty(receiverAddress) || TextUtils.isEmpty(receiverTel) ||
                        receiverLongitude == 0 || receiverLatitude == 0) {
                    showToast("除了备注所有项都是必填选项，有内容为空");
                    return;
                }
                String formId = new Random().nextInt(999999999) + "";
                Form form = new Form(senderTel, receiverName, receiverAddress, receiverTel, formId, mark,
                        receiverLatitude, receiverLongitude);
                showLoading("保存中...");
                insertForm(form);
                break;

            case R.id.btn_add_forms:
                List<Form> forms = new ArrayList<>();
                forms.add(new Form("13512345600", "王东", "北京市昌平区回龙观新村中区",
                        "13512345601", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345602", "余非", "北京市昌平区回龙观新村中区",
                        "13512345603", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345604", "王季", "北京市昌平区回龙观新村中区",
                        "13512345605", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345606", "李果然", "北京市昌平区回龙观新村中区",
                        "13512345607", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345608", "韩佳佳", "北京市昌平区回龙观新村中区",
                        "13512345609", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345610", "张可", "北京市昌平区回龙观新村中区",
                        "13512345611", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345612", "吴过", "北京市昌平区回龙观新村中区",
                        "13512345613", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345614", "王刚", "北京市昌平区回龙观新村中区",
                        "13512345615", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345616", "张启明", "北京市昌平区回龙观新村中区",
                        "13512345617", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345618", "张策", "北京市昌平区回龙观新村中区",
                        "13512345619", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345620", "孙而非", "北京市昌平区回龙观新村中区",
                        "13512345621", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345622", "周时乐", "北京市昌平区融泽嘉园",
                        "13512345623", new Random().nextInt(999999999) + "", "",
                        40.06860538d, 116.30557179));

                forms.add(new Form("13512345624", "陈金飞", "北京市昌平区回龙观新村中区",
                        "13512345625", new Random().nextInt(999999999) + "", "",
                        40.06252112d, 116.30934834));

                forms.add(new Form("13512345626", "朱有光", "北京市海淀区领秀新硅谷D区",
                        "13512345627", new Random().nextInt(999999999) + "", "",
                        40.0541123, 116.30983114));

                forms.add(new Form("13512345628", "李和光", "北京市海淀区领秀新硅谷D区",
                        "13512345629", new Random().nextInt(999999999) + "", "",
                        40.0541123, 116.30983114));

                for (Form f : forms) {
                    insertForm(f);
                }
                break;
            default:
                break;
        }

    }

    public void insertForm(Form form) {
        Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                subscriber.onNext(FormDb.getInstance().insertForm(form));
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
                        Log.e(LogTag.DB, e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        showToast(aLong > 0 ? "插入订单成功" : "插入失败");
                    }
                });
    }
}
