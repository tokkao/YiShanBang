package com.benben.yishanbang.ui.mine.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.widget.VerifyCodeButton;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 绑定手机号
 */
public class BindMobileActivity extends BaseActivity {


    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.edt_current_phone)
    EditText edtCurrentPhone;
    @BindView(R.id.edt_verify_code)
    EditText edtVerifyCode;
    @BindView(R.id.edt_new_phone)
    EditText edtNewPhone;
    @BindView(R.id.edt_re_new_phone)
    EditText edtReNewPhone;
    @BindView(R.id.btn_get_verify_code)
    VerifyCodeButton btnGetVerifyCode;

    //获取验证返回结果
    private String mVerifyCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bind_mobile;
    }

    @Override
    protected void initData() {
        centerTitle.setText("修改手机号");
        edtCurrentPhone.setEnabled(false);
        edtCurrentPhone.setText(MyApplication.mPreferenceProvider.getMobile());
    }

    /**
     * 修改手机号
     */
    private void doRestPwd() {
        String phone = edtCurrentPhone.getText().toString().trim();
        String code = edtVerifyCode.getText().toString().trim();
        String newPhone = edtNewPhone.getText().toString().trim();
        String rePhone = edtReNewPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(newPhone) || TextUtils.isEmpty(rePhone)) {
            toast("请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone) || !InputCheckUtil.checkPhoneNum(newPhone) || !InputCheckUtil.checkPhoneNum(rePhone)) {
            toast("请输入正确的手机号");
            return;
        }
        if (TextUtils.isEmpty(code)) {
            toast("请输入验证码");
            return;
        }

        if (!newPhone.equals(rePhone)) {
            toast("两次输入的手机号不一致");
            return;
        }
        if (StringUtils.isEmpty(mVerifyCode)) {
            toast("请先获取验证码");
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("event", 5)
                .addParam("mobile", phone)
                .addParam("newMobile", newPhone)
                .addParam("captcha", code)
                .url(NetUrlUtils.CHANGE_MOBILE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                toast("绑定成功");
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

    /**
     * 获取验证码
     */
    private void getVerifyCode() {
        String phone = edtCurrentPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            toast("请输入正确的手机号");
            return;
        }


        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("event", 5)
                .url(NetUrlUtils.GET_VERIFY_CODE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                toast("发送验证码成功");
                mVerifyCode = JSONUtils.toJsonString(result);
                //开启倒计时
                btnGetVerifyCode.startTimer();
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
                LogUtils.e(BindMobileActivity.class.getSimpleName(), msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(BindMobileActivity.class.getSimpleName(), e.getMessage());
            }
        });
    }


    @OnClick({R.id.rl_back, R.id.btn_get_verify_code, R.id.btn_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_get_verify_code://获取验证码
                getVerifyCode();
                break;
            case R.id.btn_confirm://确认
                doRestPwd();
                break;
        }
    }
}
