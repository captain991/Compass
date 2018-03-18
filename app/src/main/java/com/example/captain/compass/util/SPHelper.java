package com.example.captain.compass.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by captain on 2018/3/15 下午11:12}.
 */

public class SPHelper {

    public static void setIsLogin(Context context, boolean isLogin) {
        SharedPreferences sp = context.getSharedPreferences("status", Context.MODE_PRIVATE);
        sp.edit().putBoolean("isLogin", isLogin).apply();
    }

    public static boolean getIsLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences("status", Context.MODE_PRIVATE);
        return sp.getBoolean("isLogin", true);
    }
}
