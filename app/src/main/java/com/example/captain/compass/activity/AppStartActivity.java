package com.example.captain.compass.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.captain.compass.R;
import com.example.captain.compass.util.SPHelper;

public class AppStartActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_start);
        if (SPHelper.getIsLogin(this))
            MainActivity.launchActivity(this);
        else
            LoginActivity.launchActivity(this);
        finish();
    }
}
