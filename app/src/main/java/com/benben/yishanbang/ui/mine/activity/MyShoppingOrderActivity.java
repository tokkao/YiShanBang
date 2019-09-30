package com.benben.yishanbang.ui.mine.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.TabEntityBean;
import com.benben.yishanbang.ui.mine.adapter.ShoppingOrderViewPagerAdapter;
import com.benben.yishanbang.ui.mine.fragment.MyShoppingOrderFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.benben.yishanbang.config.Constants.DATA_KEY;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //我的购物订单
 */
public class MyShoppingOrderActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_shopping_order;
    }

    @Override
    protected void initData() {
        centerTitle.setText("我的订单");
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            MyShoppingOrderFragment orderFragment = new MyShoppingOrderFragment();
            Bundle args = new Bundle();
            args.putString(DATA_KEY, i + "");
            orderFragment.setArguments(args);
            mFragmentList.add(orderFragment);
        }

        mTabEntities.add(new TabEntityBean("全部", 0, 0));
        mTabEntities.add(new TabEntityBean("待付款", 0, 0));
        mTabEntities.add(new TabEntityBean("待发货", 0, 0));
        mTabEntities.add(new TabEntityBean("待收货", 0, 0));
        mTabEntities.add(new TabEntityBean("已完成", 0, 0));
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
