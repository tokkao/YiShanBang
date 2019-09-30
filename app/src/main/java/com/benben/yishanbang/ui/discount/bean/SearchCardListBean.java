package com.benben.yishanbang.ui.discount.bean;

//搜索优惠卡店铺
public class SearchCardListBean {


    /**
     * shopId : sp10001
     * shopName : 本色之家
     * shoptImg : img/server/20190726/0c689536278e1e85f4564da84e588ab9_1564125277559.jpg
     * shopMessage : 商家简介
     * distance : 6975.1
     * salesCountMonth : 0
     */

    private String shopId;
    private String shopName;
    private String shoptImg;
    private String shopMessage;
    private String distance;
    private int salesCountMonth;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShoptImg() {
        return shoptImg;
    }

    public void setShoptImg(String shoptImg) {
        this.shoptImg = shoptImg;
    }

    public String getShopMessage() {
        return shopMessage;
    }

    public void setShopMessage(String shopMessage) {
        this.shopMessage = shopMessage;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public int getSalesCountMonth() {
        return salesCountMonth;
    }

    public void setSalesCountMonth(int salesCountMonth) {
        this.salesCountMonth = salesCountMonth;
    }
}
