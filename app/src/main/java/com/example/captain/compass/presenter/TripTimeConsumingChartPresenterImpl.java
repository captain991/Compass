package com.example.captain.compass.presenter;

import android.support.v4.content.ContextCompat;

import com.example.captain.compass.R;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by captain on 2018/2/2.
 */

public class TripTimeConsumingChartPresenterImpl extends AbstractLineChartPresenter {

    @Override
    public void initToolbar() {
        view.initToolbar("途中耗时统计");
    }

    @Override
    public List<LineDataSet> getDataSets() {
        List<List<Entry>> entries = getEntries();
        List<LineDataSet> dataSets = new ArrayList<>();
        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(entries.get(0), "分钟/km");

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

        yVals1.add(new Entry(1, 2.7f));
        yVals1.add(new Entry(2, 5.2f));
        yVals1.add(new Entry(3, 7));
        yVals1.add(new Entry(4, 3.5f));
        yVals1.add(new Entry(5, 6));
        yVals1.add(new Entry(6, 2.8f));
        yVals1.add(new Entry(7, 5.1f));

        ArrayList<Entry> yVals2 = new ArrayList<>();
        yVals2.add(new Entry(1, -9));
        yVals2.add(new Entry(2, -3));
        yVals2.add(new Entry(3, -1));
        yVals2.add(new Entry(4, -7));
        yVals2.add(new Entry(5, -2));
        yVals2.add(new Entry(6, -7));
        yVals2.add(new Entry(7, -3));
        entries.add(yVals1);
        entries.add(yVals2);
        return entries;
    }

    @Override
    public void updateYAxis(YAxis leftAxis, YAxis rightAxis) {
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);

        rightAxis.setAxisMaximum(0);
        rightAxis.setAxisMinimum(-10);
    }
}
