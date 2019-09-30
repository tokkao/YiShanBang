package com.benben.yishanbang.ui.mine.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.benben.commoncore.utils.DensityUtil;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.TabEntityBean;
import com.benben.yishanbang.ui.mine.adapter.ShoppingOrderViewPagerAdapter;
import com.benben.yishanbang.ui.mine.fragment.WindowManageFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.benben.yishanbang.config.Constants.ENTRY_TYPE_WINDOW_CUSTOMER_MANAGE;
import static com.benben.yishanbang.config.Constants.ENTRY_TYPE_WINDOW_ORDER_MANAGE;
import static com.benben.yishanbang.config.Constants.EXTRA_KEY_ENTER_TYPE;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //橱窗模块 -订单管理
 */
public class OrderManageWindowActivity extends BaseActivity {
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
        centerTitle.setText("订单管理");
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();
        if (getIntent().getIntExtra(EXTRA_KEY_ENTER_TYPE, ENTRY_TYPE_WINDOW_ORDER_MANAGE) == ENTRY_TYPE_WINDOW_CUSTOMER_MANAGE) {
            tabLayout.setVisibility(View.GONE);
            WindowManageFragment orderManageFragment = new WindowManageFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(EXTRA_KEY_ENTER_TYPE, getIntent().getIntExtra(EXTRA_KEY_ENTER_TYPE, ENTRY_TYPE_WINDOW_CUSTOMER_MANAGE));
            bundle.putInt("orderType", 5);
            orderManageFragment.setArguments(bundle);
            mFragmentList.add(orderManageFragment);
            vpContent.setAdapter(new ShoppingOrderViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
        } else {
            tabLayout.setVisibility(View.VISIBLE);
            for (int i = 0; i < 3; i++) {
                WindowManageFragment orderManageFragment = new WindowManageFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(EXTRA_KEY_ENTER_TYPE, getIntent().getIntExtra(EXTRA_KEY_ENTER_TYPE, ENTRY_TYPE_WINDOW_ORDER_MANAGE));
                bundle.putInt("orderType", (i + 1));
                orderManageFragment.setArguments(bundle);
                mFragmentList.add(orderManageFragment);
            }

            mTabEntities.add(new TabEntityBean("待发货"));
            mTabEntities.add(new TabEntityBean("待取货"));
            mTabEntities.add(new TabEntityBean("已完成"));

            vpContent.setAdapter(new ShoppingOrderViewPagerAdapter(getSupportFragmentManager(), mFragmentList));

            tabLayout.setTabData(mTabEntities);
            tabLayout.setIndicatorHeight(0);
            tabLayout.setIndicatorWidth(0);
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


        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) vpContent.getLayoutParams();
        layoutParams.setMargins(0, DensityUtil.dip2px(mContext, 10), 0, 0);
    }


    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
