package com.example.captain.compass;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import com.example.captain.compass.util.LogTag;
import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * Created by captain on 2017/11/26.
 */

public class Application extends android.app.Application {
    private static Application application;
    private List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        ZXingLibrary.initDisplayOpinion(this);
        CrashReport.initCrashReport(this, "8e7430372d", false);
        CrashReport.setAppVersion(this, BuildConfig.VERSION_CODE + "(" + BuildConfig.BUILD_VERSION + ")");
    }

    public static Application getApplication() {
        return application;
    }

    public boolean addActivity(Activity activity) {
        return activities.add(activity);
    }

    public boolean removeActivity(Activity activity) {
        return activities.remove(activity);
    }

    public void finishAllActivity() {
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }

}
