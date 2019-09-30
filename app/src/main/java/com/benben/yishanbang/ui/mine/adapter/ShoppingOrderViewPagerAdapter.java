package com.benben.yishanbang.ui.mine.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.benben.yishanbang.base.LazyBaseFragments;

import java.util.List;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:购物订单viewpager适配器
 */
public class ShoppingOrderViewPagerAdapter extends FragmentPagerAdapter {

    private List<LazyBaseFragments> fragments;
    private FragmentManager fragmentManager;

    public ShoppingOrderViewPagerAdapter(FragmentManager fm, List<LazyBaseFragments> fragmentList) {
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
