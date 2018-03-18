package com.example.captain.compass.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

import com.example.captain.compass.Application;
import com.example.captain.compass.R;
import com.example.captain.compass.util.SPHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.et_tel)
    EditText etTel;

    @BindView(R.id.et_captcha)
    EditText etCaptcha;

    @BindView(R.id.et_pwd)
    EditText etPwd;

    @BindView(R.id.btn_get_captcha)
    Button btnGetCaptcha;

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initToolbar();
        setDisplayHomeAsUpEnabled();
        ContentResolver contentResolver = getContentResolver();

    }


    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_register) {
            register();
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    public void register() {
        if (TextUtils.isEmpty(etTel.getText().toString())) {
            showToast("手机号不能为空!");
            return;
        }

        if (TextUtils.isEmpty(etCaptcha.getText().toString())) {
            showToast("验证码不能为空!");
            return;
        }

        if (TextUtils.isEmpty(etPwd.getText().toString())) {
            showToast("密码不能为空!");
            return;
        }
        showToast("注册成功");
        SPHelper.setIsLogin(this, true);
        Application.getApplication().finishAllActivity();
        MainActivity.launchActivity(this);
    }

    @OnClick(R.id.btn_get_captcha)
    public void onClick(View view) {
        if (TextUtils.isEmpty(etTel.getText().toString())) {
            showToast("手机号不能为空!");
            return;
        }
        showToast("验证码已发送至手机，请注意查收！");
        btnGetCaptcha.setEnabled(false);
//        ValueAnimator valueAnimator = ValueAnimator.ofInt(59, 0);
//        valueAnimator.setInterpolator(new LinearInterpolator());
//        valueAnimator.addUpdateListener(animator -> {
//            int value = (int) animator.getAnimatedValue();
//            value = value == 0 ? 60 : value;
//            btnGetCaptcha.setEnabled(value == 60);
//            btnGetCaptcha.setText(value + "S");
//        });
//        valueAnimator.setDuration(59 * 1000);
//
//        valueAnimator.start();
    }
}
