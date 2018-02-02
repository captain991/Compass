package com.example.captain.compass.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.captain.compass.R;
import com.example.captain.compass.presenter.AbstractChartPresenter;
import com.example.captain.compass.presenter.DifficultChartPresenterImpl;
import com.example.captain.compass.presenter.TimeConsumingChartPresenterImpl;
import com.example.captain.compass.presenter.TripTimeConsumingChartPresenterImpl;
import com.example.captain.compass.presenter.WorkLoadChartPresenterImpl;
import com.example.captain.compass.view.IChartView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StatisticsChartActivity extends BaseActivity implements IChartView {

    public static final String INTENT_KEY_TYPE = "INTENT_KEY_TYPE";

    public static final int TYPE_WORK_LOAD = 1;
    public static final int TYPE_TIME_CONSUMING = 2;
    public static final int TYPE_TRIP_TIME_CONSUMING = 3;
    public static final int TYPE_DIFFICULT = 4;
    private int type;

    private DatePickerDialog.OnDateSetListener fromDateSetListener;
    private DatePickerDialog.OnDateSetListener toDateSetListener;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.layout_chart)
    LinearLayout layoutChart;

    @BindView(R.id.btn_date_from)
    Button btnDateFrom;

    @BindView(R.id.btn_date_to)
    Button btnDateTo;

    private PieChart pieChart;
    private LineChart lineChart;
    private AbstractChartPresenter presenter;
    Calendar calendar = Calendar.getInstance();
    private Date dateFrom = new Date();
    private Date dateTo = new Date();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM//dd", Locale.CHINA);


    public static void launchActivity(Context context, int type) {
        Intent intent = new Intent(context, StatisticsChartActivity.class);
        intent.putExtra(INTENT_KEY_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_chart);
        ButterKnife.bind(this);
        initPresenter();
        initView();
        presenter.bindView(this, this);
        presenter.initToolbar();
        presenter.initChart();
        presenter.getChartData();
        initListener();
    }

    public void initView() {
        btnDateFrom.setText(simpleDateFormat.format(dateFrom));
        btnDateTo.setText(simpleDateFormat.format(dateTo));
    }

    @Override
    public void getChart(PieChart pieChart, LineChart lineChart) {
        this.pieChart = pieChart;
        this.lineChart = lineChart;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (pieChart != null) {
            layoutChart.addView(pieChart, layoutParams);
        }
        if (lineChart != null)
            layoutChart.addView(lineChart, layoutParams);
    }

    public void initListener() {
        fromDateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            dateFrom = calendar.getTime();
            btnDateFrom.setText(simpleDateFormat.format(dateFrom));
        };

        toDateSetListener = (view, year, month, dayOfMonth) -> {
            calendar.set(year, month, dayOfMonth);
            dateTo = calendar.getTime();
            btnDateTo.setText(simpleDateFormat.format(dateTo));
        };
    }

    public void initPresenter() {
        type = getIntent().getIntExtra(INTENT_KEY_TYPE, 0);
        if (type == TYPE_WORK_LOAD) {
            presenter = new WorkLoadChartPresenterImpl();
        } else if (type == TYPE_TIME_CONSUMING) {
            presenter = new TimeConsumingChartPresenterImpl();
        } else if (type == TYPE_TRIP_TIME_CONSUMING) {
            presenter = new TripTimeConsumingChartPresenterImpl();
        } else if (type == TYPE_DIFFICULT) {
            presenter = new DifficultChartPresenterImpl();
        }
    }

    @Override
    public void initToolbar(String title) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.btn_date_from, R.id.btn_date_to})
    public void onClick(View view) {
        Date date;
        DatePickerDialog.OnDateSetListener listener;
        if (view.getId() == R.id.btn_date_from) {
            listener = fromDateSetListener;
            date = dateFrom;
        } else {
            listener = toDateSetListener;
            date = dateTo;
        }
        calendar.setTime(date);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, listener,
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
}
