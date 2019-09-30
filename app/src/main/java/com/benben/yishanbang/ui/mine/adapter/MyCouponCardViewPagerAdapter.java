package com.benben.yishanbang.ui.mine.adapter;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.benben.yishanbang.base.LazyBaseFragments;

import java.util.List;

/**
 * Create by wanghk on 2019-08-07.
 * Describe:我的优惠卡viewpager适配器
 */
public class MyCouponCardViewPagerAdapter extends FragmentPagerAdapter {

    private List<LazyBaseFragments> fragments;
    private FragmentManager fragmentManager;
    private List<String> mTitles;
    public MyCouponCardViewPagerAdapter(FragmentManager fm, List<LazyBaseFragments> fragmentList,List<String> mTitles) {
        super(fm);
        this.fragments = fragmentList;
        this.fragmentManager = fm;
        this.mTitles = mTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }
}
