package com.benben.yishanbang.ui.service.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.MainActivity;
import com.benben.yishanbang.ui.home.activity.VideoPlayActivity;
import com.benben.yishanbang.ui.service.bean.PaySurplusMoneyOrderBean;
import com.benben.yishanbang.ui.tea.activity.UserDetailsActivity;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 用户短视频页面
 */
public class UserShortVideoActivity extends BaseActivity {

    private static final String TAG = "UserShortVideoActivity";
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.llyt_no_data)
    LinearLayout llytNoData;
    @BindView(R.id.iv_video_preview)
    ImageView ivVideoPreview;
    @BindView(R.id.iv_play_video)
    ImageView IvPlayVideo;
    @BindView(R.id.rlyt_select_user)
    RelativeLayout rlytSelectUser;
    @BindView(R.id.rlyt_video)
    RelativeLayout rlytVideo;
    @BindView(R.id.cv_details)
    CardView cvDetails;
    private int sx;
    private int sy;
    private PayPopupWindow mPayPopupWindow;
    private PayUtils mPayUtils;
    private String mUserId;
    //服务id
    private String mServiceId;
    private String mVideoUrl;
    private String mOrderNum;
    private String mPrice;
    //是否是聊天订单
    private boolean isIM;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_short_video;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initData() {
        String userName = getIntent().getStringExtra("user_name");
        mServiceId = getIntent().getStringExtra("service_id");
        mUserId = getIntent().getStringExtra("user_id");
        mOrderNum = getIntent().getStringExtra("order_num");
        isIM = getIntent().getBooleanExtra("isIM", false);
        centerTitle.setText(userName);
        mPayPopupWindow = new PayPopupWindow(mContext, new PayPopupWindow.OnPayCallback() {
            @Override
            public void alipay() {
                aliPay();
            }

            @Override
            public void wxpay() {
                wxPay();
            }
        });
        mPayUtils = new PayUtils(mContext);
        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                toast("支付成功");
                startActivity(new Intent(mContext, MainActivity.class));
                finish();
            }

            @Override
            public void onPayError() {

            }

            @Override
            public void onPayCancel() {

            }
        });


        getVideo();
    }

    //意向金支付宝支付
    private void aliPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNum)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mPrice) ? "0" : mPrice)//
                .addParam("orderName", "payOk")
                .addParam("body", "test")
                .url(NetUrlUtils.ALIPAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtils.isEmpty(json)) {
                    ToastUtils.show(mContext, "获取订单信息失败，请重试");
                    return;
                }
                mPayUtils.aliPay(json, mContext);
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }


    //意向金微信支付
    private void wxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNum)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(mPrice) ? "0" : mPrice)//
                .addParam("body", "payOk")//body 随便写
                .url(NetUrlUtils.WX_PAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                PayBean payBean = new Gson().fromJson(json, PayBean.class);
                mPayUtils.wxPay(payBean);
            }

            @Override
            public void onError(int code, String msg) {
                ToastUtils.show(mContext, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
            }
        });
    }

    //获取用户短视频
    private void getVideo() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.GET_USER_SHORT_VIDEO)
                .addParam("userId", mUserId)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                mVideoUrl = s;
                ImageUtils.loadCover(ivVideoPreview, mVideoUrl, mContext);
                llytNoData.setVisibility(View.GONE);
                rlytVideo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError(int code, String msg) {
                llytNoData.setVisibility(View.VISIBLE);
                rlytVideo.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                llytNoData.setVisibility(View.VISIBLE);
                rlytVideo.setVisibility(View.GONE);
            }
        });
    }


    //选择用户
    private void selectUser() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.SELECT_SERVICE_USER)
                .addParam("id", mServiceId)
                .addParam("userId", mUserId)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                paySurplusMoney();
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });


    }

    //支付剩余金额
    private void paySurplusMoney() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.PAY_SURPLUS_MONEY)
                .addParam("taskId", mServiceId)
                .addParam("orderNo", mOrderNum)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                PaySurplusMoneyOrderBean paySurplusMoneyOrderBean = JSONUtils.jsonString2Bean(s, PaySurplusMoneyOrderBean.class);
                if(paySurplusMoneyOrderBean == null){
                    toast(msg);
                    return;
                }
                mPrice = paySurplusMoneyOrderBean.getPayPrice();
                mOrderNum = paySurplusMoneyOrderBean.getOrderNo();
                mPayPopupWindow.setTypePrice(paySurplusMoneyOrderBean.getName(), paySurplusMoneyOrderBean.getPayPrice());
                mPayPopupWindow.showAtLocation(getWindow().getDecorView().getRootView(), Gravity.BOTTOM, 0, 0);
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }


    @OnClick({R.id.rl_back, R.id.iv_video_preview, R.id.iv_play_video, R.id.rlyt_select_user,R.id.rlyt_bottom_option})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.iv_video_preview:
                break;
            case R.id.iv_play_video:
                Bundle bundle = new Bundle();
                bundle.putString("video", mVideoUrl);
                MyApplication.openActivity(mContext, VideoPlayActivity.class, bundle);
                break;
            case R.id.rlyt_select_user:
                selectUser();
                break;
            case R.id.rlyt_bottom_option:
                Intent intent = new Intent(mContext, UserDetailsActivity.class);
                intent.putExtra("isIM",isIM);
                intent.putExtra("user_id",mUserId);
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE, 1);
                startActivity(intent);
                break;
        }
    }
}
