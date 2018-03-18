package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.captain.compass.adapter.FragmentPagerAdapter;
import com.example.captain.compass.R;
import com.example.captain.compass.bean.Form;
import com.example.captain.compass.constant.Constant;
import com.example.captain.compass.fragment.FormListFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FormListWithTabActivity extends BaseActivity {

    private static final String INTENT_KEY_LATITUDE = "INTENT_KEY_LATITUDE";
    private static final String INTENT_KEY_LONGITUDE = "INTENT_KEY_LONGITUDE";

    private static final int REQUEST_CODE_SCAN_QUERY_FORM = 2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.et_form_id)
    EditText etFormId;

    private double latitude = 0;
    private double longitude = 0;
    private List<FormListFragment> fragments = new ArrayList<>();
    private FragmentPagerAdapter<FormListFragment> adapter;

    public static void launchActivity(Context context) {
        Intent intent = new Intent(context, FormListWithTabActivity.class);
        context.startActivity(intent);
    }

    public static void launchActivity(Context context, double latitude, double longitude) {
        Intent intent = new Intent(context, FormListWithTabActivity.class);
        intent.putExtra(INTENT_KEY_LATITUDE, latitude);
        intent.putExtra(INTENT_KEY_LONGITUDE, longitude);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list_with_tab);
        ButterKnife.bind(this);
        initData();
        initToolbar();
    }

    public void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            latitude = intent.getDoubleExtra(INTENT_KEY_LATITUDE, 0);
            longitude = intent.getDoubleExtra(INTENT_KEY_LONGITUDE, 0);
        }
        fragments.add(FormListFragment.newInstance(latitude, longitude, Constant.FORM_STATE_DELIVERYING));
        fragments.add(FormListFragment.newInstance(latitude, longitude, Constant.FORM_STATE_RECEIVED));
        fragments.add(FormListFragment.newInstance(latitude, longitude, Constant.FORM_STATE_REJECTED));
        fragments.add(FormListFragment.newInstance(latitude, longitude, Constant.FORM_STATE_SECOND_DELIVERY));
        fragments.add(FormListFragment.newInstance(latitude, longitude, Constant.FORM_STATE_RECEIVER_UNCONTACTABLE));
        fragments.add(FormListFragment.newInstance(latitude, longitude, Constant.FORM_STATE_PACKAGE_BROKEN));
        fragments.add(FormListFragment.newInstance(latitude, longitude, Constant.FORM_STATE_PACKAGE_MISSED));
        adapter = new FragmentPagerAdapter<>(getSupportFragmentManager(), fragments,
                new String[]{"配送中", "已签收", "已拒收", "二次派送", "联系不到收件人", "包裹破损", "包裹丢失"});
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setOffscreenPageLimit(fragments.size() - 1);

    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
        toolbar.setTitle("快递单列表");
    }

    @OnClick({R.id.iv_query_form, R.id.iv_scan_query_form})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_query_form:
                String formId = etFormId.getText().toString();
                if (TextUtils.isEmpty(formId)) {
                    showToast("订单号不能为空！");
                    return;
                }
                FormListActivity.launchActivity(this, formId);
                break;
            case R.id.iv_scan_query_form:
                startActivityForResult(new Intent(this, CaptureActivity.class),
                        REQUEST_CODE_SCAN_QUERY_FORM);
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SCAN_QUERY_FORM && data != null) {
            //处理扫描结果（在界面上显示）
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String result = bundle.getString(CodeUtils.RESULT_STRING);
//                LogUtil.writeLogToFile(result,"json");
                GsonBuilder gsonBuilder = new GsonBuilder().excludeFieldsWithoutExposeAnnotation();
                Gson gson = gsonBuilder.create();
                Form form = gson.fromJson(result, Form.class);
                FormListActivity.launchActivity(this, form.getFormId());
            } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                showToast("解析二维码失败");
            }

        }
    }

    @Override
    public void onBackPressed() {
        if (!fragments.get(0).onBackPressed())
            super.onBackPressed();
    }
}
