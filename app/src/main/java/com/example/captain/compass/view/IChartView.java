package com.example.captain.compass.view;


import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by captain on 2018/2/2.
 */

public interface IChartView {
    void initToolbar(String title);

    void addView(View view, LinearLayout.LayoutParams layoutParams);
}
