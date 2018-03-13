package com.example.captain.compass;

import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.Properties;

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
        CrashReport.initCrashReport(this, "8e7430372d", false);
    }

    public static Application getApplication() {
        return application;
    }

}
