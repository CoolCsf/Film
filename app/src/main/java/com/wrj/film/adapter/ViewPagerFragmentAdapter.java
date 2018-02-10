package com.wrj.film.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/24.
 * 文件描述
 */

public class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragments;
    private String[] titles;

    public ViewPagerFragmentAdapter(FragmentManager fm, String[] titles, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.titles = titles;
        this.fragments = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
