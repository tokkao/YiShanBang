package com.benben.yishanbang.ui.tea.bean;

import java.util.List;

/**
 * Author:zhn
 * Time:2019/8/28 0028 14:14
 */
public class OrderDetailsBean {
    /**
     * milkTeaOrderVo : {"status":"3","orderId":"402880386c99e985016c9a045931000f","codeImage":"QRcode/milkTea/20190816/22200958.jpg","orderTime":"2019-08-16 18:41:12","foodcode":"0467495683","milkTeaOrderGoodsVos":[{"id":"sp10001","status":"0","goodsName":"茉莉清茶","price":4,"delPrice":1,"goodsCount":3,"spec":"中","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","temperature_value":"多","sugar":"常温","temp":"多"},{"id":"sp10000","status":"1","goodsName":"茉莉茶","price":4,"delPrice":6.3,"goodsCount":1,"spec":"中","temp":"多","sugar":"常温"}]}
     * shopNameAndAddress : {"shopName":"多乐之店","shopAddress":"郑州市二七区郑州市二七区"}
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
         * status : 3
         * orderId : 402880386c99e985016c9a045931000f
         * codeImage : QRcode/milkTea/20190816/22200958.jpg
         * orderTime : 2019-08-16 18:41:12
         * foodcode : 0467495683
         * milkTeaOrderGoodsVos : [{"id":"sp10001","status":"0","goodsName":"茉莉清茶","price":4,"delPrice":1,"goodsCount":3,"spec":"中","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","temperature_value":"多","sugar":"常温"},{"id":"sp10000","status":"1","goodsName":"茉莉茶","price":4,"delPrice":6.3,"goodsCount":1,"spec":"中","temp":"多","sugar":"常温"}]
         */

        private String status;
        private String orderId;
        private String codeImage;
        private String orderTime;
        private String foodcode;
        private List<MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getCodeImage() {
            return codeImage;
        }

        public void setCodeImage(String codeImage) {
            this.codeImage = codeImage;
        }

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getFoodcode() {
            return foodcode;
        }

        public void setFoodcode(String foodcode) {
            this.foodcode = foodcode;
        }

        public List<MilkTeaOrderGoodsVosBean> getMilkTeaOrderGoodsVos() {
            return milkTeaOrderGoodsVos;
        }

        public void setMilkTeaOrderGoodsVos(List<MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos) {
            this.milkTeaOrderGoodsVos = milkTeaOrderGoodsVos;
        }

        public static class MilkTeaOrderGoodsVosBean {
            /**
             * id : sp10001
             * status : 0
             * goodsName : 茉莉清茶
             * price : 4
             * delPrice : 1
             * goodsCount : 3
             * spec : 中
             * imgUrl : upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg
             * temperature_value : 多
             * sugar : 常温
             * temp : 多
             */

            private String id;
            private String status;
            private String goodsName;
            private int price;
            private int delPrice;
            private int goodsCount;
            private String spec;
            private String imgUrl;
            private String temperature_value;
            private String sugar;
            private String temp;

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

            public int getPrice() {
                return price;
            }

            public void setPrice(int price) {
                this.price = price;
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

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getTemperature_value() {
                return temperature_value;
            }

            public void setTemperature_value(String temperature_value) {
                this.temperature_value = temperature_value;
            }

            public String getSugar() {
                return sugar;
            }

            public void setSugar(String sugar) {
                this.sugar = sugar;
            }

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }
        }
    }

    public static class ShopNameAndAddressBean {
        /**
         * shopName : 多乐之店
         * shopAddress : 郑州市二七区郑州市二七区
         */

        private String shopName;
        private String shopAddress;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShopAddress() {
            return shopAddress;
        }

        public void setShopAddress(String shopAddress) {
            this.shopAddress = shopAddress;
        }
    }
}
