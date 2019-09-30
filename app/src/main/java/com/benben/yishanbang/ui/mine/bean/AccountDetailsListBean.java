package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 交易明细
 */
public class AccountDetailsListBean {
    private String payMoney;
    private String sumMoney;
    private List<UserBill> userBill;

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(String sumMoney) {
        this.sumMoney = sumMoney;
    }

    public List<UserBill> getUserBill() {
        return userBill;
    }

    public void setUserBill(List<UserBill> userBill) {
        this.userBill = userBill;
    }
}