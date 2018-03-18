package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.captain.compass.Application;
import com.example.captain.compass.R;
import com.example.captain.compass.constant.Constant;
import com.example.captain.compass.util.SPHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static void launchActivity(Context context) {
        context.startActivity(new Intent(context, MyInfoActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        ButterKnife.bind(this);
        initToolbar();
        setDisplayHomeAsUpEnabled();
    }


    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.layout_avatar, R.id.layout_name, R.id.layout_tel, R.id.layout_id_number,
            R.id.layout_company, R.id.layout_branch, R.id.tv_log_off})
    public void itemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_name:
                showInputDialog("", "请输入姓名", InputType.TYPE_CLASS_TEXT, "李明");
                break;
            case R.id.layout_tel:
                showInputDialog("", "请输入手机号", InputType.TYPE_CLASS_PHONE, "15913145200");
                break;
            case R.id.layout_id_number:
                showInputDialog("", "请输入身份证号", InputType.TYPE_CLASS_TEXT, "123456201810017890");
                break;
            case R.id.layout_company:
                String[] items = new String[]{"顺丰快递", "申通快递", "韵达快递", "圆通快递", "宅急送"};
                new AlertDialog.Builder(this)
                        .setItems(items, (dialog, which) -> showToast(items[which]))
                        .create().show();
                break;
            case R.id.layout_branch:
                String[] items2 = new String[]{"地大中通网点", "丁字桥中通网点", "方家嘴中通网点", "傅家坡中通网点",
                        "积玉桥中通网点", "武大中通网点", "武昌中通网点", "杨家湾中通网点", "阅马场中通网点"};
                new AlertDialog.Builder(this)
                        .setItems(items2, (dialog, which) -> showToast(items2[which]))
                        .create().show();
                break;
            case R.id.tv_log_off:
                Application.getApplication().finishAllActivity();
                LoginActivity.launchActivity(this);
                SPHelper.setIsLogin(this, false);
                break;
            default:
                break;
        }
    }

    public void showInputDialog(String title, String placeHolder, int inputType, String hint) {
        final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
        builder.setTitle(title)
                .setPlaceholder(placeHolder)
                .setInputType(inputType)
                .addAction("取消", ((dialog, index) -> dialog.dismiss()))
                .addAction("确定", (dialog, index) -> {
                    CharSequence text = builder.getEditText().getText();
                    if (text != null && text.length() > 0) {
                        dialog.dismiss();
                    } else {
                        showToast("内容不能为空");
                    }
                }).show();
        builder.getEditText().setText(hint);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
