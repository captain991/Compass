package com.example.captain.compass.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.captain.compass.R;
import com.example.captain.compass.fragment.MineFragment;
import com.example.captain.compass.fragment.StatisticsFragment;
import com.example.captain.compass.fragment.WorkFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener,
        BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView bottomNavigationView;

    private FragmentPagerAdapter fragmentPagerAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initToolbar();
    }

    public void initData() {
        fragments.add(WorkFragment.newInstance("", ""));
        fragments.add(StatisticsFragment.newInstance("", ""));
        fragments.add(MineFragment.newInstance("", ""));

        fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    public void initToolbar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        viewPager.setCurrentItem(item.getOrder());
        return true;
    }

    private class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
        private List<Fragment> fragments;

        public FragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }


}
