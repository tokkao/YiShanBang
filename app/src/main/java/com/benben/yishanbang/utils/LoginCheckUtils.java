package com.benben.yishanbang.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.api.NetUrlUtils;
import com.benben.yishanbang.http.BaseCallBack;
import com.benben.yishanbang.http.BaseOkHttpClient;
import com.benben.yishanbang.ui.login.LoginCodeActivity;
import com.benben.yishanbang.ui.login.bean.UserLoginSuccessBean;
import com.benben.yishanbang.ui.mine.bean.UserInfoBean;
import com.kongzue.dialog.v3.MessageDialog;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;

//import com.hyphenate.chat.EMClient;

/**
 * 用户是否登录检测工具类
 * Created by Administrator on 2017/11/28.
 */
public class LoginCheckUtils {
    private static final String TAG = "LoginCheckUtils";

    //验证是否登录的异步回调
    public interface CheckCallBack {
        /**
         * 检查结果
         *
         * @param flag 是否登录
         *             true：已登录；false：未登录
         */
        void onCheckResult(boolean flag);
    }

    /**
     * 检查用户是否登录
     *
     * @param context
     * @return
     */
    public static boolean checkUserIsLogin(Context context) {
        String uid = MyApplication.mPreferenceProvider.getUId();
        String token = MyApplication.mPreferenceProvider.getToken();
        if (!StringUtils.isEmpty(uid) && !StringUtils.isEmpty(token)) {
            return true;
        }
        return false;
    }

    /**
     * 检查用户是否登录
     *
     * @param activity
     * @param callBack
     * @return
     */
    public static void checkUserIsLogin(Activity activity, CheckCallBack callBack) {
        String uid = MyApplication.mPreferenceProvider.getUId();
        String token = MyApplication.mPreferenceProvider.getToken();
        if (StringUtils.isEmpty(uid) || StringUtils.isEmpty(token)) {
            //清除用户信息
            clearUserInfo(activity);
            //验证结果回调
            callBack.onCheckResult(false);
            //显示验证弹窗
            showLoginDialog(activity, false);
            return;
        }
        BaseOkHttpClient.newBuilder()
                .addParam("token",MyApplication.mPreferenceProvider.getToken())
                .url(NetUrlUtils.GET_USER_INFO)
                .post().build().enqueue(activity, new BaseCallBack<String>() {
            @Override
            public void onSuccess(String result, String msg) {
                String user = JSONUtils.getNoteJson(result, "user");
                //更新当前的登录用户信息
                if (!StringUtils.isEmpty(user)) {
                    UserInfoBean userBean = JSONUtils.jsonString2Bean(user, UserInfoBean.class);
                    if (userBean != null) {
                        LoginCheckUtils.updateUserInfo(userBean);
                    }
                }
                callBack.onCheckResult(true);
            }

            @Override
            public void onError(int code, String msg) {
                //清除用户信息
                clearUserInfo(activity);
                //验证结果回调
                callBack.onCheckResult(false);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                //清除用户信息
                clearUserInfo(activity);
                //验证结果回调
                callBack.onCheckResult(false);
                //显示验证弹窗
                showLoginDialog(activity, false);
            }
        });
    }


    /**
     * 提示用户登录对话框
     *
     * @param context         //上下文
     * @param isClearUserInfo //是否清除用户信息
     */
    public static void showLoginDialog(Activity context, boolean isClearUserInfo) {

        MessageDialog.show((AppCompatActivity) context, "温馨提示", "您还没有登录，请先登录！", "确定", "取消")
                .setOnOkButtonClickListener((baseDialog, v) -> {
                    if (isClearUserInfo) LoginCheckUtils.clearUserInfo(context);
                    context.startActivity(new Intent(context, LoginCodeActivity.class));
                    return false;
                }).show();


    }


    //检查是否登录
    public static boolean checkLoginShowDialog(Activity mContext) {
        if (!LoginCheckUtils.checkUserIsLogin(mContext)) {
            LoginCheckUtils.showLoginDialog(mContext, false);
            return false;

        }
        return true;
    }

    /**
     * 更新用户信息
     *
     * @param infoBean 用户信息
     */
    public static void updateUserInfo(UserInfoBean infoBean) {
        //昵称
        MyApplication.mPreferenceProvider.setUserName(infoBean.getUsername());
        //type  用户类型 1为普通用户 2为优惠卡商家 3为奶茶商家用户 4为客服 5为创业商城用户 6超级管理员 7普通管理员
        MyApplication.mPreferenceProvider.setUserType(infoBean.getUserType());
        //手机号
        MyApplication.mPreferenceProvider.setMobile(infoBean.getMobile());
        //头像
        MyApplication.mPreferenceProvider.setPhoto(infoBean.getAvatar());
        //店铺id
        MyApplication.mPreferenceProvider.setShopId(infoBean.getShopId());
        //身份证号
        MyApplication.mPreferenceProvider.setIdNumber(infoBean.getCitizenNo());
        //真实姓名
        MyApplication.mPreferenceProvider.setRealName(infoBean.getRealname());
        //IM账号
        MyApplication.mPreferenceProvider.setIMUserName(infoBean.getImUserName());
        //聊天权限 聊天权限1有权限0无权限
        MyApplication.mPreferenceProvider.setIMJurisdiction(infoBean.getImJurisdiction());
        //聊天权限 是否设置支付密码设置0没设置1有设置
        MyApplication.mPreferenceProvider.setClosePayPwd(infoBean.getIsPayPassword().equals("1"));

    }

    //保存登录信息
    public static void saveLoginInfo(UserLoginSuccessBean infoBean) {
        //token
        MyApplication.mPreferenceProvider.setToken(infoBean.getToken());
        //用户id
        MyApplication.mPreferenceProvider.setUId(infoBean.getUser().getId());
        //昵称
        MyApplication.mPreferenceProvider.setUserName(infoBean.getUser().getUsername());
        //type  用户类型1为普通用户2为优惠卡商家3为奶茶商家用户4为客服5为创业商城用户6超级管理员7普通管理员
        MyApplication.mPreferenceProvider.setUserType(infoBean.getUser().getUserType());
        //手机号
        MyApplication.mPreferenceProvider.setMobile(infoBean.getUser().getMobile());
        //头像
        MyApplication.mPreferenceProvider.setPhoto(infoBean.getUser().getAvatar());
        //店铺id
        MyApplication.mPreferenceProvider.setShopId(infoBean.getUser().getShopId());
        //身份证号
        MyApplication.mPreferenceProvider.setIdNumber(infoBean.getUser().getCitizenNo());
        //真实姓名
        MyApplication.mPreferenceProvider.setRealName(infoBean.getUser().getRealname());
        //IM账号
        MyApplication.mPreferenceProvider.setIMUserName(infoBean.getUser().getImUserName());
        //聊天权限 聊天权限1有权限0无权限
        MyApplication.mPreferenceProvider.setIMJurisdiction(infoBean.getUser().getImJurisdiction());
        //聊天权限 是否设置支付密码设置0没设置1有设置
        MyApplication.mPreferenceProvider.setClosePayPwd(infoBean.getUser().getIsPayPassword().equals("1"));
    }

    //清空登录信息和授权信息
    public static void clearUserInfo(Activity activity) {
        MyApplication.mPreferenceProvider.setToken("");
        MyApplication.mPreferenceProvider.setUId("");
        MyApplication.mPreferenceProvider.setUserName("");
        MyApplication.mPreferenceProvider.setUserType("");
        MyApplication.mPreferenceProvider.setMobile("");
        MyApplication.mPreferenceProvider.setPhoto("");
        MyApplication.mPreferenceProvider.setShopId("");
        MyApplication.mPreferenceProvider.setIdNumber("");
        MyApplication.mPreferenceProvider.setRealName("");
        MyApplication.mPreferenceProvider.setIMUserName("");
        clearAuthLogin(activity);
    }

    //清除授权信息
    private static void clearAuthLogin(Activity activity) {
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
        UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.WEIXIN, authListener);
        UMShareAPI.get(activity).deleteOauth(activity, SHARE_MEDIA.QQ, authListener);

    }
}
