package com.benben.yishanbang.ui.login;

import android.Manifest;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benben.commoncore.utils.AppManager;
import com.benben.commoncore.utils.ImageUtils;
import com.benben.commoncore.utils.InputCheckUtil;
import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StyledDialogUtils;
import com.benben.commoncore.utils.ToastUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.config.Constants;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.pop.BindingPopupWindow;
import com.benben.yishanbang.ui.MainActivity;
import com.benben.yishanbang.ui.login.bean.UserLoginSuccessBean;
import com.benben.yishanbang.utils.LoginCheckUtils;
import com.benben.yishanbang.utils.PermissionUtils;
import com.benben.yishanbang.utils.TimerUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.Map;

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
 * 登录（验证码登录）
 */
public class LoginCodeActivity extends BaseActivity {

    private static final String TAG = "LoginCodeActivity";
    @BindView(R.id.iv_delete)
    ImageView ivDelete;
    @BindView(R.id.edt_phone)
    EditText edtPhone;
    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.tv_pwd_login)
    TextView tvPwdLogin;
    @BindView(R.id.tv_other)
    TextView tvOther;
    @BindView(R.id.tv_wx)
    TextView tvWx;
    @BindView(R.id.tv_qq)
    TextView tvQq;
    private int mLoginType;//登录类型 1qq 2微信
    private BindingPopupWindow mBindingPopupWindow;//三方登录绑定手机号
    private String mVerifyCode;
    private String mOpenId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login_code;
    }

    @Override
    protected void initData() {

        edtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ivDelete.setVisibility(charSequence.toString().length() > 0 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    //三方登录绑定手机号
    private void bindMobile(String phoneNum, String verifyCode) {
        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phoneNum)
                .addParam("captcha", verifyCode)
                .addParam("event", 7)
                .addParam("openId", mOpenId)
                .addParam("platform", mLoginType)//平台类型('1':QQ,'2':微信,'3':微博)
                .url(NetUrlUtils.THIRD_LOGIN_BIND_MOBILE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                UserLoginSuccessBean userLoginSuccessBean = JSONUtils.jsonString2Bean(json, UserLoginSuccessBean.class);
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
                if (mBindingPopupWindow != null) {
                    mBindingPopupWindow.showAtLocation(tvWx, Gravity.CENTER, 0, 0);
                }

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


    @OnClick({R.id.iv_delete, R.id.tv_code, R.id.btn_login, R.id.tv_pwd_login, R.id.tv_wx, R.id.tv_qq})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //清空手机号
            case R.id.iv_delete:
                edtPhone.setText("");
                break;
            //发短信
            case R.id.tv_code:
                sendMessage();
                break;
            //登录
            case R.id.btn_login:
                login();
                break;
            //密码登录
            case R.id.tv_pwd_login:
                MyApplication.openActivity(mContext, LoginActivity.class);
                break;
            //微信
            case R.id.tv_wx:
                PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_PHONE_STATE, 101, new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        loginByThirdPlatform(SHARE_MEDIA.WEIXIN);
                    }
                });

                break;
            //QQ
            case R.id.tv_qq:
                PermissionUtils.checkAndRequestPermission(mContext, Manifest.permission.READ_PHONE_STATE, 101, new PermissionUtils.PermissionRequestSuccessCallBack() {
                    @Override
                    public void onHasPermission() {
                        loginByThirdPlatform(SHARE_MEDIA.QQ);
                    }
                });

                break;
        }
    }

    /**
     * 登录
     */
    private void login() {
        String phone = edtPhone.getText().toString().trim();
        String code = edtCode.getText().toString().trim();
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
        if (TextUtils.isEmpty(mVerifyCode)) {
            Toast.makeText(mContext, "请先获取验证码", Toast.LENGTH_SHORT).show();
            return;
        }

        BaseOkHttpClient.newBuilder()
                .addParam("mobile", phone)
                .addParam("event", "1")//传1~5;1:短信登录注册、2:忘记密码、3:修改密码、4:修改支付密码、5:换绑手机
                .addParam("captcha", code)
                .url(NetUrlUtils.VERIFY_CODE_LOGIN)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                UserLoginSuccessBean userLoginSuccessBean = JSONUtils.jsonString2Bean(json, UserLoginSuccessBean.class);
                LoginCheckUtils.saveLoginInfo(userLoginSuccessBean);
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
                .addParam("event", "1")//传1~5  1:注册/短信登录、2:忘记密码、3:修改密码、4:修改支付密码、5:换绑手机
                .url(NetUrlUtils.GET_VERIFY_CODE)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast("发送验证码成功");
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

    //三方登录
    private void loginByThirdPlatform(SHARE_MEDIA platform) {
        UMAuthListener authListener = new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param platform 平台名称
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {
                StyledDialogUtils.getInstance().loading(mContext);
                LogUtils.e(TAG, "授权开始");
            }

            /**
             * @desc 授权成功的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param data 用户资料返回
             */
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                LogUtils.e(TAG, "授权完成");
                StyledDialogUtils.getInstance().dismissLoading();
                if (platform == SHARE_MEDIA.QQ) {
                    mLoginType = Constants.TYPE_QQ;
                } else {
                    mLoginType = Constants.TYPE_WEIXIN;
                }

                LogUtils.e(TAG, "data = " + data.toString());
                mOpenId = data.get("openid");
                checkThirdLogin(mLoginType,
                        data.get("uid"),
                        mOpenId,
                        data.get("name"),
                        data.get("iconurl"),
                        "0".equals(data.get("gender")) ? 1 : 2);
            }

            /**
             * @desc 授权失败的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                StyledDialogUtils.getInstance().dismissLoading();
                LogUtils.e(TAG, "授权错误");
                LogUtils.e(TAG, t.getLocalizedMessage());
                mOpenId = "";
            }

            /**
             * @desc 授权取消的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             */
            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                StyledDialogUtils.getInstance().dismissLoading();
                mOpenId = "";
                LogUtils.e(TAG, "授权取消");
                Toast.makeText(mContext, "已取消", Toast.LENGTH_LONG).show();
            }
        };
        UMShareAPI.get(mContext).getPlatformInfo(mContext, platform, authListener);
    }

    //三方登录
    private void checkThirdLogin(int type, String uid, String openid, String name, String iconurl, int gender) {
        BaseOkHttpClient.newBuilder()
                .addParam("openId", openid)
                .url(NetUrlUtils.THIRD_LOGIN)
                .post()
                .build().enqueue(mContext, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String json, String msg) {
                toast(msg);
                UserLoginSuccessBean userLoginSuccessBean = JSONUtils.jsonString2Bean(json, UserLoginSuccessBean.class);
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

                mBindingPopupWindow = new BindingPopupWindow(mContext, new BindingPopupWindow.OnBindingCallback() {
                    @Override
                    public void cancel() {
                        mBindingPopupWindow.dismiss();
                    }

                    @Override
                    public void submit() {
                        String phoneNum = mBindingPopupWindow.getPhoneNum();
                        String verifyCode = mBindingPopupWindow.getVerifyCode();
                        bindMobile(phoneNum, verifyCode);
                    }
                });

                mBindingPopupWindow.showAtLocation(tvWx, Gravity.CENTER, 0, 0);


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
