package com.benben.yishanbang.ui.mine;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.benben.commoncore.utils.DensityUtil;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StatusBarUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.LazyBaseFragments;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.activity.ControlCenterActivity;
import com.benben.yishanbang.ui.mine.activity.MilkTeaOrderActivity;
import com.benben.yishanbang.ui.mine.activity.MyCollectionActivity;
import com.benben.yishanbang.ui.mine.activity.MyCouponCardActivity;
import com.benben.yishanbang.ui.mine.activity.MyLotteryActivity;
import com.benben.yishanbang.ui.mine.activity.MyShoppingOrderActivity;
import com.benben.yishanbang.ui.mine.activity.PersonalDataActivity;
import com.benben.yishanbang.ui.mine.activity.ServiceManageActivity;
import com.benben.yishanbang.ui.mine.activity.SettingActivity;
import com.benben.yishanbang.ui.mine.activity.ShopCenterActivity;
import com.benben.yishanbang.ui.mine.activity.ShopStationedActivity;
import com.benben.yishanbang.ui.mine.activity.WalletActivity;
import com.benben.yishanbang.ui.mine.activity.WindowManageActivity;
import com.benben.yishanbang.ui.mine.bean.UserInfoBean;
import com.benben.yishanbang.ui.tea.activity.UserDetailsActivity;
import com.benben.yishanbang.utils.LoginCheckUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 9:14
 * 个人中心
 */
public class MineFragment extends LazyBaseFragments {
    private static final String TAG = "MineFragment";
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_nickname)
    TextView tvNickname;
    @BindView(R.id.tv_my_coupon_card)
    TextView tvMyCouponCard;
    @BindView(R.id.card_view)
    CardView cardView;
    @BindView(R.id.tv_shop_center)
    TextView tvShopCenter;
    @BindView(R.id.view_divider)
    View viewDivider;
    @BindView(R.id.rlyt_avatar)
    RelativeLayout rlytAvatar;

    @Override
    public View bindLayout(LayoutInflater inflater) {
        mRootView = inflater.inflate(R.layout.frag_mine, null);
        return mRootView;
    }

    @Override
    public void initView() {
        if (!LoginCheckUtils.checkLoginShowDialog(mContext)) {
            return;
        }
        //是否是商户  来显示商户中心
        if ("2".equals(MyApplication.mPreferenceProvider.getUserType()) || "3".equals(MyApplication.mPreferenceProvider.getUserType())) {
            tvShopCenter.setVisibility(View.VISIBLE);
            viewDivider.setVisibility(View.VISIBLE);
        } else {
            tvShopCenter.setVisibility(View.GONE);
            viewDivider.setVisibility(View.GONE);
        }
        int mStatusBarHeight = StatusBarUtils.getStatusBarHeight(mContext);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rlytAvatar.getLayoutParams();
        layoutParams.setMargins(0, mStatusBarHeight+ DensityUtil.dip2px(mContext,10), 0, 0);
        rlytAvatar.setLayoutParams(layoutParams);
    }

    @Override
    public void initData() {
    }


    @Override
    protected void loadData() {
        super.loadData();
        //如果用户已登录 进行网络请求获取用户信息
        if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
            getUserMsg();
        } else {
            tvNickname.setText("未登录");
            civAvatar.setImageResource(R.mipmap.icon_default_avatar);
        }
    }

    // R.id.tv_control_center,R.id.tv_shop_stationed, R.id.tv_service_manage,
    @OnClick({R.id.tv_my_coupon_card, R.id.rlyt_avatar, R.id.tv_window_manage, R.id.tv_my_homepage, R.id.tv_my_account, R.id.tv_my_shopping_order, R.id.tv_my_collection, R.id.tv_my_lottery, R.id.tv_shop_center, R.id.tv_milk_tea_order,  R.id.tv_account_setting,R.id.tv_control_center,R.id.tv_shop_stationed, R.id.tv_service_manage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rlyt_avatar://个人资料
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, PersonalDataActivity.class));
                }
                break;
            case R.id.tv_window_manage://橱窗管理
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, WindowManageActivity.class));
                }
                break;
            case R.id.tv_my_homepage://我的主页
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, UserDetailsActivity.class).putExtra(Constants.EXTRA_KEY_ENTER_TYPE,"0"));
                }
                break;
            case R.id.tv_my_account://我的账户
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, WalletActivity.class));
                }
                break;
            case R.id.tv_my_coupon_card://我的优惠卡
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, MyCouponCardActivity.class));
                }
                break;
            case R.id.tv_my_shopping_order://我的购物订单
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, MyShoppingOrderActivity.class));
                }
                break;
            case R.id.tv_my_collection://我的收藏
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, MyCollectionActivity.class));
                }
                break;
            case R.id.tv_my_lottery://我的抽奖
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, MyLotteryActivity.class));
                }
                break;
            case R.id.tv_shop_center://商户中心
                if ("2".equals(MyApplication.mPreferenceProvider.getUserType()) || "3".equals(MyApplication.mPreferenceProvider.getUserType())) {
                    startActivity(new Intent(mContext, ShopCenterActivity.class));
                } else {
                    // startActivity(new Intent(mContext, ShopCenterActivity.class));
                    ToastUtils.show(mContext, "您还不是商家，无法进入商户中心");
                }
                break;
            case R.id.tv_milk_tea_order://奶茶订单
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, MilkTeaOrderActivity.class));
                }
                break;
            case R.id.tv_shop_stationed://商户入驻
                startActivity(new Intent(mContext, ShopStationedActivity.class));
                break;
            case R.id.tv_service_manage://服务管理
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, ServiceManageActivity.class));
                }
                break;
            case R.id.tv_control_center://控制中心
                startActivity(new Intent(mContext, ControlCenterActivity.class));
                break;
            case R.id.tv_account_setting://账户设置
                startActivity(new Intent(mContext, SettingActivity.class));
                break;
        }
    }

    /**
     * 获取用户信息
     */
    private void getUserMsg() {

        BaseOkHttpClient.newBuilder()
                .addParam("token", MyApplication.mPreferenceProvider.getToken())
                .url(NetUrlUtils.GET_USER_INFO)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                //更新本地用户信息
                UserInfoBean getUserMsgBean = JSONUtils.jsonString2Bean(json, UserInfoBean.class);
                LoginCheckUtils.updateUserInfo(getUserMsgBean);
                //头像
                ImageUtils.getPic(Constants.IMAGE_BASE_URL + getUserMsgBean.getAvatar(), civAvatar, mContext, R.mipmap.icon_default_avatar);
                //昵称
                tvNickname.setText(getUserMsgBean.getNickname());
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

}
