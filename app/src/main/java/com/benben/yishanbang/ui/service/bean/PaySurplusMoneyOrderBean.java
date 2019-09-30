package com.benben.yishanbang.ui.service.bean;

public class PaySurplusMoneyOrderBean {

    /**
     * orderNo :
     * hour : 8
     * payPrice : 800
     * name : 搓背
     */

    private String orderNo;
    private int hour;
    private String payPrice;
    private String name;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(String payPrice) {
        this.payPrice = payPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
