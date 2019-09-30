package com.benben.yishanbang.ui.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.benben.commoncore.utils.JSONUtils;
import com.benben.commoncore.utils.LogUtils;
import com.benben.commoncore.utils.StringUtils;
import com.benben.yishanbang.MyApplication;
import com.benben.yishanbang.R;
import com.benben.yishanbang.base.BaseActivity;
import com.benben.yishanbang.ui.mine.activity.WriteOffCardOrderDetailsActivity;
import com.benben.yishanbang.ui.mine.activity.WriteOffTeaOrderDetailsActivity;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * Created by lixiaofeng on 2018/11/21.
 */

public class QRCodeActivity extends BaseActivity implements QRCodeView.Delegate {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.zxingview)
    ZXingView zxingview;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qr_code;
    }

    @Override
    protected void initData() {
        zxingview.setDelegate(this);
    }

    @OnClick({R.id.iv_back})
    public void onViewClicked(View view) {
        Intent intent = null;
        switch (view.getId()) {
            //返回
            case R.id.iv_back:
                finish();
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        zxingview.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        zxingview.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        zxingview.startSpotAndShowRect(); // 显示扫描框，并且延迟0.1秒后开始识别
    }

    @Override
    protected void onStop() {
        zxingview.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        zxingview.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        LogUtils.e("TAG", "扫码结果：" + result);
//        vibrate();
        if (StringUtils.isEmpty(result)) {
            toast("扫描错误，请重试");
            return;
        }
        String orderType = JSONUtils.getNoteJson(result, "orderType");
        if (StringUtils.isEmpty(orderType)) {
            //纸巾机
            String number = JSONUtils.getNoteJson(result, "number");
            Bundle bundle = new Bundle();
            bundle.putString("number", number);
            MyApplication.openActivity(mContext, PageListActivity.class, bundle);
            finish();
            return;
        }
        switch (orderType) {
            case "3"://奶茶
                String foodCode = JSONUtils.getNoteJson(result, "foodCode");
                String orderId = JSONUtils.getNoteJson(result, "orderId");
                String userId = JSONUtils.getNoteJson(result, "userId");
                Bundle teaBundle = new Bundle();
                teaBundle.putString("foodCode", foodCode);
                teaBundle.putString("orderId", orderId);
                teaBundle.putString("userId", userId);
                MyApplication.openActivity(mContext, WriteOffTeaOrderDetailsActivity.class, teaBundle);
                finish();
                break;
            case "0"://优惠卡
                String cardId = JSONUtils.getNoteJson(result, "cardId");
                String cardOrderId = JSONUtils.getNoteJson(result, "orderId");
                String cardUserId = JSONUtils.getNoteJson(result, "userId");
                Bundle cardBundle = new Bundle();
                cardBundle.putString("cardId", cardId);
                cardBundle.putString("orderId", cardOrderId);
                cardBundle.putString("userId", cardUserId);
                MyApplication.openActivity(mContext, WriteOffCardOrderDetailsActivity.class, cardBundle);
                finish();
                break;
        }

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = zxingview.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zxingview.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                zxingview.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e("TAG", "打开相机出错");
    }

    @Override
    protected int getStatusBarColor() {
        return R.color.qr_code_state;
    }
}
