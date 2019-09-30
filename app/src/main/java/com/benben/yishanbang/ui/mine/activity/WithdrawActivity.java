package com.benben.yishanbang.ui.mine.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benben.commoncore.utils.ActivityManagerUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //提现
 */
public class WithdrawActivity extends BaseActivity {

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.edt_money)
    EditText edtMoney;
    @BindView(R.id.edt_mobile)
    EditText edtMobile;
    @BindView(R.id.tv_alipay)
    TextView tvAlipay;
    @BindView(R.id.tv_wxpay)
    TextView tvWxpay;

    private int mPayWay = -1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw;
    }

    @Override
    protected void initData() {
        centerTitle.setText("提现");
        getBalance();
    }


    private void withdraw() {
        String money = edtMoney.getText().toString().trim();
        String mobile = edtMobile.getText().toString().trim();
        if (StringUtils.isEmpty(money)) {
            toast("你还没有输入提现金额");
            return;
        }
        if (Integer.parseInt(money) <= 0) {
            toast("提现金额不能小于1元");
            return;
        }
        if (mPayWay == -1) {
            toast("你还没有选择提现账户");
            return;
        }
        if (StringUtils.isEmpty(mobile)) {
            toast("你还没有输入提现账户");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("money", money)
                .addParam("mobile", mobile)
                .addParam("type", mPayWay)
                .url(NetUrlUtils.WALLET_WITHDRAW)
                .post()
                .json()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String s, String msg) {
                toast(msg);
                ActivityManagerUtils.clear();
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


    @OnClick({R.id.rl_back, R.id.tv_alipay, R.id.tv_wxpay, R.id.rlyt_confirm, R.id.tv_all_withdraw})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_alipay://支付宝
                tvAlipay.setSelected(true);
                tvWxpay.setSelected(false);
                mPayWay = 1;
                break;
            case R.id.tv_wxpay://微信
                tvAlipay.setSelected(false);
                tvWxpay.setSelected(true);
                mPayWay = 2;
                break;
            case R.id.rlyt_confirm:
                withdraw();
                break;
            case R.id.tv_all_withdraw:
                if (StringUtils.isEmpty(tvBalance.getText().toString())) {
                    toast("余额不足，无法提现");
                    return;
                }
                edtMoney.setText(tvBalance.getText());
                break;
        }
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
}
