package com.benben.yishanbang.ui.tea.bean;

import java.util.List;

/**
 * Author:zhn
 * Time:2019/8/26 0026 9:34
 */
public class MilkTeaBean {
    /**
     * imageUrl : upload/img/photosucai/20190812/b88889ef9bf8_1565582870775.jpg
     * milkTeaGoodsVos : [{"sortId":7,"categoryName":"热卖","milkTeaGoodsVos":[{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":6,"goodsId":"nc100021","delPrice":12.3,"goodsName":"草莓奶茶","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":7,"goodsId":"nc100022","delPrice":12.3,"goodsName":"草莓奶茶","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":8,"goodsId":"nc100023","delPrice":12.3,"goodsName":"茉莉茶","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":9,"goodsId":"nc100024","delPrice":12.3,"goodsName":"哈哈奶昔","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":14,"goodsId":"nc100025","delPrice":12.3,"goodsName":"哈哈奶昔","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":10,"goodsId":"nc100029","delPrice":12.3,"goodsName":"魔力奶昔","goodsType":"0","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":10,"goodsId":"nc100030","delPrice":12.3,"goodsName":"安慕希","goodsType":"0","selectNum":"","description":"商品简介","status":"0"}],"categoryId":"gc10006"},{"sortId":8,"categoryName":"沁凉夏日","milkTeaGoodsVos":[{"shopId":"sp10002","categoryId":"","imgUrl":"130GG9515094_1565269785947.jpg","price":6,"goodsId":"nc10002","delPrice":9.3,"goodsName":"水晶奶茶","goodsType":"1","selectNum":"","description":"都是男的就来劲","status":"0"}],"categoryId":"gc10007"}]
     */

    private String imageUrl;
    private List<MilkTeaGoodsVosBeanX> milkTeaGoodsVos;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<MilkTeaGoodsVosBeanX> getMilkTeaGoodsVos() {
        return milkTeaGoodsVos;
    }

    public void setMilkTeaGoodsVos(List<MilkTeaGoodsVosBeanX> milkTeaGoodsVos) {
        this.milkTeaGoodsVos = milkTeaGoodsVos;
    }

    public  class MilkTeaGoodsVosBeanX {
        /**
         * sortId : 7
         * categoryName : 热卖
         * milkTeaGoodsVos : [{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":6,"goodsId":"nc100021","delPrice":12.3,"goodsName":"草莓奶茶","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":7,"goodsId":"nc100022","delPrice":12.3,"goodsName":"草莓奶茶","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":8,"goodsId":"nc100023","delPrice":12.3,"goodsName":"茉莉茶","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":9,"goodsId":"nc100024","delPrice":12.3,"goodsName":"哈哈奶昔","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":14,"goodsId":"nc100025","delPrice":12.3,"goodsName":"哈哈奶昔","goodsType":"1","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":10,"goodsId":"nc100029","delPrice":12.3,"goodsName":"魔力奶昔","goodsType":"0","selectNum":"","description":"商品简介","status":"0"},{"shopId":"sp10002","categoryId":"","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","price":10,"goodsId":"nc100030","delPrice":12.3,"goodsName":"安慕希","goodsType":"0","selectNum":"","description":"商品简介","status":"0"}]
         * categoryId : gc10006
         */

        private int sortId;
        private String categoryName;
        private String categoryId;
        private List<MilkTeaGoodsVosBean> milkTeaGoodsVos;

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<MilkTeaGoodsVosBean> getMilkTeaGoodsVos() {
            return milkTeaGoodsVos;
        }

        public void setMilkTeaGoodsVos(List<MilkTeaGoodsVosBean> milkTeaGoodsVos) {
            this.milkTeaGoodsVos = milkTeaGoodsVos;
        }

        public  class MilkTeaGoodsVosBean {
            /**
             * shopId : sp10002
             * categoryId :
             * imgUrl : upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg
             * price : 6.0
             * goodsId : nc100021
             * delPrice : 12.3
             * goodsName : 草莓奶茶
             * goodsType : 1
             * selectNum :
             * description : 商品简介
             * status : 0
             */

            private String shopId;
            private String categoryId;
            private String imgUrl;
            private double price;
            private String goodsId;
            private double delPrice;
            private String goodsName;
            private String goodsType;
            private String selectNum;
            private String description;
            private String status;
            private String goodsCount;

            public String getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(String goodsCount) {
                this.goodsCount = goodsCount;
            }


            public String getShopId() {
                return shopId;
            }

            public void setShopId(String shopId) {
                this.shopId = shopId;
            }

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public double getPrice() {
                return price;
            }

            public void setPrice(double price) {
                this.price = price;
            }

            public String getGoodsId() {
                return goodsId;
            }

            public void setGoodsId(String goodsId) {
                this.goodsId = goodsId;
            }

            public double getDelPrice() {
                return delPrice;
            }

            public void setDelPrice(double delPrice) {
                this.delPrice = delPrice;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }

            public String getGoodsType() {
                return goodsType;
            }

            public void setGoodsType(String goodsType) {
                this.goodsType = goodsType;
            }

            public String getSelectNum() {
                return selectNum;
            }

            public void setSelectNum(String selectNum) {
                this.selectNum = selectNum;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }
        }
    }


    /**
     * imageUrl : upload/img/photosucai/20190812/b88889ef9bf8_1565582870775.jpg
     * goodsCategoryVos : [{"sortId":9,"categoryName":"咖啡时光","categoryId":"gc10008"}]
     * milkTeaGoodsVos : [{"shopId":"sp10006","price":3,"goodsId":"nc10007","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","goodsName":"娃哈哈奶茶","delPrice":17,"categoryId":"gc10008","goodsType":"1","selectNum":28,"description":"活动你回复","status":"0"},{"shopId":"sp10006","price":4,"goodsId":"nc10008","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","goodsName":"火龙果奶茶","delPrice":13,"categoryId":"gc10008","goodsType":"0","selectNum":"","description":"个害己哦","status":"0"},{"shopId":"sp10006","price":5,"goodsId":"nc10009","imgUrl":"upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg","goodsName":"好的奶茶","delPrice":10,"categoryId":"gc10008","goodsType":"0","selectNum":1,"description":"法院遂过后就起来更不易","status":"0"}]
     */

    /*private String imageUrl;
    private List<GoodsCategoryVosBean> goodsCategoryVos;
    private List<MilkTeaGoodsVosBean> milkTeaGoodsVos;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<GoodsCategoryVosBean> getGoodsCategoryVos() {
        return goodsCategoryVos;
    }

    public void setGoodsCategoryVos(List<GoodsCategoryVosBean> goodsCategoryVos) {
        this.goodsCategoryVos = goodsCategoryVos;
    }

    public List<MilkTeaGoodsVosBean> getMilkTeaGoodsVos() {
        return milkTeaGoodsVos;
    }

    public void setMilkTeaGoodsVos(List<MilkTeaGoodsVosBean> milkTeaGoodsVos) {
        this.milkTeaGoodsVos = milkTeaGoodsVos;
    }

    public static class GoodsCategoryVosBean {
        *//**
         * sortId : 9
         * categoryName : 咖啡时光
         * categoryId : gc10008
         *//*

        private int sortId;
        private String categoryName;
        private String categoryId;

        public int getSortId() {
            return sortId;
        }

        public void setSortId(int sortId) {
            this.sortId = sortId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }
    }

    public  class MilkTeaGoodsVosBean {
        *//**
         * shopId : sp10006
         * price : 3.0
         * goodsId : nc10007
         * imgUrl : upload/img/photosucai/20190808/325717-130GG9515094_1565269785947.jpg
         * goodsName : 娃哈哈奶茶
         * delPrice : 17.0
         * categoryId : gc10008
         * goodsType : 1
         * selectNum : 28
         * description : 活动你回复
         * status : 0
         *//*

        private String shopId;
        private double price;
        private String goodsId;
        private String imgUrl;
        private String goodsName;
        private double delPrice;
        private String categoryId;
        private String goodsType;
        private int selectNum;
        private String description;
        private String status;

        public String getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(String goodsCount) {
            this.goodsCount = goodsCount;
        }

        private String goodsCount;

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getDelPrice() {
            return delPrice;
        }

        public void setDelPrice(double delPrice) {
            this.delPrice = delPrice;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getGoodsType() {
            return goodsType;
        }

        public void setGoodsType(String goodsType) {
            this.goodsType = goodsType;
        }

        public int getSelectNum() {
            return selectNum;
        }

        public void setSelectNum(int selectNum) {
            this.selectNum = selectNum;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }*/


}



