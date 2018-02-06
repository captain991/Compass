package com.example.captain.compass;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;

/**
 * Created by captain on 2018/2/6.
 */

public class IntegerValueFormatter implements IValueFormatter {
    private DecimalFormat decimalFormat;

    public IntegerValueFormatter() {
        this.decimalFormat = new DecimalFormat("###,###,###");
    }

    @Override
    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {

        return value - new Float(value).intValue() == 0 ? decimalFormat.format(value) : String.valueOf(value);
    }
}
