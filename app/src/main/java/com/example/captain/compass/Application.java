package com.example.captain.compass;

import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * Created by captain on 2017/11/26.
 */

public class Application extends android.app.Application {
    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ZXingLibrary.initDisplayOpinion(this);

    }

    public static Application getApplication() {
        return application;
    }

}
