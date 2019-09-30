package com.benben.yishanbang.ui.mine.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.benben.commoncore.utils.DensityUtil;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.TabEntityBean;
import com.benben.yishanbang.ui.mine.adapter.ShoppingOrderViewPagerAdapter;
import com.benben.yishanbang.ui.mine.fragment.MyServicesFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

import static com.benben.yishanbang.ui.mine.fragment.MyServicesFragment.KEY_TYPE;
import static com.benben.yishanbang.ui.mine.fragment.MyServicesFragment.KEY_TYPE_ORDER;
import static com.benben.yishanbang.ui.mine.fragment.MyServicesFragment.KEY_TYPE_TAKE;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //我的服务 （发布/接受的家政订单  发布/接受的任务）
 */
public class MyServicesActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    private int enterType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_shopping_order;
    }

    @Override
    protected void initData() {
        enterType = getIntent().getIntExtra("enter_type", 0);
        switch (enterType) {
            case 0:
                centerTitle.setText("已发布的家政订单");
                break;
            case 1:
                centerTitle.setText("已接受的家政订单");
                break;
            case 2:
                centerTitle.setText("已发布的任务");
                break;
            case 3:
                centerTitle.setText("已接受的任务");
                break;

        }
        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();

        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            MyServicesFragment myServicesFragment = new MyServicesFragment();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_TYPE, "" + i);
            bundle.putString(KEY_TYPE_ORDER, (enterType == 0 || enterType == 1) ? "2" : "1");
            bundle.putString(KEY_TYPE_TAKE, (enterType == 0 || enterType == 2) ? "1" : "2");
            myServicesFragment.setArguments(bundle);
            mFragmentList.add(myServicesFragment);
        }

        mTabEntities.add(new TabEntityBean("全部"));
        mTabEntities.add(new TabEntityBean("未接单"));
        mTabEntities.add(new TabEntityBean("进行中"));
        mTabEntities.add(new TabEntityBean("已完成"));
        vpContent.setAdapter(new ShoppingOrderViewPagerAdapter(getSupportFragmentManager(), mFragmentList));
        vpContent.setOffscreenPageLimit(mFragmentList.size());

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

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) vpContent.getLayoutParams();
        layoutParams.setMargins(0, DensityUtil.dip2px(mContext, 10), 0, 0);
    }


    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
