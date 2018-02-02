package com.example.captain.compass.presenter;

import android.content.Context;

import com.example.captain.compass.view.IChartView;

/**
 * Created by captain on 2018/2/2.
 */

public abstract class AbstractChartPresenter implements IChartPresenter {
    protected IChartView view = null;
    protected Context context = null;

    public void bindView(IChartView view, Context context) {
        this.view = view;
        this.context = context;
    }

    public void unbindView() {
        this.view = null;
        this.context = null;
    }
}
