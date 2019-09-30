package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 核销奶茶订单详情
 */
public class WriteOffTeaOrderDetailsBean {

    /**
     * userEntity : {"mobile":"18503840250","nickname":"小哥","avatar":"upload/user/20190829/5a381f62301945828d50e476c3e29959_1567081791356.jpg","userId":"402881ef6bc55fe1016bc67825320003"}
     * milkTeaOrderVo : {"status":"8","codeImage":"","orderNo":"","goodsMoney":224,"foodcode":"","orderTime":"2019-07-02 18:50:19","orderId":"1711329512","userId":"","milkTeaOrderGoodsVos":[{"id":"sp10000","goodsCount":1,"goodsName":"茉莉茶"}]}
     * foodCode : 123
     */

    private UserEntityBean userEntity;
    private MilkTeaOrderVoBean milkTeaOrderVo;
    private String foodCode;

    public UserEntityBean getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntityBean userEntity) {
        this.userEntity = userEntity;
    }

    public MilkTeaOrderVoBean getMilkTeaOrderVo() {
        return milkTeaOrderVo;
    }

    public void setMilkTeaOrderVo(MilkTeaOrderVoBean milkTeaOrderVo) {
        this.milkTeaOrderVo = milkTeaOrderVo;
    }

    public String getFoodCode() {
        return foodCode;
    }

    public void setFoodCode(String foodCode) {
        this.foodCode = foodCode;
    }

    public static class UserEntityBean {
        /**
         * mobile : 18503840250
         * nickname : 小哥
         * avatar : upload/user/20190829/5a381f62301945828d50e476c3e29959_1567081791356.jpg
         * userId : 402881ef6bc55fe1016bc67825320003
         */

        private String mobile;
        private String nickname;
        private String avatar;
        private String userId;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class MilkTeaOrderVoBean {
        /**
         * status : 8
         * codeImage :
         * orderNo :
         * goodsMoney : 224
         * foodcode :
         * orderTime : 2019-07-02 18:50:19
         * orderId : 1711329512
         * userId :
         * milkTeaOrderGoodsVos : [{"id":"sp10000","goodsCount":1,"goodsName":"茉莉茶"}]
         */

        private String status;
        private String codeImage;
        private String orderNo;
        private String goodsMoney;
        private String foodcode;
        private String orderTime;
        private String orderId;
        private String userId;
        private List<MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCodeImage() {
            return codeImage;
        }

        public void setCodeImage(String codeImage) {
            this.codeImage = codeImage;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getGoodsMoney() {
            return goodsMoney;
        }

        public void setGoodsMoney(String goodsMoney) {
            this.goodsMoney = goodsMoney;
        }

        public String getFoodcode() {
            return foodcode;
        }

        public void setFoodcode(String foodcode) {
            this.foodcode = foodcode;
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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public List<MilkTeaOrderGoodsVosBean> getMilkTeaOrderGoodsVos() {
            return milkTeaOrderGoodsVos;
        }

        public void setMilkTeaOrderGoodsVos(List<MilkTeaOrderGoodsVosBean> milkTeaOrderGoodsVos) {
            this.milkTeaOrderGoodsVos = milkTeaOrderGoodsVos;
        }

        public static class MilkTeaOrderGoodsVosBean {
            /**
             * id : sp10000
             * goodsCount : 1
             * goodsName : 茉莉茶
             */

            private String id;
            private int goodsCount;
            private String goodsName;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getGoodsCount() {
                return goodsCount;
            }

            public void setGoodsCount(int goodsCount) {
                this.goodsCount = goodsCount;
            }

            public String getGoodsName() {
                return goodsName;
            }

            public void setGoodsName(String goodsName) {
                this.goodsName = goodsName;
            }
        }
    }
}
