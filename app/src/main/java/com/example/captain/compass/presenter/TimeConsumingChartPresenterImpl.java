package com.example.captain.compass.presenter;

import android.support.v4.content.ContextCompat;

import com.example.captain.compass.R;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by captain on 2018/2/2.
 */

public class TimeConsumingChartPresenterImpl extends AbstractLineChartPresenter {

    @Override
    public void initToolbar() {
        view.initToolbar("包裹耗时统计");
    }

    @Override
    public List<LineDataSet> getDataSets() {
        List<List<Entry>> entries = getEntries();
        List<LineDataSet> dataSets = new ArrayList<>();
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entries.get(0), "分钟/(件∙km)");

        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(ColorTemplate.getHoloBlue());
        set1.setFillColor(ColorTemplate.getHoloBlue());


        // create a dataset and give it a type
        LineDataSet set2 = new LineDataSet(entries.get(1), "温度/°C");
        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set2.setColor(ContextCompat.getColor(context, R.color.red));
        set2.setCircleColor(ContextCompat.getColor(context, R.color.red));
        set2.setFillColor(ContextCompat.getColor(context, R.color.red));
        dataSets.add(set1);
        dataSets.add(set2);
        return dataSets;
    }

    @Override
    public List<List<Entry>> getEntries() {
        List<List<Entry>> entries = new ArrayList<>();
        ArrayList<Entry> yVals1 = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 1, 1);
        yVals1.add(new Entry(1, 4.5f, calendar.getTime()));
        calendar.set(2018, 1, 2);
        yVals1.add(new Entry(2, 10f, calendar.getTime()));
        calendar.set(2018, 1, 3);
        yVals1.add(new Entry(3, 14.5f, calendar.getTime()));
        calendar.set(2018, 1, 4);
        yVals1.add(new Entry(4, 16.2f, calendar.getTime()));
        calendar.set(2018, 1, 5);
        yVals1.add(new Entry(5, 14.2f, calendar.getTime()));
        calendar.set(2018, 1, 6);
        yVals1.add(new Entry(6, 14.2f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(7, 13.6f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(8, 12f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(9, 12.5f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(10, 11.1f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(11, 10.75f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(12, 11.3f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(13, 9.3f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(14, 7.3f, calendar.getTime()));

        ArrayList<Entry> yVals2 = new ArrayList<>();
        calendar.set(2018, 1, 1);
        yVals2.add(new Entry(1, 8, calendar.getTime()));
        calendar.set(2018, 1, 2);
        yVals2.add(new Entry(2, 5, calendar.getTime()));
        calendar.set(2018, 1, 3);
        yVals2.add(new Entry(3, 2, calendar.getTime()));
        calendar.set(2018, 1, 4);
        yVals2.add(new Entry(4, 0, calendar.getTime()));
        calendar.set(2018, 1, 5);
        yVals2.add(new Entry(5, -1, calendar.getTime()));
        calendar.set(2018, 1, 6);
        yVals2.add(new Entry(6, -1, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(7, -1, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(8, -2, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(9, 0, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(10, 1, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(11, 2, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(12, 3, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(13, 5, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals2.add(new Entry(14, 7, calendar.getTime()));
        entries.add(yVals1);
        entries.add(yVals2);
        return entries;
    }

    @Override
    public void updateYAxis(YAxis leftAxis, YAxis rightAxis) {
        leftAxis.setAxisMaximum(20f);
        leftAxis.setAxisMinimum(0f);

        rightAxis.setAxisMaximum(10);
        rightAxis.setAxisMinimum(-10);
    }

    @Override
    public void updateChartData() {
        super.updateChartData();
        lineChart.getXAxis().setValueFormatter(((value, axis) ->
                new SimpleDateFormat("MM/dd", Locale.CHINA).format(
                        (Date) (getEntries().get(0).get((int) value - 1).getData()))
        ));
        lineChart.invalidate();
    }
}
