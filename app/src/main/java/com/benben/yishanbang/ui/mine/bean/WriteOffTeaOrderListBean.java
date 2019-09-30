package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

//核销奶茶订单列表
public class WriteOffTeaOrderListBean {

    /**
     * foodTime :
     * milkTeaOrderVo : {"status":"3","codeImage":"","orderNo":"","goodsMoney":224,"foodcode":"","orderTime":"2019-08-22 10:46:39","orderId":"2c91f41d6cb3c6fb016cb7380be50031","userId":"","milkTeaOrderGoodsVos":[{"id":"sp10001","goodsCount":3,"goodsName":"茉莉清茶"},{"id":"sp10000","goodsCount":1,"goodsName":"茉莉茶"},{"id":"sp10000","goodsCount":1,"goodsName":"茉莉茶"}]}
     * user : {"mobile":"18503840250","nickname":"小哥","avatar":"upload/user/20190829/5a381f62301945828d50e476c3e29959_1567081791356.jpg","userId":"402881ef6bc55fe1016bc67825320003"}
     */

    private String foodTime;
    private MilkTeaOrderVoBean milkTeaOrderVo;
    private UserBean user;

    public String getFoodTime() {
        return foodTime;
    }

    public void setFoodTime(String foodTime) {
        this.foodTime = foodTime;
    }

    public MilkTeaOrderVoBean getMilkTeaOrderVo() {
        return milkTeaOrderVo;
    }

    public void setMilkTeaOrderVo(MilkTeaOrderVoBean milkTeaOrderVo) {
        this.milkTeaOrderVo = milkTeaOrderVo;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class MilkTeaOrderVoBean {
        /**
         * status : 3
         * codeImage :
         * orderNo :
         * goodsMoney : 224
         * foodcode :
         * orderTime : 2019-08-22 10:46:39
         * orderId : 2c91f41d6cb3c6fb016cb7380be50031
         * userId :
         * milkTeaOrderGoodsVos : [{"id":"sp10001","goodsCount":3,"goodsName":"茉莉清茶"},{"id":"sp10000","goodsCount":1,"goodsName":"茉莉茶"},{"id":"sp10000","goodsCount":1,"goodsName":"茉莉茶"}]
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
             * id : sp10001
             * goodsCount : 3
             * goodsName : 茉莉清茶
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

    public static class UserBean {
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
}
