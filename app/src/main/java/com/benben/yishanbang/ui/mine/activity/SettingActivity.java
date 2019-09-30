package com.benben.yishanbang.ui.mine.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.benben.commoncore.utils.ActivityManagerUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.ui.MainActivity;
import com.benben.yishanbang.ui.NormalWebViewActivity;
import com.benben.yishanbang.ui.login.LoginCodeActivity;
import com.benben.yishanbang.utils.LoginCheckUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by: wanghk 2019-08-07.
 * Describe: 账户设置
 */
public class SettingActivity extends BaseActivity {

    private static final String TAG = "SettingActivity";
    @BindView(R.id.center_title)
    TextView centerTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initData() {
        centerTitle.setText("设置");
    }


    @OnClick({R.id.rl_back, R.id.tv_account_safe, R.id.tv_feed_back, R.id.tv_about_us, R.id.llyt_app_version, R.id.tv_log_out})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                onBackPressed();
                break;
            case R.id.tv_account_safe://账户安全
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, AccountSafeActivity.class));
                }
                break;
            case R.id.tv_feed_back://投诉建议
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    startActivity(new Intent(mContext, FeedBackActivity.class));
                }
                break;
            case R.id.tv_about_us://关于我们
                NormalWebViewActivity.startWithData(mContext,"http://ysbh5.zjxtaq.com/aboutUs.html","关于我们",true,false);
//                startActivity(new Intent(mContext, AboutUsActivity.class));
                break;
            case R.id.llyt_app_version://app 版本
                break;
            case R.id.tv_log_out://退出登录
                if (LoginCheckUtils.checkLoginShowDialog(mContext)) {
                    doLoginOut();
                }
                break;
        }
    }


    private void doLoginOut() {
        //清除登录信息
        LoginCheckUtils.clearUserInfo(mContext);
        //删除登录授权
        clearAuthLogin();
        //退出极光IM
        JMessageClient.logout();
        ActivityManagerUtils.remove(MainActivity.class);
        // ActivityManagerUtils.clear();
        Intent intent = new Intent(mContext, LoginCodeActivity.class);
        startActivity(intent);
        finish();
    }

    //清除授权信息
    private void clearAuthLogin() {
        UMAuthListener authListener = new UMAuthListener() {
            /**
             * @desc 授权开始的回调
             * @param platform 平台名称
             */
            @Override
            public void onStart(SHARE_MEDIA platform) {

            }

            /**
             * @desc 授权成功的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param data 用户资料返回
             */
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {

            }

            /**
             * @desc 授权失败的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             * @param t 错误原因
             */
            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                LogUtils.e(TAG, t.getLocalizedMessage());
            }

            /**
             * @desc 授权取消的回调
             * @param platform 平台名称
             * @param action 行为序号，开发者用不上
             */
            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
            }
        };
        UMShareAPI.get(mContext).deleteOauth(mContext, SHARE_MEDIA.WEIXIN, authListener);
        UMShareAPI.get(mContext).deleteOauth(mContext, SHARE_MEDIA.QQ, authListener);

    }
}
