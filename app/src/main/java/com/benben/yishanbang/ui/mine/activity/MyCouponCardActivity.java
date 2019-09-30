package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.benben.commoncore.utils.DensityUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.discount.activity.SearchActivity;
import com.benben.yishanbang.ui.mine.adapter.MyCouponCardViewPagerAdapter;
import com.benben.yishanbang.ui.mine.bean.WalletCouponCardMenuBean;
import com.benben.yishanbang.ui.mine.fragment.MyCouponCardFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 钱包 - 我的优惠卡
 */
public class MyCouponCardActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tab_layout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.right_title)
    TextView rightTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_my_coupon_card;
    }

    @Override
    protected void initData() {
        centerTitle.setText("我的优惠卡");

        /**
         * 右边搜索logo
         */
        Drawable drawable = getResources().getDrawable(R.mipmap.ic_search_white);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        rightTitle.setCompoundDrawables(null, null, drawable, null);

        ArrayList<String> mTabEntities = new ArrayList<>();//tab数组
        ArrayList<LazyBaseFragments> mFragmentList = new ArrayList<>();//fragment数组

        /**
         * 布局
         */
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) vpContent.getLayoutParams();
        /**
         * fragment距离top  10dp
         */
        layoutParams.setMargins(0, DensityUtil.dip2px(mContext, 10), 0, 0);

        /**
         * 去掉指示器
         */
        tabLayout.setIndicatorHeight(0);
        tabLayout.setIndicatorWidth(0);

        /**
         * 网络请求
         */
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.WALLET_QUERY_SHOP_CATE_GROY)
                .get()
                .build().enqueue(mContext, new BaseCallBack<String>() {
                    @Override
                    public void onSuccess(String json, String msg) {

                        List<WalletCouponCardMenuBean> walletCouponCardMenuBeans = JSONUtils.jsonString2Beans(json, WalletCouponCardMenuBean.class);
                        //添加tab数据和fragmen
                        for(int i = 0;i < walletCouponCardMenuBeans.size();i++){
                            mTabEntities.add(walletCouponCardMenuBeans.get(i).getCategoryName());//设置类型
                            MyCouponCardFragment myServicesFragment = new MyCouponCardFragment();//实例化fragment
                            Bundle bundle = new Bundle();
                            bundle.putString("mCouponCardType", walletCouponCardMenuBeans.get(i).getCategoryId());//Bundle传值   类型id
                            myServicesFragment.setArguments(bundle);
                            mFragmentList.add(myServicesFragment);//添加fragment到数组里
                        }
                        /**
                         * 实例化适配器viewpager
                         */
                        vpContent.setAdapter(new MyCouponCardViewPagerAdapter(getSupportFragmentManager(), mFragmentList, mTabEntities));
                        tabLayout.setViewPager(vpContent);

                    }

                    @Override
                    public void onError(int code, String msg) {

                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                });

    }

    @OnClick({R.id.rl_back, R.id.right_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://搜索
                Intent intent = new Intent(mContext, SearchActivity.class);
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 3);
                startActivity(intent);
                break;
        }
    }
}
