package com.example.captain.compass.presenter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.widget.LinearLayout;

import com.example.captain.compass.IntegerValueFormatter;
import com.example.captain.compass.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by captain on 2018/2/2.
 */

public abstract class AbstractLineChartPresenter extends AbstractChartPresenter {
    protected LineChart lineChart;

    @Override
    public void initChart() {
        lineChart = new LineChart(context);
        // no description text
        lineChart.getDescription().setEnabled(false);

        // enable touch gestures
        lineChart.setTouchEnabled(true);

        lineChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setHighlightPerDragEnabled(true);

        // if disabled, scaling can be done on  x- and y-axis separately
        lineChart.setPinchZoom(true);

        // set an alternative background color
//        lineChart.setBackgroundColor(Color.LTGRAY);

//        lineChart.animateX(700);
        lineChart.animateY(900);
        // get the legend (only possible after setting data)
        Legend l = lineChart.getLegend();

        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextSize(11f);
        l.setTextColor(Color.BLACK);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setTextSize(11f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(false);
        xAxis.setLabelRotationAngle(-45);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setTextColor(ContextCompat.getColor(context, R.color.red));
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);

        updateYAxis(leftAxis, rightAxis);
        addView2Layout();
    }

    @Override
    public void updateChartData() {
        if (lineChart.getData() != null && lineChart.getData().getDataSetCount() > 0) {
            List<List<Entry>> entries = getEntries();
            for (int i = 0; i < entries.size(); i++) {
                List<Entry> values = entries.get(i);
                LineDataSet lineDataSet = (LineDataSet) lineChart.getData().getDataSetByIndex(i);
                lineDataSet.setValues(values);
            }
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();
        } else {
            LineData data = new LineData();

            List<LineDataSet> dataSets = getDataSets();
            for (LineDataSet lineDataSet : dataSets) {
                lineDataSet.setLineWidth(3f);
                lineDataSet.setCircleRadius(4f);
                lineDataSet.setFillAlpha(65);
                lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));
                lineDataSet.setCircleColorHole(Color.WHITE);
                lineDataSet.setDrawCircleHole(true);
                data.addDataSet(lineDataSet);
            }
            // create a data object with the datasets

            data.setHighlightEnabled(false);
            data.setValueTextColor(Color.BLACK);
            data.setValueTextSize(9f);
            data.setValueFormatter(new IntegerValueFormatter());

            // set data
            lineChart.setData(data);
            lineChart.getXAxis().setValueFormatter(((value, axis) ->
                    new SimpleDateFormat("MM/dd", Locale.CHINA).format(
                            (Date) (getEntries().get(0).get((int) value - 1).getData()))
            ));
            lineChart.invalidate();
        }

    }

    abstract public List<LineDataSet> getDataSets();

    abstract public List<List<Entry>> getEntries();

    abstract public void updateYAxis(YAxis leftAxis, YAxis rightAxis);

    public void addView2Layout() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        view.addView(lineChart, layoutParams);
    }

}
