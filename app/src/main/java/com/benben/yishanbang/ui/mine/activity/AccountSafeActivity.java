package com.benben.yishanbang.ui.mine.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.benben.yishanbang.bean.TabBean;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.widget.VerifyCodeButton;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 修改支付/登录密码
 */
public class AccountSafeActivity extends BaseActivity {

    private static final String TAG = "AccountSafeActivity";

    @BindView(R.id.center_title)
    TextView centerTitle;
    @BindView(R.id.ctl_account_tab)
    CommonTabLayout ctlAccountTab;
    @BindView(R.id.edt_account_safe_phone)
    EditText edtAccountSafePhone;
    @BindView(R.id.edt_account_safe_verify_code)
    EditText edtAccountSafeVerifyCode;
    @BindView(R.id.btn_account_safe_get_verify_code)
    VerifyCodeButton btnAccountSafeGetVerifyCode;
    @BindView(R.id.edt_account_safe_new_pwd)
    EditText edtAccountSafeNewPwd;
    @BindView(R.id.edt_account_safe_re_pwd)
    EditText edtAccountSafeRePwd;
    @BindView(R.id.btn_account_safe_confirm)
    Button btnAccountSafeConfirm;


    //标签：登录密码
    private final String LABEL_LOGIN = "Login";
    //标签：支付密码
    private final String LABEL_PAY = "Pay";
    //当前选择的标签
    private String currentLabel = LABEL_LOGIN;

    //获取验证返回结果
    private String mVerifyCode;

    @Override
    protected void initData() {
        centerTitle.setText("设置");
        //初始化标签
        initFlyTab();
        edtAccountSafePhone.setEnabled(false);
        edtAccountSafePhone.setText(MyApplication.mPreferenceProvider.getMobile());
    }

    private void initFlyTab() {
        //标签集合
        ArrayList<CustomTabEntity> lstAccountTab = new ArrayList<>();
        lstAccountTab.add(new TabBean<>(LABEL_LOGIN, "登录密码修改"));
        lstAccountTab.add(new TabBean<>(LABEL_PAY, "支付密码修改"));
        ctlAccountTab.setTabData(lstAccountTab);
        ctlAccountTab.setCurrentTab(0);
        ctlAccountTab.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                TabBean<String> data = (TabBean<String>) lstAccountTab.get(position);
                currentLabel = data.getTabKey();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_account_safe;
    }

    @OnClick({R.id.rl_back,
            R.id.btn_account_safe_get_verify_code,
            R.id.btn_account_safe_confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.btn_account_safe_get_verify_code:
                if (LABEL_LOGIN.equals(currentLabel)) {
                    getVerifyCode(3);//登录
                } else {
                    getVerifyCode(4);//支付
                }
                break;
            case R.id.btn_account_safe_confirm:
                doResetPwd();
                break;
        }
    }

    //修改密码
    private void doResetPwd() {
        String phone = edtAccountSafePhone.getText().toString().trim();//手机号
        String captcha = edtAccountSafeVerifyCode.getText().toString().trim();//验证码
        String newPwd = edtAccountSafeNewPwd.getText().toString().trim();//新密码
        String rePwd = edtAccountSafeRePwd.getText().toString().trim();//确认新密码
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号");
            return;
        }
        if (TextUtils.isEmpty(captcha)) {
            toast("请输入验证码");
            return;
        }
        if (TextUtils.isEmpty(newPwd) || TextUtils.isEmpty(rePwd)) {
            toast("请输入新密码");
            return;
        }
        if (newPwd.length() < 6 || newPwd.length() > 12) {
            toast("请输入6~12位密码");
            return;
        }

        if (!newPwd.equals(rePwd)) {
            toast("两次输入的密码不一致");
            return;
        }
        mVerifyCode = captcha;
        if (StringUtils.isEmpty(mVerifyCode)) {
            toast("请先获取验证码");
            return;
        }
        if (LABEL_LOGIN.equals(currentLabel)) {
            doRestLoginPwd(phone, captcha, newPwd);
        } else {
            doRestPayPwd(phone, captcha, newPwd);
        }
    }

    //修改支付密码
    private void doRestPayPwd(String phone, String captcha, String newPwd) {
        BaseOkHttpClient.newBuilder()
                .addParam("event", 4)
                .addParam("payPassword", newPwd)
                .addParam("mobile", phone)
                .addParam("captcha", captcha)
                .url(NetUrlUtils.SET_PAY_PASSWORD)
                .json().post().build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                toast(msg);
                finish();
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
                LogUtils.e(TAG, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }
        });
    }

    //修改登录密码
    private void doRestLoginPwd(String phone, String captcha, String newPwd) {
        BaseOkHttpClient.newBuilder()
                .addParam("event", 3)
                .addParam("newPassword", newPwd)
                .addParam("mobile", phone)
                .addParam("captcha", captcha)
                .url(NetUrlUtils.CHANGE_PASSWORD_LOGIN)
                .post().build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                toast(msg);
                finish();
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
                LogUtils.e(TAG, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }
        });
    }

    //获取验证码
    private void getVerifyCode(int scene) {
        String phone = edtAccountSafePhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            toast("请输入手机号！");
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            toast("请输入正确的手机号");
            return;
        }
        btnAccountSafeGetVerifyCode.startTimer();
        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("event", scene)//事件
                .url(NetUrlUtils.GET_VERIFY_CODE)
                .post().build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                toast("发送验证码成功");

                mVerifyCode = JSONUtils.toJsonString(result);
            }

            @Override
            public void onError(int code, String msg) {
                toast(msg);
                LogUtils.e(TAG, msg);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(TAG, e.getMessage());
            }
        });
    }
}
