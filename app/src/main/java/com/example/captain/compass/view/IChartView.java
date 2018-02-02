package com.example.captain.compass.view;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;

/**
 * Created by captain on 2018/2/2.
 */

public interface IChartView {
    void getChart(PieChart pieChart, LineChart lineChart);

    void initToolbar(String title);
}
