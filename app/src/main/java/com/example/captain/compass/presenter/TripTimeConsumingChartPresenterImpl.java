package com.example.captain.compass.presenter;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.captain.compass.IntegerPercentFormatter;
import com.example.captain.compass.R;
import com.example.captain.compass.widget.ScaleCircleNavigator;
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by captain on 2018/2/2.
 */

public class TripTimeConsumingChartPresenterImpl extends AbstractLineChartPresenter {
    private ViewPager viewPager;
    private ChartPagerAdapter adapter;
    private CombinedChart combinedChart;

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

        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 1, 1);
        yVals1.add(new Entry(1, 2.34f, calendar.getTime()));
        calendar.set(2018, 1, 2);
        yVals1.add(new Entry(2, 2.6f, calendar.getTime()));
        calendar.set(2018, 1, 3);
        yVals1.add(new Entry(3, 3.86f, calendar.getTime()));
        calendar.set(2018, 1, 4);
        yVals1.add(new Entry(4, 4.7f, calendar.getTime()));
        calendar.set(2018, 1, 5);
        yVals1.add(new Entry(5, 3.12f, calendar.getTime()));
        calendar.set(2018, 1, 6);
        yVals1.add(new Entry(6, 3.12f, calendar.getTime()));
        calendar.set(2018, 1, 7);
        yVals1.add(new Entry(7, 2.62f, calendar.getTime()));
        calendar.set(2018, 1, 8);
        yVals1.add(new Entry(8, 1.54f, calendar.getTime()));
        calendar.set(2018, 1, 9);
        yVals1.add(new Entry(9, 1.7f, calendar.getTime()));
        calendar.set(2018, 1, 10);
        yVals1.add(new Entry(10, 0.78f, calendar.getTime()));
        calendar.set(2018, 1, 11);
        yVals1.add(new Entry(11, 0.86f, calendar.getTime()));
        calendar.set(2018, 1, 12);
        yVals1.add(new Entry(12, 1.94f, calendar.getTime()));
        calendar.set(2018, 1, 13);
        yVals1.add(new Entry(13, 2.1f, calendar.getTime()));
        calendar.set(2018, 1, 14);
        yVals1.add(new Entry(14, 2.26f, calendar.getTime()));

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
        calendar.set(2018, 1, 8);
        yVals2.add(new Entry(8, -2, calendar.getTime()));
        calendar.set(2018, 1, 9);
        yVals2.add(new Entry(9, 0, calendar.getTime()));
        calendar.set(2018, 1, 10);
        yVals2.add(new Entry(10, 1, calendar.getTime()));
        calendar.set(2018, 1, 11);
        yVals2.add(new Entry(11, 2, calendar.getTime()));
        calendar.set(2018, 1, 12);
        yVals2.add(new Entry(12, 3, calendar.getTime()));
        calendar.set(2018, 1, 13);
        yVals2.add(new Entry(13, 5, calendar.getTime()));
        calendar.set(2018, 1, 14);
        yVals2.add(new Entry(14, 7, calendar.getTime()));
        entries.add(yVals1);
        entries.add(yVals2);
        return entries;
    }

    @Override
    public void updateYAxis(YAxis leftAxis, YAxis rightAxis) {
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(0f);

        rightAxis.setAxisMaximum(10);
        rightAxis.setAxisMinimum(-10);
    }

    @Override
    public void initChart() {
        super.initChart();
        combinedChart = new CombinedChart(context);
        combinedChart.getDescription().setEnabled(false);
        combinedChart.setBackgroundColor(Color.WHITE);
        combinedChart.setDrawGridBackground(false);
        combinedChart.setDrawBarShadow(false);
        combinedChart.setHighlightFullBarEnabled(false);

        combinedChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        combinedChart.setMaxVisibleValueCount(20);

        // scaling can now only be done on x- and y-axis separately
        combinedChart.setPinchZoom(false);
        combinedChart.setDrawValueAboveBar(false);

        Legend l = combinedChart.getLegend();
        l.setWordWrapEnabled(true);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);

        YAxis rightAxis = combinedChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setAxisMaximum(10f); // this replaces setStartAtZero(true)
        rightAxis.setAxisMinimum(-10f);

        YAxis leftAxis = combinedChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)
        leftAxis.setAxisMaximum(100f);
        leftAxis.setValueFormatter(new IntegerPercentFormatter());

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setAxisMinimum(0.5f);
        xAxis.setAxisMaximum(14.5f);
//        xAxis.setSpaceMin(0.5f);
        xAxis.setGranularity(1f);
        xAxis.setLabelRotationAngle(0);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

    }

    @Override
    public void updateChartData() {
        super.updateChartData();
        CombinedData data = new CombinedData();

        data.setData(getLineData());
        data.setData(getBarData());
        combinedChart.setData(data);

        XAxis xAxis = combinedChart.getXAxis();
        xAxis.setValueFormatter(((value, axis) ->
                new SimpleDateFormat("MM/dd", Locale.CHINA).format(
                        (Date) (getLineData().getDataSets().get(0).getEntryForIndex((int) value - 1).getData()))
        ));
        combinedChart.invalidate();

        lineChart.getXAxis().setValueFormatter(((value, axis) ->
                new SimpleDateFormat("MM/dd", Locale.CHINA).format(
                        (Date) (getLineData().getDataSets().get(0).getEntryForIndex((int) value - 1).getData()))
        ));
        lineChart.invalidate();
    }

    public LineData getLineData() {
        LineData lineData = new LineData();
        List<Entry> entries = new ArrayList<>();
        LineDataSet lineDataSet = new LineDataSet(entries, "温度/°C");
        Calendar calendar = Calendar.getInstance();

        calendar.set(2018, 1, 1);
        entries.add(new Entry(1, 8, calendar.getTime()));
        calendar.set(2018, 1, 2);
        entries.add(new Entry(2, 5, calendar.getTime()));
        calendar.set(2018, 1, 3);
        entries.add(new Entry(3, 2, calendar.getTime()));
        calendar.set(2018, 1, 4);
        entries.add(new Entry(4, 0, calendar.getTime()));
        calendar.set(2018, 1, 5);
        entries.add(new Entry(5, -1, calendar.getTime()));
        calendar.set(2018, 1, 6);
        entries.add(new Entry(6, -1, calendar.getTime()));
        calendar.set(2018, 1, 7);
        entries.add(new Entry(7, -1, calendar.getTime()));
        calendar.set(2018, 1, 8);
        entries.add(new Entry(8, -2, calendar.getTime()));
        calendar.set(2018, 1, 9);
        entries.add(new Entry(9, 0, calendar.getTime()));
        calendar.set(2018, 1, 10);
        entries.add(new Entry(10, 1, calendar.getTime()));
        calendar.set(2018, 1, 11);
        entries.add(new Entry(11, 2, calendar.getTime()));
        calendar.set(2018, 1, 12);
        entries.add(new Entry(12, 3, calendar.getTime()));
        calendar.set(2018, 1, 13);
        entries.add(new Entry(13, 5, calendar.getTime()));
        calendar.set(2018, 1, 14);
        entries.add(new Entry(14, 7, calendar.getTime()));


        lineDataSet.setLineWidth(3f);
        lineDataSet.setFillAlpha(65);
//        lineDataSet.setCircleColorHole(Color.WHITE);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(ContextCompat.getColor(context, R.color.yellow));

//        lineDataSet.setCircleColor(ContextCompat.getColor(context, R.color.yellow));
        lineDataSet.setCircleRadius(0f);
//        lineDataSet.setFillColor(ContextCompat.getColor(context, R.color.yellow));
        lineDataSet.setDrawValues(false);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setAxisDependency(YAxis.AxisDependency.RIGHT);
        lineData.addDataSet(lineDataSet);
        lineData.setHighlightEnabled(false);
        return lineData;
    }

    public BarData getBarData() {
        BarData barData = new BarData();
        List<BarEntry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2018, 1, 1);
        entries.add(new BarEntry(1, new float[]{17f, 83f}, calendar.getTime()));
        calendar.set(2018, 1, 2);
        entries.add(new BarEntry(2, new float[]{24f, 76f}, calendar.getTime()));
        calendar.set(2018, 1, 3);
        entries.add(new BarEntry(3, new float[]{32f, 68f}, calendar.getTime()));
        calendar.set(2018, 1, 4);
        entries.add(new BarEntry(4, new float[]{37f, 63f}, calendar.getTime()));
        calendar.set(2018, 1, 5);
        entries.add(new BarEntry(5, new float[]{30f, 70f}, calendar.getTime()));
        calendar.set(2018, 1, 6);
        entries.add(new BarEntry(6, new float[]{38f, 62f}, calendar.getTime()));
        calendar.set(2018, 1, 7);
        entries.add(new BarEntry(7, new float[]{36f, 64f}, calendar.getTime()));
        calendar.set(2018, 1, 8);
        entries.add(new BarEntry(8, new float[]{35f, 65f}, calendar.getTime()));
        calendar.set(2018, 1, 9);
        entries.add(new BarEntry(9, new float[]{33f, 67f}, calendar.getTime()));
        calendar.set(2018, 1, 10);
        entries.add(new BarEntry(10, new float[]{30f, 70f}, calendar.getTime()));
        calendar.set(2018, 1, 11);
        entries.add(new BarEntry(11, new float[]{28f, 72f}, calendar.getTime()));
        calendar.set(2018, 1, 12);
        entries.add(new BarEntry(12, new float[]{27f, 73f}, calendar.getTime()));
        calendar.set(2018, 1, 13);
        entries.add(new BarEntry(13, new float[]{23f, 77f}, calendar.getTime()));
        calendar.set(2018, 1, 14);
        entries.add(new BarEntry(14, new float[]{19f, 81f}, calendar.getTime()));
        BarDataSet dataSet = new BarDataSet(entries, "");
        dataSet.setDrawIcons(false);
        dataSet.setStackLabels(new String[]{"途中耗时", "其他耗时"});
        dataSet.setColors(new int[]{ContextCompat.getColor(context, R.color.red),
                ColorTemplate.getHoloBlue()});
        dataSet.setValueTextSize(0f);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        barData.addDataSet(dataSet);
        barData.setHighlightEnabled(false);
        float barWidth = 0.45f; // x2 dataset

        barData.setBarWidth(barWidth);
//        barData.setValueFormatter(new IntegerPercentFormatter());
        return barData;
    }

    @Override
    public void addView2Layout() {
        viewPager = new ViewPager(context);
        adapter = new ChartPagerAdapter();
        viewPager.setAdapter(adapter);
        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 0);
        lp1.weight = 1;
        view.addView(viewPager, lp1);

        MagicIndicator magicIndicator = new MagicIndicator(context);
        ScaleCircleNavigator scaleCircleNavigator = new ScaleCircleNavigator(context);
        scaleCircleNavigator.setCircleCount(2);
        scaleCircleNavigator.setNormalCircleColor(Color.LTGRAY);
        scaleCircleNavigator.setSelectedCircleColor(Color.DKGRAY);
        scaleCircleNavigator.setCircleClickListener(index -> viewPager.setCurrentItem(index));
        magicIndicator.setNavigator(scaleCircleNavigator);
        ViewPagerHelper.bind(magicIndicator, viewPager);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp2.gravity = Gravity.CENTER_HORIZONTAL;
        lp2.setMargins(0, 20, 0, 40);
        view.addView(magicIndicator, lp2);

    }

    private class ChartPagerAdapter extends PagerAdapter {
//        public ChartPagerAdapter(LineChart lineChart) {
//            this.lineChart = lineChart;
//        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = position == 1 ? combinedChart : lineChart;
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return object.equals(lineChart) ? 0 : 1;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }


    }
}
