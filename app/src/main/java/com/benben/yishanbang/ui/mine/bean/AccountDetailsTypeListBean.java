package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 不同类型交易明细
 */
public class AccountDetailsTypeListBean {
    private List<UserBill> sumMoney;

    public List<UserBill> getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(List<UserBill> sumMoney) {
        this.sumMoney = sumMoney;
    }
}