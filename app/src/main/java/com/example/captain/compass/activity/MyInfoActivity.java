package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Toast;

import com.example.captain.compass.R;
import com.example.captain.compass.constant.Constant;
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
    }


    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @OnClick({R.id.layout_avatar, R.id.layout_name, R.id.layout_tel, R.id.layout_id_number,
            R.id.layout_company, R.id.layout_branch})
    public void itemClick(View view) {
        switch (view.getId()) {
            case R.id.layout_name:
            case R.id.layout_tel:
            case R.id.layout_id_number:
                final QMUIDialog.EditTextDialogBuilder builder = new QMUIDialog.EditTextDialogBuilder(this);
                builder.setTitle("标题")
                        .setPlaceholder("请输入")
                        .setInputType(InputType.TYPE_CLASS_TEXT)
                        .addAction("取消", ((dialog, index) -> dialog.dismiss()))
                        .addAction("确定", (dialog, index) -> {
                            CharSequence text = builder.getEditText().getText();
                            if (text != null && text.length() > 0) {
                                dialog.dismiss();
                            } else {
                                showToast("内容不能为空");
                            }
                        }).show();
                break;
            case R.id.layout_company:
                String[] items = new String[]{"顺丰快递", "申通快递", "韵达快递", "圆通快递", "宅急送"};
                new AlertDialog.Builder(this)
                        .setItems(items, (dialog, which) -> showToast(items[which]))
                        .create().show();
                break;
            case R.id.layout_branch:
                String[] items2 = new String[]{"北京海淀区北京邮电大学快递分部", "北京中关村分部", "北京主城区公司海淀区西郊服务部",
                        "北京市海淀锦绣大地", "北京海淀区巨山分部", "北京海淀区联想桥分部", "北京朝阳区朝阳门营业部","北京西城月坛营业点",
                "北京西城区宣武公司宣武寄存点分部"};
                new AlertDialog.Builder(this)
                        .setItems(items2, (dialog, which) -> showToast(items2[which]))
                        .create().show();
                break;
            default:
                break;
        }
    }
}
