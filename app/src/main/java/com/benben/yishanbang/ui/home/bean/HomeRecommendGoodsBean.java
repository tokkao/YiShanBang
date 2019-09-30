package com.benben.yishanbang.ui.home.bean;

import java.util.List;

//首页推荐商品/店铺/视频信息
public class HomeRecommendGoodsBean {

    private List<CardVoBean> cardVo;
    private List<CardShopVoBean> cardShopVo;
    private List<AdvertVoBean> advertVo;

    public List<CardVoBean> getCardVo() {
        return cardVo;
    }

    public void setCardVo(List<CardVoBean> cardVo) {
        this.cardVo = cardVo;
    }

    public List<CardShopVoBean> getCardShopVo() {
        return cardShopVo;
    }

    public void setCardShopVo(List<CardShopVoBean> cardShopVo) {
        this.cardShopVo = cardShopVo;
    }

    public List<AdvertVoBean> getAdvertVo() {
        return advertVo;
    }

    public void setAdvertVo(List<AdvertVoBean> advertVo) {
        this.advertVo = advertVo;
    }

    public static class CardVoBean {
        /**
         * id : yhk10003
         * name : 红豆奶茶
         * price : 26.65
         * imgUrl : upload/img/photosucai/20190812/325717-130GG9515094_1565582765730.jpg
         * questionType : 0
         * number : 123
         */

        private String id;
        private String name;
        private double price;
        private String imgUrl;
        private String questionType;
        private int number;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getQuestionType() {
            return questionType;
        }

        public void setQuestionType(String questionType) {
            this.questionType = questionType;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    public static class CardShopVoBean {
        /**
         * shopName : tom
         * shoptImg : upload/image/20190819/381d9a4e4f11342e8e6ee8c87fdc07d6_1566195128233.jpg,
         * id : 402880126c71020a016c715999b40003
         */

        private String shopName;
        private String shoptImg;
        private String id;

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }

        public String getShoptImg() {
            return shoptImg;
        }

        public void setShoptImg(String shoptImg) {
            this.shoptImg = shoptImg;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class AdvertVoBean {
        /**
         * id : 402881e96b633031016b633031250000
         * title : aaa
         * shopId :
         * content : a
         * time : 2019-06-17
         * address : aaa
         * shopName : aaa
         */

        private String id;
        private String title;
        private String shopId;
        private String content;
        private String time;
        private String address;
        private String shopName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getShopName() {
            return shopName;
        }

        public void setShopName(String shopName) {
            this.shopName = shopName;
        }
    }
}
