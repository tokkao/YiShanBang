package com.benben.yishanbang.ui.tea.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.bean.TabEntityBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.ui.mine.adapter.ShoppingOrderViewPagerAdapter;
import com.benben.yishanbang.ui.tea.fragment.UserAllDynamicFragment;
import com.benben.yishanbang.ui.tea.fragment.UserImageDynamicFragment;
import com.benben.yishanbang.ui.tea.fragment.UserVideoDynamicFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/8/13 0013
 * Describe:他的动态页面
 */
public class UserDynamicActivity extends BaseActivity {

    private static final String TAG = "UserDynamicActivity";
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.tab_layout)
    CommonTabLayout tabLayoutDynamic;
    @BindView(R.id.vp_content)
    ViewPager vpUserDynamic;
    private boolean isMine;
    private ShoppingOrderViewPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void initData() {

        //0 自己查看自己的资料  1 别人查看自己的资料
        String mEnterType = getIntent().getStringExtra(Constants.EXTRA_KEY_ENTER_TYPE);
        isMine = "0".equals(mEnterType);
        //加一个判断   他的动态/我的动态
        centerTitle.setText(isMine ? "我的动态" : "TA的动态");

        Drawable drawable = getResources().getDrawable(R.mipmap.icon_add_goods);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(null, null, isMine ? drawable : null, null);

        ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();

        Bundle allBundle = new Bundle();
        Bundle imageBundle = new Bundle();
        Bundle playBundle = new Bundle();

        allBundle.putInt("type", 0);
        imageBundle.putInt("type", 2);
        playBundle.putInt("type", 1);
        allBundle.putBoolean("isMine", isMine);
        imageBundle.putBoolean("isMine", isMine);
        playBundle.putBoolean("isMine", isMine);
        UserAllDynamicFragment userAllDynamicFragment = new UserAllDynamicFragment();
        UserVideoDynamicFragment userVideoDynamicFragment = new UserVideoDynamicFragment();
        UserImageDynamicFragment userImageDynamicFragment = new UserImageDynamicFragment();

        userAllDynamicFragment.setArguments(allBundle);
        userVideoDynamicFragment.setArguments(playBundle);
        userImageDynamicFragment.setArguments(imageBundle);

        mFragmentList.add(userAllDynamicFragment);
        mFragmentList.add(userVideoDynamicFragment);
        mFragmentList.add(userImageDynamicFragment);

        mTabEntities.add(new TabEntityBean("全部", 0, 0));
        mTabEntities.add(new TabEntityBean("视频动态", 0, 0));
        mTabEntities.add(new TabEntityBean("图片动态", 0, 0));
        mPagerAdapter = new ShoppingOrderViewPagerAdapter(getSupportFragmentManager(), mFragmentList);
        vpUserDynamic.setAdapter(mPagerAdapter);

        tabLayoutDynamic.setTabData(mTabEntities);
        tabLayoutDynamic.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                vpUserDynamic.setCurrentItem(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

        vpUserDynamic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayoutDynamic.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:  //返回上一页
                onBackPressed();
                break;
            case R.id.right_title://添加动态
                startActivity(new Intent(mContext, AddDynamicActivity.class));
                break;
        }
    }

}
