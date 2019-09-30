package com.benben.yishanbang.ui.tea.bean;

import java.util.List;

/**
 * Created by Administrator on 2019/8/21 0021
 * Describe:商店详情
 */
public class StoresDetailsBean {

    /**
     * code : 1
     * msg : 操作成功
     * time : 1566381186689
     * data : {"shopEndtime":"21:00:00","shopId":"sp10004","shopName":"春店","shopStarttime":"","shoptImg":"user/20190711/0c689536278e1e85f4564da84e588ab9_1562815494824.jpg","shopPlace":"郑州市中原区","latitude":113.535912,"longitude":34.817169,"weekStarttime":"09:30:00","weekEndtime":"21:00:00","categoryId":"","shopImgs":[]}
     */
        /**
         * shopEndtime : 21:00:00
         * shopId : sp10004
         * shopName : 春店
         * shopStarttime :
         * shoptImg : user/20190711/0c689536278e1e85f4564da84e588ab9_1562815494824.jpg
         * shopPlace : 郑州市中原区
         * latitude : 113.535912
         * longitude : 34.817169
         * weekStarttime : 09:30:00
         * weekEndtime : 21:00:00
         * categoryId :
         * shopImgs : []
         */

        private String shopEndtime;
        private String shopId;
        private String shopName;
        private String shopStarttime;
        private String shoptImg;
        private String shopPlace;
        private double latitude;
        private double longitude;
        private String weekStarttime;
        private String weekEndtime;
        private String categoryId;
        private List<String> shopImgs;

        public String getShopEndtime() {
            return shopEndtime;
        }

        public void setShopEndtime(String shopEndtime) {
            this.shopEndtime = shopEndtime;
        }

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

        public String getShopStarttime() {
            return shopStarttime;
        }

        public void setShopStarttime(String shopStarttime) {
            this.shopStarttime = shopStarttime;
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

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public String getWeekStarttime() {
            return weekStarttime;
        }

        public void setWeekStarttime(String weekStarttime) {
            this.weekStarttime = weekStarttime;
        }

        public String getWeekEndtime() {
            return weekEndtime;
        }

        public void setWeekEndtime(String weekEndtime) {
            this.weekEndtime = weekEndtime;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public List<String> getShopImgs() {
            return shopImgs;
        }

        public void setShopImgs(List<String> shopImgs) {
            this.shopImgs = shopImgs;
        }
    }

