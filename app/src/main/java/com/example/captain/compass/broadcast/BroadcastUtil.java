package com.example.captain.compass.broadcast;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.example.captain.compass.bean.Form;

/**
 * Created by captain on 2018/1/30.
 */

public class BroadcastUtil {

    public static final String BROADCAST_ACTION_ADD_FORM = "BROADCAST_ACTION_ADD_FORM";
    public static final String BROADCAST_INTENT_KEY_FORM = "BROADCAST_INTENT_KEY_FORM";


    public static void addForm(Context context, Form form) {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        Intent intent = new Intent(BROADCAST_ACTION_ADD_FORM);
        intent.putExtra(BROADCAST_INTENT_KEY_FORM, form);
        lbm.sendBroadcast(intent);
    }
}
