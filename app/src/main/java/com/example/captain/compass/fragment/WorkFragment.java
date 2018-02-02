package com.example.captain.compass.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captain.compass.LogTag;
import com.example.captain.compass.R;
import com.example.captain.compass.activity.CarInfoActivity;
import com.example.captain.compass.activity.ClientActivity;
import com.example.captain.compass.activity.FormListWithTabActivity;
import com.example.captain.compass.activity.MapActivity;
import com.example.captain.compass.constant.Constant;
import com.example.captain.compass.database.FormDb;
import com.uuzuche.lib_zxing.activity.CaptureActivity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

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

    private String mParam1;
    private String mParam2;

    @BindView(R.id.tv_deliverying_count)
    TextView tvDeliveryingCount;

    @BindView(R.id.tv_received_count)
    TextView tvReceivedCount;

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
        initData();
    }

    public void initData() {
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
            R.id.layout_package_state,})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.layout_scan_delivery:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                getActivity().startActivityForResult(intent, 1);
                break;
            case R.id.layout_navigation:
                MapActivity.launchActivity(getActivity());
                break;
            case R.id.layout_client:
                ClientActivity.launchActivity(getActivity());
                break;
            case R.id.layout_car_info:
                CarInfoActivity.launchActivity(getActivity());
                break;
            case R.id.layout_package_state:
                FormListWithTabActivity.launchActivity(getActivity());
                break;

            default:
                break;

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
