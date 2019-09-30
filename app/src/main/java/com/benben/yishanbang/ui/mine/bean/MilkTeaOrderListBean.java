package com.benben.yishanbang.ui.mine.bean;

import java.io.Serializable;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 奶茶订单
 */
public class MilkTeaOrderListBean implements Serializable {

    /**
     * userMark :
     * orderNo : 9692247175
     * orderId : 2c91f41d6cf1de7a016cf48d2c19000b
     * fristGoodsName : {"goodsName":"娃哈哈奶茶"}
     * foodtime : 40
     * shopName : 多乐之店
     * userId : 2c91f41d6cae67a9016cb323cc440041
     * foodcode :
     * goodsCount : 5
     * goodsMoney :
     * orderTime : 2019-09-03 08:36:28
     * codeImage :
     * shopId : sp10000
     * status : 1
     */

    private String userMark;
    private String orderNo;
    private String orderId;
    private FristGoodsNameBean fristGoodsName;
    private int foodtime;
    private String shopName;
    private String userId;
    private String foodcode;
    private int goodsCount;
    private String goodsMoney;
    private String orderTime;
    private String codeImage;
    private String shopId;
    private String status;

    public String getUserMark() {
        return userMark;
    }

    public void setUserMark(String userMark) {
        this.userMark = userMark;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public FristGoodsNameBean getFristGoodsName() {
        return fristGoodsName;
    }

    public void setFristGoodsName(FristGoodsNameBean fristGoodsName) {
        this.fristGoodsName = fristGoodsName;
    }

    public int getFoodtime() {
        return foodtime;
    }

    public void setFoodtime(int foodtime) {
        this.foodtime = foodtime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFoodcode() {
        return foodcode;
    }

    public void setFoodcode(String foodcode) {
        this.foodcode = foodcode;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getGoodsMoney() {
        return goodsMoney;
    }

    public void setGoodsMoney(String goodsMoney) {
        this.goodsMoney = goodsMoney;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(String codeImage) {
        this.codeImage = codeImage;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class FristGoodsNameBean {
        /**
         * goodsName : 娃哈哈奶茶
         */

        private String goodsName;

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }
    }
}
