package com.example.captain.compass.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.captain.compass.R;
import com.example.captain.compass.activity.StatisticsChartActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.layout_workload, R.id.layout_time_consuming, R.id.layout_time_consuming_during_trip,
            R.id.layout_difficult})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.layout_workload:
                StatisticsChartActivity.launchActivity(getActivity(), StatisticsChartActivity.TYPE_WORK_LOAD);
                break;
            case R.id.layout_time_consuming:
                StatisticsChartActivity.launchActivity(getActivity(), StatisticsChartActivity.TYPE_TIME_CONSUMING);
                break;
            case R.id.layout_time_consuming_during_trip:
                StatisticsChartActivity.launchActivity(getActivity(), StatisticsChartActivity.TYPE_TRIP_TIME_CONSUMING);
                break;
            case R.id.layout_difficult:
                StatisticsChartActivity.launchActivity(getActivity(), StatisticsChartActivity.TYPE_DIFFICULT);
                break;
            default:
                break;
        }
    }

}
