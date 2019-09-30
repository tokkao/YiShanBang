package com.benben.yishanbang.ui.tea.bean;

import java.util.List;

//奶茶店铺商品信息
public class TeaShopGoodsInfoBean {

    /**
     * imageUrl :  upload/user/20190903/99d1a82f785d44fc9b3291986155d5f5_1567492340547.png
     * milkTeaGoodsVos : [{"sortId":6,"categoryName":"醇香奶茶","milkTeaGoodsVos":[{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10007","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":17,"goodsName":"娃哈哈奶茶","goodsType":"1","selectNum":"","description":"活动你回复","status":"0"},{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10008","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":13,"goodsName":"火龙果奶茶","goodsType":"0","selectNum":"","description":"个害己哦","status":"0"},{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10009","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":10,"goodsName":"好的奶茶","goodsType":"0","selectNum":"","description":"法院遂过后就起来更不易","status":"0"}],"categoryId":"gc10005"},{"sortId":7,"categoryName":"热卖","milkTeaGoodsVos":[{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10005","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":13.2,"goodsName":"魄罗奶茶","goodsType":"0","selectNum":"","description":"尼日附件二维就热后","status":"0"}],"categoryId":"gc10006"},{"sortId":8,"categoryName":"沁凉夏日","milkTeaGoodsVos":[{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10003","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":15.3,"goodsName":"葡萄奶茶","goodsType":"1","selectNum":"","description":"多久了开始的","status":"0"}],"categoryId":"gc10007"}]
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

    public static class MilkTeaGoodsVosBeanX {
        /**
         * sortId : 6
         * categoryName : 醇香奶茶
         * milkTeaGoodsVos : [{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10007","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":17,"goodsName":"娃哈哈奶茶","goodsType":"1","selectNum":"","description":"活动你回复","status":"0"},{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10008","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":13,"goodsName":"火龙果奶茶","goodsType":"0","selectNum":"","description":"个害己哦","status":"0"},{"shopId":"sp10000","price":0.01,"categoryId":"","goodsId":"nc10009","imgUrl":"upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png","delPrice":10,"goodsName":"好的奶茶","goodsType":"0","selectNum":"","description":"法院遂过后就起来更不易","status":"0"}]
         * categoryId : gc10005
         */

        private int sortId;
        private String categoryName;
        private String categoryId;
        private List<MilkTeaGoodsVosBean> milkTeaGoodsVos;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

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

        public static class MilkTeaGoodsVosBean {
            /**
             * shopId : sp10000
             * price : 0.01
             * categoryId :
             * goodsId : nc10007
             * imgUrl : upload/user/20190903/768e10355867449ebaba2347a8c526e0_1567492318053.png
             * delPrice : 17
             * goodsName : 娃哈哈奶茶
             * goodsType : 1
             * selectNum :
             * description : 活动你回复
             * status : 0
             */

            private String shopId;
            private double price;
            private String categoryId;
            private String goodsId;
            private String imgUrl;
            private int delPrice;
            private String goodsName;
            private String goodsType;
            private String selectNum;
            private String description;
            private String status;
            private int goodsCount;

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

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

            public String getCategoryId() {
                return categoryId;
            }

            public void setCategoryId(String categoryId) {
                this.categoryId = categoryId;
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
}
