package com.benben.yishanbang.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.utils.TimerUtil;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 14:15
 * 忘记密码
 */
public class ForgetActivity extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.edt_confirm_pwd)
    EditText edtConfirmPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    private String mVerifyCode;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back, R.id.tv_code, R.id.btn_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //返回
            case R.id.iv_back:
                finish();
                break;
            //发短信
            case R.id.tv_code:
                sendMessage();
                break;
            //忘记密码
            case R.id.btn_login:
                submit();
                break;
        }
    }

    /**
     * 确认忘记密码
     */
    private void submit() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
        String pwd = edtPwd.getText().toString().trim();
        String confirmPwd = edtConfirmPwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(mContext, "请输入验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtPwd.getText().toString().contains(" ") || edtConfirmPwd.getText().toString().contains(" ")) {
            toast("密码不能包含空格字符");
            return;
        }
        if (StringUtils.isEmpty(pwd) || StringUtils.isEmpty(confirmPwd)) {
            toast("密码不能为空");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 12 || confirmPwd.length() < 6 || confirmPwd.length() > 12) {
            toast("密码的长度应为6~12字符");
            return;
        }
        if (!pwd.equals(confirmPwd)) {
            Toast.makeText(mContext, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(mVerifyCode)) {
            toast("请先获取验证码");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("password", pwd)
                .addParam("event", 2)
                .addParam("captcha", code)
                .url(NetUrlUtils.FORGET_PASSWORD)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);

                Intent intent = new Intent();
                intent.putExtra("phone", "" + phone);
                intent.putExtra("pwd", "" + pwd);
                setResult(RESULT_OK, intent);
                finish();

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
     * 发短信
     */
    private void sendMessage() {
        String phone = edtPhone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        TimerUtil timerUtil = new TimerUtil(tvCode);
        timerUtil.timers();


        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("event", "2")//1:短信登录、2:忘记密码、3:修改密码、4:修改支付密码、5:换绑手机
                .url(NetUrlUtils.GET_VERIFY_CODE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                mVerifyCode = json;
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

    @Override
    protected int getStatusBarColor() {
        return R.color.white;
    }

    @Override
    protected boolean needStatusBarDarkText() {
        return true;
    }
}
