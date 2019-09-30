package com.benben.yishanbang.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.benben.yishanbang.base.LazyBaseFragments;

import java.util.List;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:首页viewpager适配器
 */
public class MainViewPagerAdapter extends FragmentPagerAdapter {

    private List<LazyBaseFragments> fragments;
    private FragmentManager fragmentManager;
    public MainViewPagerAdapter(FragmentManager fm, List<LazyBaseFragments> fragmentList) {
        super(fm);
        this.fragments = fragmentList;
        this.fragmentManager = fm;
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
