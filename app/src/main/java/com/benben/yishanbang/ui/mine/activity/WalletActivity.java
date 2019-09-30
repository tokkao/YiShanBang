package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benben.commoncore.utils.ActivityManagerUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.PayPopupWindow;
import com.benben.yishanbang.utils.PayUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //钱包
 */
public class WalletActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.tv_wxpay)
    TextView tvWxpay;
    private int mPayWay = -1;

    private PayPopupWindow mPayPopupWindow;
    //支付工具类
    private PayUtils mPayUtils;

    private String mOrderId;
    private String mTotalPrice;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_wallet;
    }

    @Override
    protected void initData() {
        centerTitle.setText("钱包");
        rightTitle.setText("明细");

        mPayUtils = new PayUtils(mContext);
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

        ActivityManagerUtils.addOneActivity(this);
        getBalance();
    }

    private void getBalance() {
        BaseOkHttpClient.newBuilder()
                .url(NetUrlUtils.WALLET_BALANCE)
                .post()
                .json()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                tvBalance.setText("¥ " + s);

            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    @OnClick({R.id.rl_back, R.id.right_title, R.id.rlyt_withdraw, R.id.tv_alipay, R.id.tv_wxpay, R.id.rlyt_recharge, R.id.tv_my_discount_card, R.id.btn_pay_setting})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.right_title://明细
                startActivity(new Intent(mContext, AccountDetailsActivity.class));
                break;
            case R.id.rlyt_withdraw://提现
                startActivity(new Intent(mContext, WithdrawActivity.class));
                break;
            case R.id.tv_alipay://支付宝
                tvAlipay.setSelected(true);
                tvWxpay.setSelected(false);
                mPayWay = 0;
                break;
            case R.id.tv_wxpay://微信
                tvAlipay.setSelected(false);
                tvWxpay.setSelected(true);
                mPayWay = 1;
                break;
            case R.id.rlyt_recharge://充值
                createOrder();
                break;
            case R.id.tv_my_discount_card: //我的优惠卡
                startActivity(new Intent(mContext, MyCouponCardActivity.class));
                break;
            case R.id.btn_pay_setting: //支付设置
                startActivity(new Intent(mContext, PaySettingActivity.class));
                break;
        }
    }

    /**
     * 生成订单
     */
    private void createOrder() {
        String money = edtMoney.getText().toString().trim();
        if (StringUtils.isEmpty(money)) {
            toast("你还没有输入充值金额");
            return;
        }
        if (Integer.parseInt(money) <= 0) {
            toast("充值金额不能小于1元");
            return;
        }
        if (mPayWay == -1) {
            toast("你还没有选择支付方式");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("money", money)
                .url(NetUrlUtils.RECHARGE_CREATE_ORDER_ID)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    mOrderId = jsonObject.optString("orderNo");
                    mTotalPrice = jsonObject.optString("orderMoney");
                    recharge();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String msg) {

            }

            @Override
            public void onFailure(Call call, IOException e) {

            }
        });
    }

    /**
     * 充值
     */
    private void recharge() {
        if (StringUtils.isEmpty(mOrderId) || StringUtils.isEmpty(mTotalPrice)) {
            toast("请确认充值金额");
            return;
        }
        mPayPopupWindow.setTypePrice("", mTotalPrice);
        mPayPopupWindow.showAtLocation(edtMoney, Gravity.BOTTOM, 0, 0);
    }  //支付宝支付

    private void aliPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalPrice)) ? "0" : String.valueOf(mTotalPrice))//
                .addParam("orderName", "naicha")//body 随便写
                .addParam("body", "test")//body 随便写
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

    //微信支付
    private void wxPay() {
        BaseOkHttpClient.newBuilder()
                .addParam("orderId", mOrderId)//订单id
                .addParam("orderMoney", StringUtils.isEmpty(String.valueOf(mTotalPrice)) ? "0" : String.valueOf(mTotalPrice))//订单价格
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
}
