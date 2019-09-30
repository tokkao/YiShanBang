package com.benben.yishanbang.ui.tea.bean;

/**
 * Created by Administrator on 2019/8/15 0015
 * Describe:购物车商品
 */
public class ShoppingCarGoodsBean {


    /**
     * id : 2c91f41d6d0085b5016d0091c9f60008
     * spec : 中杯
     * temp : 半糖
     * suger : 少冰
     * goodsNum : 1
     * goodsId : nc10007
     * price : 0.01
     * delPrice : 17
     * goodsName : 娃哈哈奶茶
     * goodsImgUrl : upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png
     */

    private String id;
    private String spec;
    private String temp;
    private String suger;
    private int goodsNum;
    private String goodsId;
    private double price;
    private int delPrice;
    private String goodsName;
    private String goodsImgUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getSuger() {
        return suger;
    }

    public void setSuger(String suger) {
        this.suger = suger;
    }

    public int getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(int goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDelPrice() {
        return delPrice;
    }

    public void setDelPrice(int delPrice) {
        this.delPrice = delPrice;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImgUrl() {
        return goodsImgUrl;
    }

    public void setGoodsImgUrl(String goodsImgUrl) {
        this.goodsImgUrl = goodsImgUrl;
    }
}
