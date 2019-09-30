package com.benben.yishanbang.ui.mine.bean;

//收藏的店铺
public class CollectionShopListBean {


    /**
     * shopId : sp10000
     * shopName : 多乐之店
     * shoptImg : img/server/20190726/0c689536278e1e85f4564da84e588ab9_1564125277559.jpg
     * shopPlace :
     * shopMessage : Bbbbbbbb
     * distance : 6978.6
     * salesCountMonth : 0
     */

    private String shopId;
    private String shopName;
    private String shoptImg;
    private String shopPlace;
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

    public String getShopPlace() {
        return shopPlace;
    }

    public void setShopPlace(String shopPlace) {
        this.shopPlace = shopPlace;
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
