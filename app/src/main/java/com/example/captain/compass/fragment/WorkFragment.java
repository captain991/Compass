package com.example.captain.compass.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.captain.compass.activity.FormListActivity;
import com.example.captain.compass.util.LogTag;
import com.example.captain.compass.R;
import com.example.captain.compass.activity.BaseActivity;
import com.example.captain.compass.activity.CarInfoActivity;
import com.example.captain.compass.activity.ClientActivity;
import com.example.captain.compass.activity.FormListWithTabActivity;
import com.example.captain.compass.activity.MapActivity;
import com.example.captain.compass.bean.Form;
import com.example.captain.compass.constant.Constant;
import com.example.captain.compass.database.FormDb;
import com.example.captain.compass.event.UpdateFormCountEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WorkFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int REQUEST_CODE_SCAN_DELIVERY = 1;
    private static final int REQUEST_CODE_SCAN_QUERY_FORM = 2;

    private String mParam1;
    private String mParam2;

    @BindView(R.id.tv_deliverying_count)
    TextView tvDeliveryingCount;

    @BindView(R.id.tv_received_count)
    TextView tvReceivedCount;

    @BindView(R.id.et_form_id)
    EditText etFormId;

    public WorkFragment() {
        // Required empty public constructor
    }

    public static WorkFragment newInstance(String param1, String param2) {
        WorkFragment fragment = new WorkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        EventBus.getDefault().register(this);
        initData(null);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initData(UpdateFormCountEvent event) {
        Observable.create(new Observable.OnSubscribe<CountData>() {
            @Override
            public void call(Subscriber<? super CountData> subscriber) {
                int deliverying = FormDb.getInstance().getFormCount(Constant.FORM_STATE_DELIVERYING);
                int received = FormDb.getInstance().getFormCount(Constant.FORM_STATE_RECEIVED);
                CountData countData = new CountData(deliverying, received);
                subscriber.onNext(countData);
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<CountData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LogTag.DB, "getFormCount error:" + e.toString());
                    }

                    @Override
                    public void onNext(CountData countData) {
                        tvDeliveryingCount.setText(String.valueOf(countData.getDeliverying()));
                        tvReceivedCount.setText(String.valueOf(countData.getReceived()));
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.layout_scan_delivery, R.id.layout_navigation, R.id.layout_client, R.id.layout_car_info,
            R.id.layout_package_state, R.id.iv_query_form, R.id.iv_scan_query_form})
    public void click(View view) {
        BaseActivity activity = (BaseActivity) getActivity();
        switch (view.getId()) {
            case R.id.layout_scan_delivery:
                Intent intent = new Intent(activity, CaptureActivity.class);
                getActivity().startActivityForResult(intent, REQUEST_CODE_SCAN_DELIVERY);
                break;
            case R.id.layout_navigation:
                MapActivity.launchActivity(activity);
                break;
            case R.id.layout_client:
                ClientActivity.launchActivity(activity);
                break;
            case R.id.layout_car_info:
                CarInfoActivity.launchActivity(activity);
                break;
            case R.id.layout_package_state:
                FormListWithTabActivity.launchActivity(activity);
                break;
            case R.id.iv_query_form:
                String formId = etFormId.getText().toString();
                if (TextUtils.isEmpty(formId)) {
                    activity.showToast("订单号不能为空！");
                    return;
                }
                FormListActivity.launchActivity(activity, formId);
                break;
            case R.id.iv_scan_query_form:
                activity.startActivityForResult(new Intent(activity, CaptureActivity.class),
                        REQUEST_CODE_SCAN_QUERY_FORM);
                break;

            default:
                break;

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_CODE_SCAN_DELIVERY || requestCode == REQUEST_CODE_SCAN_QUERY_FORM) &&
                data != null) {
            //处理扫描结果（在界面上显示）
            BaseActivity activity = (BaseActivity) getActivity();
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
//                LogUtil.writeLogToFile(result,"json");
                GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                Gson gson = gsonBuilder.create();
                Form form = gson.fromJson(result, Form.class);
                if (requestCode == REQUEST_CODE_SCAN_DELIVERY) {
                    handleScanDelivery(activity, form);
                } else if (requestCode == REQUEST_CODE_SCAN_QUERY_FORM) {
                    handleQueryForm(activity, form.getFormId());
                }
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                activity.showToast("解析二维码失败");
            }

        }
    }

    public void handleScanDelivery(BaseActivity activity, Form form) {
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

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(LogTag.CAPTAIN, "scan qrcode insert form error:" + e.toString());
                    }

                    @Override
                    public void onNext(Long aLong) {
                        activity.showToast("添加快递单成功！");
                        EventBus.getDefault().post(new UpdateFormCountEvent());
                    }
                });
    }

    public void handleQueryForm(BaseActivity activity, String formId) {
        FormListActivity.launchActivity(activity, formId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private class CountData implements Serializable {
        private int deliverying;
        private int received;

        public CountData(int deliverying, int received) {
            this.deliverying = deliverying;
            this.received = received;
        }

        public int getDeliverying() {
            return deliverying;
        }

        public void setDeliverying(int deliverying) {
            this.deliverying = deliverying;
        }

        public int getReceived() {
            return received;
        }

        public void setReceived(int received) {
            this.received = received;
        }
    }

}
