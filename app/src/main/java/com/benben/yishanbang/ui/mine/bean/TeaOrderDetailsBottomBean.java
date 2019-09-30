package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by: wanghk 2019-08-12.
 * Describe: 订单管理-奶茶订单详情底部信息
 */
public class TeaOrderDetailsBottomBean {


    /**
     * milkTeaOrderVo : {"userId":"2c91f41d6cb3c6fb016cb71c710e001a","orderTime":"2019-09-09 09:27:34","goodsMoney":0.01,"orderNo":"7529142052","goodsCount":1,"orderId":"2c91f41d6d0a9e8a016d13a21b8d00b3","userMark":"","foodcode":"115231","codeImage":"QRcode/milkTea/20190909/43642686.png","fristGoodsName":"","milkTeaOrderGoodsVos":[{"id":"nc10007","status":"0","goodsName":"娃哈哈奶茶","price":0.01,"imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":17,"goodsCount":1,"spec":"小杯","temp":"常温","sugar":"无糖"},{"id":"nc10007","status":"0","goodsName":"娃哈哈奶茶","price":0.01,"imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":17,"goodsCount":0,"spec":"小杯","temp":"常温","sugar":"无糖"}],"status":"3"}
     * shopNameAndAddress : {"foodTime":40,"shopName":"多乐之店","shopId":"sp10000","shopAddress":"郑州市二七区郑州市二七区"}
     */

    private MilkTeaOrderVoBean milkTeaOrderVo;
    private ShopNameAndAddressBean shopNameAndAddress;

    public MilkTeaOrderVoBean getMilkTeaOrderVo() {
        return milkTeaOrderVo;
    }

    public void setMilkTeaOrderVo(MilkTeaOrderVoBean milkTeaOrderVo) {
        this.milkTeaOrderVo = milkTeaOrderVo;
    }

    public ShopNameAndAddressBean getShopNameAndAddress() {
        return shopNameAndAddress;
    }

    public void setShopNameAndAddress(ShopNameAndAddressBean shopNameAndAddress) {
        this.shopNameAndAddress = shopNameAndAddress;
    }

    public static class MilkTeaOrderVoBean {
        /**
         * userId : 2c91f41d6cb3c6fb016cb71c710e001a
         * orderTime : 2019-09-09 09:27:34
         * goodsMoney : 0.01
         * orderNo : 7529142052
         * goodsCount : 1
         * orderId : 2c91f41d6d0a9e8a016d13a21b8d00b3
         * userMark :
         * foodcode : 115231
         * codeImage : QRcode/milkTea/20190909/43642686.png
         * fristGoodsName :
         * milkTeaOrderGoodsVos : [{"id":"nc10007","status":"0","goodsName":"娃哈哈奶茶","price":0.01,"imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":17,"goodsCount":1,"spec":"小杯","temp":"常温","sugar":"无糖"},{"id":"nc10007","status":"0","goodsName":"娃哈哈奶茶","price":0.01,"imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":17,"goodsCount":0,"spec":"小杯","temp":"常温","sugar":"无糖"}]
         * status : 3
         */

        private String userId;
        private String orderTime;
        private double goodsMoney;
        private String orderNo;
        private int goodsCount;
        private String orderId;
        private String userMark;
        private String foodcode;
        private String codeImage;
        private String fristGoodsName;
        private String status;
        private List<MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public double getGoodsMoney() {
            return goodsMoney;
        }

        public void setGoodsMoney(double goodsMoney) {
            this.goodsMoney = goodsMoney;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getUserMark() {
            return userMark;
        }

        public void setUserMark(String userMark) {
            this.userMark = userMark;
        }

        public String getFoodcode() {
            return foodcode;
        }

        public void setFoodcode(String foodcode) {
            this.foodcode = foodcode;
        }

        public String getCodeImage() {
            return codeImage;
        }

        public void setCodeImage(String codeImage) {
            this.codeImage = codeImage;
        }

        public String getFristGoodsName() {
            return fristGoodsName;
        }

        public void setFristGoodsName(String fristGoodsName) {
            this.fristGoodsName = fristGoodsName;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public List<MilkTeaOrderGoodsVosBean> getMilkTeaOrderGoodsVos() {
            return milkTeaOrderGoodsVos;
        }

        public void setMilkTeaOrderGoodsVos(List<MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos) {
            this.milkTeaOrderGoodsVos = milkTeaOrderGoodsVos;
        }

        public static class MilkTeaOrderGoodsVosBean {
            /**
             * id : nc10007
             * status : 0
             * goodsName : 娃哈哈奶茶
             * price : 0.01
             * imgUrl : upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png
             * delPrice : 17
             * goodsCount : 1
             * spec : 小杯
             * temp : 常温
             * sugar : 无糖
             */

            private String id;
            private String status;
            private String goodsName;
            private double price;
            private String imgUrl;
            private int delPrice;
            private int goodsCount;
            private String spec;
            private String temp;
            private String sugar;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public int getDelPrice() {
                return delPrice;
            }

            public void setDelPrice(int delPrice) {
                this.delPrice = delPrice;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
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

            public String getSugar() {
                return sugar;
            }

            public void setSugar(String sugar) {
                this.sugar = sugar;
            }
        }
    }

    public static class ShopNameAndAddressBean {
        /**
         * foodTime : 40
         * shopName : 多乐之店
         * shopId : sp10000
         * shopAddress : 郑州市二七区郑州市二七区
         */

        private int foodTime;
        private String shopName;
        private String shopId;
        private String shopAddress;

        public int getFoodTime() {
            return foodTime;
        }

        public void setFoodTime(int foodTime) {
            this.foodTime = foodTime;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }
    }
}
