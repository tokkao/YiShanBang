package com.benben.yishanbang.ui.tea.bean;

public class ShopCartInfoBean {

    public ShopCartInfoBean(int count, double price) {
        this.count = count;
        this.price = price;
    }

    private int count;
    private double price;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
