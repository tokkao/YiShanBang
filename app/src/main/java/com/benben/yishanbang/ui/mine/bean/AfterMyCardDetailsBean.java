package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by lff
 * on 2019/8/27 0027
 * Describe:核销订单-核销优惠卡详情数据
 */
public class AfterMyCardDetailsBean {

    /**
     * orderTime : 2019-09-07 17:57:32
     * cardName : 蔬果奶茶4
     * AccountBillEntities : [{"updateDate":"","createDate":"2019-09-07 16:53:05","userId":"402881ef6bc55fe1016bc67825320003","createName":"","createBy":"","beforeMoney":111,"cardId":"yhk10000","updateName":"","updateBy":"","changeMoney":0.01,"afterMpney":110.99,"billType":"2","sign":"-","id":"222"}]
     * cardImg : upload/user/20190903/49466c677fac40a4a2b67fb93e1c2117_1567492255757.png
     * userName : 我真的裂开来咯啦咯来咯啦咯啦啦啦啦掏空了快乐啦啦啦啦了
     * pice : 0.01
     */

    private String orderTime;
    private String cardName;
    private String cardImg;
    private String userName;
    private double pice;
    private List<AccountBillEntitiesBean> AccountBillEntities;

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardImg() {
        return cardImg;
    }

    public void setCardImg(String cardImg) {
        this.cardImg = cardImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public double getPice() {
        return pice;
    }

    public void setPice(double pice) {
        this.pice = pice;
    }

    public List<AccountBillEntitiesBean> getAccountBillEntities() {
        return AccountBillEntities;
    }

    public void setAccountBillEntities(List<AccountBillEntitiesBean> AccountBillEntities) {
        this.AccountBillEntities = AccountBillEntities;
    }

    public static class AccountBillEntitiesBean {
        /**
         * updateDate :
         * createDate : 2019-09-07 16:53:05
         * userId : 402881ef6bc55fe1016bc67825320003
         * createName :
         * createBy :
         * beforeMoney : 111
         * cardId : yhk10000
         * updateName :
         * updateBy :
         * changeMoney : 0.01
         * afterMpney : 110.99
         * billType : 2
         * sign : -
         * id : 222
         */

        private String updateDate;
        private String createDate;
        private String userId;
        private String createName;
        private String createBy;
        private int beforeMoney;
        private String cardId;
        private String updateName;
        private String updateBy;
        private double changeMoney;
        private double afterMpney;
        private String billType;
        private String sign;
        private String id;

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public int getBeforeMoney() {
            return beforeMoney;
        }

        public void setBeforeMoney(int beforeMoney) {
            this.beforeMoney = beforeMoney;
        }

        public String getCardId() {
            return cardId;
        }

        public void setCardId(String cardId) {
            this.cardId = cardId;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public double getChangeMoney() {
            return changeMoney;
        }

        public void setChangeMoney(double changeMoney) {
            this.changeMoney = changeMoney;
        }

        public double getAfterMpney() {
            return afterMpney;
        }

        public void setAfterMpney(double afterMpney) {
            this.afterMpney = afterMpney;
        }

        public String getBillType() {
            return billType;
        }

        public void setBillType(String billType) {
            this.billType = billType;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
