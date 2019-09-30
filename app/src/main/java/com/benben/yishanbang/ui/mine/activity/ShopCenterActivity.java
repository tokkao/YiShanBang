package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;

import butterknife.BindView;
import butterknife.OnClick;

import static com.benben.yishanbang.config.Constants.ENTRY_TYPE_SHOP;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //商户中心
 */
public class ShopCenterActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_shop_lottery)
    TextView tvShopLottery;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    private String mUserType;
    private boolean isTea;

    //两种商户：
    //
    //1、优惠卡商户
    //
    //没有抽奖活动
    //
    //2.、奶茶商户
    //
    //没有店铺管理
    @Override
    protected int getLayoutId() {
        return R.layout.activity_shop_center;
    }

    @Override
    protected void initData() {
        centerTitle.setText("商户中心");
        mUserType = MyApplication.mPreferenceProvider.getUserType();
        //两种商户：
        //2 优惠卡商户 店铺管理
        //3 奶茶商户  抽奖活动
        isTea = "3".equals(mUserType);
        if (isTea) {
            tvShopLottery.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.icon_lottery_active), null, null);
            tvShopLottery.setText("抽奖活动");
            tvDiscount.setText("奶茶查看");
        } else {
            tvShopLottery.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(R.mipmap.icon_my_window), null, null);
            tvShopLottery.setText("店铺管理");
            tvDiscount.setText("优惠卡查看");

        }
    }


    @OnClick({R.id.rl_back, R.id.tv_shop_lottery, R.id.tv_discount, R.id.tv_order_manage, R.id.tv_user_manage, R.id.tv_data_report, R.id.tv_writing_off_order, R.id.iv_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_shop_lottery:
                if (isTea) {//抽奖活动
                    startActivity(new Intent(mContext, LotteryActiveActivity.class));
                } else {//店铺管理
                    startActivity(new Intent(mContext, StoreManageActivity.class));
                }
                break;
            case R.id.tv_discount://优惠卡/奶茶 查看
                startActivity(new Intent(mContext, GoodsManageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, isTea ? 4 : 2));
                break;
            case R.id.tv_order_manage://订单管理
                if (isTea) {
                    startActivity(new Intent(mContext, OrderManageTeaActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, ENTRY_TYPE_SHOP));
                } else {
//                    startActivity(new Intent(mContext, OrderManageTeaActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1));
                    startActivity(new Intent(mContext, OrderManageCardActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1));
                }
                break;
            case R.id.tv_user_manage://客户管理
                startActivity(new Intent(mContext, ClientManageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 0));
                break;
            case R.id.tv_data_report://数据报表
                startActivity(new Intent(mContext, GoodsManageActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE, isTea ? 5 : 3));
                break;
            case R.id.tv_writing_off_order://核销订单
                if (isTea) {  //2：优惠卡   3：奶茶
                    startActivity(new Intent(mContext, WriteOffTeaOrderActivity.class));
                } else {
                    startActivity(new Intent(mContext, WriteOffCardOrderActivity.class));
                }
                break;
            case R.id.iv_setting://设置 子账号
                startActivity(new Intent(mContext, SubAccountManageActivity.class));
                break;
        }
    }


}
