package com.example.captain.compass;

import android.app.*;
import android.os.Bundle;

/**
 * Created by captain on 2017/3/13 0013.
 */

public class ActivityLifecycleCallbackImpl implements android.app.Application.ActivityLifecycleCallbacks {
    private Application app = Application.getApplication();

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        app.addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        app.removeActivity(activity);
    }
}
