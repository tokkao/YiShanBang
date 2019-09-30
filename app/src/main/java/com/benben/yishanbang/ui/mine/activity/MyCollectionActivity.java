package com.benben.yishanbang.ui.mine.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.TabEntityBean;
import com.benben.yishanbang.ui.mine.adapter.ShoppingOrderViewPagerAdapter;
import com.benben.yishanbang.ui.mine.fragment.MyCollectionGoodsFragment;
import com.benben.yishanbang.ui.mine.fragment.MyCollectionShopFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //我的收藏
 */
public class MyCollectionActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initData() {
        centerTitle.setText("我的收藏");
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();

        Bundle shopBundle = new Bundle();
        Bundle goodsBundle = new Bundle();
        shopBundle.putInt("type", 0);
        goodsBundle.putInt("type", 1);

        MyCollectionShopFragment myCollectionShopFragment = new MyCollectionShopFragment();
        MyCollectionGoodsFragment myCollectionGoodsFragment = new MyCollectionGoodsFragment();

        myCollectionShopFragment.setArguments(shopBundle);
        myCollectionGoodsFragment.setArguments(goodsBundle);

        mFragmentList.add(myCollectionShopFragment);
        mFragmentList.add(myCollectionGoodsFragment);

        mTabEntities.add(new TabEntityBean("店铺", 0, 0));
        mTabEntities.add(new TabEntityBean("商品", 0, 0));
        vpContent.setAdapter(new ShoppingOrderViewPagerAdapter(getSupportFragmentManager(), mFragmentList));

        tabLayout.setTabData(mTabEntities);
        tabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpContent.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vpContent.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
