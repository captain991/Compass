package com.example.captain.compass.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;

import com.example.captain.compass.adapter.FragmentPagerAdapter;
import com.example.captain.compass.R;
import com.example.captain.compass.constant.Constant;
import com.example.captain.compass.fragment.FormListFragment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FormListWithTabActivity extends BaseActivity {

    private static final String INTENT_KEY_LATITUDE = "INTENT_KEY_LATITUDE";
    private static final String INTENT_KEY_LONGITUDE = "INTENT_KEY_LONGITUDE";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

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

    @Override
    public void onBackPressed() {
        if (!fragments.get(0).onBackPressed())
            super.onBackPressed();
    }
}
