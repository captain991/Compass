package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.captain.compass.R;
import com.example.captain.compass.util.SPHelper;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_account)
    EditText etAccount;

    @BindView(R.id.et_pwd)
    EditText etPwd;

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    public void initView() {
        QMUIStatusBarHelper.translucent(this);
    }

    @OnClick({R.id.btn_register, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                RegisterActivity.launchActivity(this);
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(etAccount.getText().toString()) ||
                        TextUtils.isEmpty(etPwd.getText().toString())) {
                    showToast("账号或密码不能为空!");
                    return;
                }
                MainActivity.launchActivity(this);
                SPHelper.setIsLogin(this, true);
                finish();
                break;
            default:
                break;
        }
    }
}
