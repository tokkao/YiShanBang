package com.benben.yishanbang.ui.mine.bean;

/**
 * Created by Administrator on 2019/8/22 0022
 * Describe:钱包--我的优惠卡列表
 */
public class WalletCouponCardBean {

    /**
     * id : yhk10000
     * imgUrl : upload/img/photosucai/20190812/325717-130GG9515094_1565582765730.jpg
     * name : 芒果奶茶
     * price : 25.55
     * shopName : tom
     * codeImage : QRcode/card/20190820/34298255.jpg
     */

    private String id;
    private String imgUrl;
    private String name;
    private double price;
    private String shopName;
    private String codeImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCodeImage() {
        return codeImage;
    }

    public void setCodeImage(String codeImage) {
        this.codeImage = codeImage;
    }
}
