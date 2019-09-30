package com.benben.yishanbang.ui.mine.activity;


import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.DateUtils;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.widget.CircleImageView;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.mine.adapter.WriteOffTeaOrderGoodsListAdapter;
import com.benben.yishanbang.ui.mine.bean.WriteOffTeaOrderDetailsBean;

import java.io.IOException;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 核销奶茶订单详情
 */
public class WriteOffTeaOrderDetailsActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.rlv_list)
    RecyclerView rlvList;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private WriteOffTeaOrderGoodsListAdapter mAdapter;
    private String mFoodCode;
    private String mOrderId;
    private String mUserId;
    //倒计时时间
    private long mDownTime;
    private CountDownTimer mCountDownTimer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_write_off_tea_order_details;
    }

    @Override
    protected void initData() {
        centerTitle.setText("订单详情");
        mFoodCode = getIntent().getStringExtra("foodCode");
        mOrderId = getIntent().getStringExtra("orderId");
        mUserId = getIntent().getStringExtra("userId");
        rlvList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new WriteOffTeaOrderGoodsListAdapter(mContext);
        rlvList.setAdapter(mAdapter);

        getOrderInfo();
    }

    //获取订单信息
    private void getOrderInfo() {

        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.WRITE_OFF_TEA_ORDER_INFO)
                .post()
                .addParam("orderId", mOrderId)
                .addParam("userId", mUserId)
                .addParam("foodCode", mFoodCode)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                WriteOffTeaOrderDetailsBean detailsBean = JSONUtils.jsonString2Bean(json, WriteOffTeaOrderDetailsBean.class);
                if (detailsBean != null) {
                    mAdapter.refreshList(detailsBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos());
                    ImageUtils.getPic(Constants.IMAGE_BASE_URL + detailsBean.getUserEntity().getAvatar(), civAvatar, mContext, R.mipmap.icon_default_avatar);
                    tvName.setText(detailsBean.getUserEntity().getNickname());
                    tvPhone.setText(detailsBean.getUserEntity().getMobile());
                    tvMoney.setText("¥" + detailsBean.getMilkTeaOrderVo().getGoodsMoney());
                    tvDate.setText("购买日期：" + detailsBean.getMilkTeaOrderVo().getOrderTime());
                    String orderTime = detailsBean.getMilkTeaOrderVo().getOrderTime();
                    countDownTime(orderTime);
                } else {
                    toast("获取订单信息失败，请重试");
                }


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

    /**
     * 核销奶茶
     */
    private void writeOffTea() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.AFTER_TEA_ORDER)
                .post()
                .addParam("orderId", mOrderId)
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                finish();
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


    //奶茶订单倒计时
    private void countDownTime(String orderTime) {
        Date orderDate = DateUtils.stringToDate(orderTime, "yyyy-MM-dd HH:mm:ss");
        Date currentDate = DateUtils.longToDate(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss");
        long min = DateUtils.minutesBetween(orderDate, currentDate);

        if (min >= 40) {
            tvCountDown.setVisibility(View.INVISIBLE);
        } else {
            mDownTime = (40 - min) * 60;
            mCountDownTimer = new CountDownTimer(mDownTime * 1000, 1000) {
                @Override
                public void onTick(long l) {
                    mDownTime--;
                    long min = (long) mDownTime / 60;
                    long second = mDownTime - min * 60;
                    tvCountDown.setText((min < 10 ? "0" + min : min) + ":" + (second < 10 ? "0" + second : second));
                }

                @Override
                public void onFinish() {
                    tvCountDown.setVisibility(View.INVISIBLE);
                }
            }.start();
        }

    }

    @OnClick({R.id.rl_back, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_confirm:
                writeOffTea();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null) {
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }
}
