package com.benben.yishanbang.ui.tea.activity;

import android.content.Intent;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.RxBus;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.ui.mine.adapter.MilkTeaOrderDetailsAdapter;
import com.benben.yishanbang.ui.mine.bean.MilkTeaDetailsBean;
import com.benben.yishanbang.ui.tea.adapter.ConfirmOrderAdapter;
import com.benben.yishanbang.utils.PayListenerUtils;
import com.benben.yishanbang.utils.PayResultListener;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2019/8/12 0012
 * Describe:确认订单页面
 */
public class ConfirmOrderActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView tvCenterTitle;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    @BindView(R.id.tv_stores_name)
    TextView tvStoresName;
    @BindView(R.id.tv_stores_address)
    TextView tvStoresAddress;
    @BindView(R.id.rlv_confirm_order)
    RecyclerView rlvConfirmOrder;
    @BindView(R.id.btn_confirm_pay)
    Button btnConfirmPay;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;

    private String mShopId, mTotalGoodNum, mTotalPriceSum, mIds, mOrederType = "3", mUnpaidOrderId;

    private PayPopupWindow mPayPopupWindow;//支付弹窗
    private ConfirmOrderAdapter mConfirmOrderAdapter;//确认订单适配器

    String mFoodCode, mCodeImage;

    private MilkTeaOrderDetailsAdapter mMilkTeaOrderDetailsAdapter;

    List<MilkTeaDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean> mMilkTeaOrderGoodsVosBeans = new ArrayList<>();
    private String mOrderNumber;
    double mTotalMoney;
    //支付工具类
    private PayUtils mPayUtils;
    private CountDownTimer mPayTimer;
    private String mGoodsIds;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        mShopId = intent.getStringExtra("mShopId");
        mTotalGoodNum = intent.getStringExtra("goodsNum");
        mTotalPriceSum = intent.getStringExtra("priceSum");
        mIds = intent.getStringExtra("ids");

        //标题
        tvCenterTitle.setText("确认订单");
        mPayUtils = new PayUtils(mContext);

        rlvConfirmOrder.setLayoutManager(new LinearLayoutManager(mContext));
        mMilkTeaOrderDetailsAdapter = new MilkTeaOrderDetailsAdapter(mContext);
        rlvConfirmOrder.setAdapter(mMilkTeaOrderDetailsAdapter);

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

        PayListenerUtils.getInstance(mContext).addListener(new PayResultListener() {
            @Override
            public void onPaySuccess() {
                //跳转到订单详情页面
                Intent intent = new Intent(mContext, OrderDetailsActivity.class);
                intent.putExtra("id", mUnpaidOrderId);//订单id
                intent.putExtra("mShopId", mShopId);//店铺id
                intent.putExtra(Constants.EXTRA_KEY_ENTER_TYPE,"0");
                mContext.startActivity(intent);
                finish();
            }

            @Override
            public void onPayError() {
                toast("支付失败");
            }

            @Override
            public void onPayCancel() {
                toast("支付取消");
            }
        });

        //获取未支付订单详情
        getUnpaidOrder();
        startPayTimer();
    }

    //3分钟支付倒计时
    private void startPayTimer() {

        mPayTimer = new CountDownTimer(3 * 60 * 1000, 1000) {
            /**
             * 固定间隔被调用,就是每隔countDownInterval会回调一次方法onTick
             * @param millisUntilFinished
             */
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("支付倒计时：" + formatTime(millisUntilFinished));
            }

            /**
             * 倒计时完成时被调用
             */
            @Override
            public void onFinish() {
                tvCountDown.setText("订单已超时，请重新下单");
                btnConfirmPay.setEnabled(false);
                btnConfirmPay.setAlpha(0.5f);
            }
        };
        mPayTimer.start();
    }

    @OnClick({R.id.rl_back, R.id.btn_confirm_pay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back://返回上一页
                onBackPressed();
                break;
            case R.id.btn_confirm_pay://确认并支付
                mPayPopupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
                mPayPopupWindow.setTypePrice("共计商品：" + mTotalGoodNum, mTotalPriceSum);
                break;
        }
    }

    //刷新选择商品页面数据
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RxBus.getInstance().post(Constants.REFRESH_TEA_SHOP_GOODS_INFO);
    }

    //支付宝支付
    private void aliPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNumber)//优惠卡id
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalMoney)) ? "0" : String.valueOf(mTotalMoney))//
                .addParam("orderName", "naicha")//body 随便写
                .addParam("body", "test")//body 随便写
                .url(NetUrlUtils.ALIPAY)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                if (StringUtils.isEmpty(json)) {
                    toast("获取订单信息失败，请重试");
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

    //微信支付
    private void wxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderNumber)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalMoney)) ? "0" : String.valueOf(mTotalMoney))//订单价格
                .addParam("body", "naicha")//body体内容随便传
                .url(NetUrlUtils.MILK_TEA_WECHAT_PAY)
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


    //获取未支付订单数据
    private void getUnpaidOrder() {
        BaseOkHttpClient.newBuilder()
                .addParam("shopId", mShopId)//店铺Id
                .addParam("goodsNum", mTotalGoodNum)//商品件数
                .addParam("priceSum", mTotalPriceSum)//商品单价
                .addParam("ids", mIds)//购物车id
                .addParam("orderType", mOrederType)//订单类型
                .url(NetUrlUtils.UNPAID_GOODS_ORDER)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                MilkTeaDetailsBean mMilkTeaDetailsBean = JSONUtils.jsonString2Bean(json, MilkTeaDetailsBean.class);
                if (mMilkTeaDetailsBean == null) return;

                tvStoresName.setText(mMilkTeaDetailsBean.getShopNameAndAddress().getShopName());
                tvStoresAddress.setText(mMilkTeaDetailsBean.getShopNameAndAddress().getShopAddress());
                mUnpaidOrderId = mMilkTeaDetailsBean.getMilkTeaOrderVo().getOrderId();//订单id
                mOrderNumber = mMilkTeaDetailsBean.getMilkTeaOrderVo().getOrderNo();//订单编号

                mFoodCode = mMilkTeaDetailsBean.getMilkTeaOrderVo().getFoodcode();
                mCodeImage = mMilkTeaDetailsBean.getMilkTeaOrderVo().getCodeImage();

                for (int i = 0; i < mMilkTeaDetailsBean.getMilkTeaOrderVo().getMilkTeaOrderGoodsVos().size(); i++) {

                    MilkTeaDetailsBean.MilkTeaOrderVoBean.MilkTeaOrderGoodsVosBean mMilkTeaOrderGoodsVosBean =
                            new MilkTeaDetailsBean().new MilkTeaOrderVoBean().new MilkTeaOrderGoodsVosBean();

                    mMilkTeaOrderGoodsVosBean.setDelPrice(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getDelPrice());

                    mMilkTeaOrderGoodsVosBean.setGoodsCount(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getGoodsCount());

                    mMilkTeaOrderGoodsVosBean.setGoodsName(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getGoodsName());

                    mMilkTeaOrderGoodsVosBean.setImgUrl(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getImgUrl());

                    mMilkTeaOrderGoodsVosBean.setPrice(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getPrice());

                    mMilkTeaOrderGoodsVosBean.setSpec(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getSpec());

                    mMilkTeaOrderGoodsVosBean.setSugar(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getSugar());

                    mMilkTeaOrderGoodsVosBean.setTemp(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getTemp());

                    mMilkTeaOrderGoodsVosBeans.add(mMilkTeaOrderGoodsVosBean);


                    String mSingleCount = Integer.toString(mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getGoodsCount());

                    double mSinglePrice = mMilkTeaDetailsBean.getMilkTeaOrderVo()
                            .getMilkTeaOrderGoodsVos().get(i).getPrice();

                    double mGoodPrice = mSinglePrice * Double.parseDouble(mSingleCount);

                    mTotalMoney = mTotalMoney + mGoodPrice;
                }

                mMilkTeaOrderDetailsAdapter.refreshList(mMilkTeaOrderGoodsVosBeans);

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


    /**
     * 将毫秒转化为 分钟：秒 的格式
     *
     * @param millisecond 毫秒
     * @return
     */
    public String formatTime(long millisecond) {
        int minute;//分钟
        int second;//秒数
        minute = (int) ((millisecond / 1000) / 60);
        second = (int) ((millisecond / 1000) % 60);
        if (minute < 10) {
            if (second < 10) {
                return "0" + minute + ":" + "0" + second;
            } else {
                return "0" + minute + ":" + second;
            }
        } else {
            if (second < 10) {
                return minute + ":" + "0" + second;
            } else {
                return minute + ":" + second;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPayTimer.cancel();
        mPayTimer = null;
    }
}
