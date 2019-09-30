package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Author:zhn
 * Time:2019/8/27 0027 17:02
 */
public class MilkTeaDetailsBean {
    /**
     * milkTeaOrderVo : {"orderNo":"0962763758","foodcode":"","codeImage":"","orderTime":"2019-08-16 18:44:25","orderId":"402880386c99e985016c9a074c7c0012","milkTeaOrderGoodsVos":[{"id":"sp10001","status":"0","goodsName":"茉莉清茶","price":4,"imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","delPrice":5.5,"goodsCount":3,"spec":"中","temp":"多","sugar":"常温"},{"id":"sp10000","status":"1","goodsName":"茉莉茶","price":4,"imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","delPrice":6.3,"goodsCount":1,"spec":"中","temp":"多","sugar":"常温"}],"status":"5"}
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

    public  class MilkTeaOrderVoBean {
        /**
         * orderNo : 0962763758
         * foodcode :
         * codeImage :
         * orderTime : 2019-08-16 18:44:25
         * orderId : 402880386c99e985016c9a074c7c0012
         * milkTeaOrderGoodsVos : [{"id":"sp10001","status":"0","goodsName":"茉莉清茶","price":4,"imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","delPrice":5.5,"goodsCount":3,"spec":"中","temp":"多","sugar":"常温"},{"id":"sp10000","status":"1","goodsName":"茉莉茶","price":4,"imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","delPrice":6.3,"goodsCount":1,"spec":"中","temp":"多","sugar":"常温"}]
         * status : 5
         */

        private String orderNo;
        private String foodcode;
        private String codeImage;
        private String orderTime;
        private String orderId;
        private String status;
        private List<MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
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

        public String getOrderTime() {
            return orderTime;
        }

        public void setOrderTime(String orderTime) {
            this.orderTime = orderTime;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
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

        public  class MilkTeaOrderGoodsVosBean {
            public MilkTeaOrderGoodsVosBean() {
            }

            /**
             * id : sp10001
             * status : 0
             * goodsName : 茉莉清茶
             * price : 4.0
             * imgUrl : upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg
             * delPrice : 5.5
             * goodsCount : 3
             * spec : 中
             * temp : 多
             * sugar : 常温
             */


            private String id;
            private String status;
            private String goodsName;
            private double price;
            private String imgUrl;
            private double delPrice;
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

            public double getDelPrice() {
                return delPrice;
            }

            public void setDelPrice(double delPrice) {
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


