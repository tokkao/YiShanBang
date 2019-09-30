package com.benben.yishanbang.ui.mine.bean;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 购物订单列表
 */
public class ShoppingOrderListBean {

    private String orderTime;
    private String orderNo;
    private String orderMoney;
    private String goodCount;
    private String stauts;
    private String name;
    private String id;
    private Good good;


    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getGoodCount() {
        return goodCount;
    }

    public void setGoodCount(String goodCount) {
        this.goodCount = goodCount;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Good getGood() {
        return good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public static class Good {
        private String imgUrl;
        private String shopId;
        private String price;
        private String bottomImgs;
        private String goodsCategoryId;
        private String count;
        private String status;
        private String name;
        private String message;
        private String id;
        private String number;

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBottomImgs() {
            return bottomImgs;
        }

        public void setBottomImgs(String bottomImgs) {
            this.bottomImgs = bottomImgs;
        }

        public String getGoodsCategoryId() {
            return goodsCategoryId;
        }

        public void setGoodsCategoryId(String goodsCategoryId) {
            this.goodsCategoryId = goodsCategoryId;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
