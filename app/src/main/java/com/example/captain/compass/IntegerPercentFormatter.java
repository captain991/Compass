package com.example.captain.compass;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by captain on 2018/2/6.
 */

public class IntegerPercentFormatter implements IValueFormatter, IAxisValueFormatter {
    private DecimalFormat decimalFormat;

    public IntegerPercentFormatter() {
        decimalFormat = new DecimalFormat("###,###,###");
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return (value - new Float(value).intValue() == 0 ? decimalFormat.format(value) : String.valueOf(value)) + "%";
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return (value - new Float(value).intValue() == 0 ? decimalFormat.format(value) : String.valueOf(value)) + "%";
    }
}
