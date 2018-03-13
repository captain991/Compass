package com.example.captain.compass.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import java.util.List;

/**
 * Created by captain on 2018/1/29.
 */

public class FragmentPagerAdapter<T extends Fragment> extends android.support.v4.app.FragmentPagerAdapter {
    private List<T> fragments;
    private String[] titles;

    public FragmentPagerAdapter(FragmentManager fm, List<T> fragments) {
        this(fm, fragments, null);
    }

    public FragmentPagerAdapter(FragmentManager fm, List<T> fragments, String[] titles) {
        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles == null || titles.length == 0 ? "" : titles[position];
    }

    @Override
    public T getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
