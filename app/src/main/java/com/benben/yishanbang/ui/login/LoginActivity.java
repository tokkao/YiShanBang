package com.benben.yishanbang.ui.login;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.benben.commoncore.utils.AppManager;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.MainActivity;
import com.benben.yishanbang.ui.login.bean.UserLoginSuccessBean;
import com.benben.yishanbang.utils.LoginCheckUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;
import okhttp3.Call;

/**
 * Created by Android Studio.
 * User: feng
 * Date: 2019/8/5
 * Time: 10:49
 * 登录(账号密码登录)
 */
public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.edt_pwd)
    EditText edtPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_forget)
    TextView tvForget;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.iv_back, R.id.btn_login, R.id.tv_forget, R.id.tv_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_login:
                login();
                break;
            //忘记密码
            case R.id.tv_forget:
                MyApplication.openActivityForResult(mContext, ForgetActivity.class, 101);
                break;
            //注册账号
            case R.id.tv_register:
                MyApplication.openActivityForResult(mContext, RegisterActivity.class, 102);
                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String phone = edtPhone.getText().toString().trim();
        String pwd = edtPwd.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(mContext, "请输入手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!InputCheckUtil.checkPhoneNum(phone)) {
            Toast.makeText(mContext, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(mContext, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtPwd.getText().toString().contains(" ")) {
            toast("密码不能包含空格字符");
            return;
        }
        if (StringUtils.isEmpty(pwd)) {
            toast("密码不能为空");
            return;
        }
        if (pwd.length() < 6 || pwd.length() > 12) {
            toast("密码的长度应为6~12字符");
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("password", pwd)
                .url(NetUrlUtils.PASSWORD_LOGIN)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                UserLoginSuccessBean userLoginSuccessBean = JSONUtils.jsonString2Bean(json, UserLoginSuccessBean.class);
                //保存用户信息到本地
                LoginCheckUtils.saveLoginInfo(userLoginSuccessBean);
                loginJMessageIM();
                //进入主页
                AppManager.getInstance().finishAllActivity();
                MyApplication.openActivity(mContext, MainActivity.class);
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

    //登录极光IM
    private void loginJMessageIM() {
        JMessageClient.login(MyApplication.mPreferenceProvider.getIMUserName(), "123456", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i != 0) return;
                LogUtils.e(TAG, "i = " + i + "***s =" + s);
                //修改昵称
                UserInfo userInfo = JMessageClient.getMyInfo();
                userInfo.setNickname(MyApplication.mPreferenceProvider.getUserName());
                JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
                    @Override
                    public void gotResult(int i, String s) {

                    }
                });

                //修改头像
                try {
                    JMessageClient.updateUserAvatar(ImageUtils.saveFile(ImageUtils.getBitMBitmap(MyApplication.mPreferenceProvider.getPhoto()), System.currentTimeMillis() + ""), new BasicCallback() {
                        @Override
                        public void gotResult(int i, String s) {

                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            if (data != null) {
                edtPhone.setText("" + data.getStringExtra("phone"));
                edtPwd.setText("" + data.getStringExtra("pwd"));
            }
        }
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
