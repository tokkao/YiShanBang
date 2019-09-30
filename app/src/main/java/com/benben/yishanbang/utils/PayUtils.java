package com.benben.yishanbang.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.benben.commoncore.utils.LogUtils;
import com.benben.yishanbang.bean.PayBean;
import com.benben.yishanbang.config.Constants;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Map;

// 支付工具类
public class PayUtils {
    private  Activity mContext ;

    public PayUtils(Activity mContext) {
        this.mContext = mContext;
    }



    /**
     * 微信支付
     */
    public void wxPay(PayBean bean) {
        LogUtils.e("微信支付", bean.toString());
        IWXAPI api = WXAPIFactory.createWXAPI(mContext, null);
        api.registerApp(Constants.WX_APP_KEY);//微信的appkey

        PayReq request = new PayReq();
        request.appId = bean.getAppid();
        request.partnerId = bean.getPartnerid();
        request.prepayId = bean.getPrepayid();
        request.packageValue = bean.getPackageX();
        request.nonceStr = bean.getNoncestr();
        request.timeStamp = bean.getTimestamp();
        request.sign = bean.getSign();
        api.sendReq(request);
    }


    /**
     * 支付宝支付
     */
    public void aliPay(final String orderInfo, Activity mContext) {
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mContext);
                Map<String, String> result = alipay.payV2(orderInfo, true);

                Message msg = new Message();
                msg.what = 1;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * 支付宝支付的回调
     */
    @SuppressLint("HandlerLeak")
    public  Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                Map<String, String> result = (Map<String, String>) msg.obj;

                String resultStatus = result.get("resultStatus");
                if (resultStatus.equals("4000")) {
                    //支付失败
                    PayListenerUtils.getInstance(mContext).addError();

                } else if (resultStatus.equals("9000")) {
                    //支付成功
                    PayListenerUtils.getInstance(mContext).addSuccess();
                } else {
                    //支付取消
                    PayListenerUtils.getInstance(mContext).addCancel();

                }
            }
        }
    };

}
