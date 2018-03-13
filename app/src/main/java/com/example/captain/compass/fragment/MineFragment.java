package com.example.captain.compass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captain.compass.R;
import com.example.captain.compass.activity.FormManageActivity;
import com.example.captain.compass.activity.MyInfoActivity;
import com.example.captain.compass.activity.QRCodeActivity;
import com.example.captain.compass.activity.SmsTemplateListActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.layout_my_info, R.id.layout_sms_template, R.id.layout_feedback, R.id.layout_about_us})
    public void itemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_feedback:
//                FormManageActivity.launchActivity(getActivity());
                break;
            case R.id.layout_sms_template:
                SmsTemplateListActivity.launchActivity(getActivity());
                break;
            case R.id.layout_my_info:
                MyInfoActivity.launchActivity(getActivity());
                break;
            case R.id.layout_about_us:
//                QRCodeActivity.launchActivity(getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
