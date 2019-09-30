package com.benben.yishanbang.ui.service.bean;

import java.util.List;

public class VentureShopCartOrderBean {

    /**
     * orderNo : 2866312099
     * orderId : 402881096d44aa38016d44af57420000
     * orderInfo : [{"id":"1","goodsName":"破洞牛仔裤","price":"48","imgUrl":"假装有路径","delPrice":"","goodsCount":3}]
     */

    private String orderNo;
    private String orderId;
    private String orderMoney;
    private List<OrderInfoBean> orderInfo;

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
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

    public List<OrderInfoBean> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(List<OrderInfoBean> orderInfo) {
        this.orderInfo = orderInfo;
    }

    public static class OrderInfoBean {
        /**
         * id : 1
         * goodsName : 破洞牛仔裤
         * price : 48
         * imgUrl : 假装有路径
         * delPrice :
         * goodsCount : 3
         */

        private String id;
        private String goodsName;
        private String price;
        private String imgUrl;
        private String delPrice;
        private int goodsCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getDelPrice() {
            return delPrice;
        }

        public void setDelPrice(String delPrice) {
            this.delPrice = delPrice;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }
    }
}
