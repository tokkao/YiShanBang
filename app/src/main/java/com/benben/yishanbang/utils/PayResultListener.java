package com.benben.yishanbang.utils;

/**
 * Created by Administrator on 2019/4/7.
 */

public interface PayResultListener {
    public void onPaySuccess();

    public void onPayError();

    public void onPayCancel();

}
