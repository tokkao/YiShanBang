package com.benben.yishanbang.ui.mine.activity;

import android.widget.TextView;

import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.suke.widget.SwitchButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: //支付设置
 */
public class PaySettingActivity extends BaseActivity {
    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.sb_pwd)
    SwitchButton sbPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_pay_setting;
    }

    @Override
    protected void initData() {
        centerTitle.setText("支付设置");
        sbPwd.setChecked(MyApplication.mPreferenceProvider.getClosePayPwd());
        sbPwd.setOnCheckedChangeListener((view, isChecked) ->

                BaseOkHttpClient.newBuilder()
                        .url(NetUrlUtils.WALLET_IS_PASSWORD_PAY)
                        .addParam("type", isChecked ? "1" : "0")
                        .post()
                        .build().enqueue(mContext, new BaseCallBack() {
                    @Override
                    public void onSuccess(Object o, String msg) {
                        MyApplication.mPreferenceProvider.setClosePayPwd(isChecked);
                        PaySettingActivity.this.finish();
                        toast(msg);
                    }

                    @Override
                    public void onError(int code, String msg) {
                        toast(msg);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                })
        );
    }


    @OnClick(R.id.rl_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
