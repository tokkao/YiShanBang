package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by lff
 * on 2019/8/26 0026
 * Describe:抽奖活动返回数据
 */
public class LotteryActiveBean {

    /**
     * time : 1566627197340
     * data : {"activityVo":{"id":"402881ef6bac3941016bac3941790001","startTime":"2019-09-01 10:40:08","startPeriod":"2019-08-01 10:39:54","endPeriod":"2019-08-31 10:40","userCount":100,"periodicState":"1"},"PrizeVos":[{"message":"华为手机","prizeGarde":"1","prizeCount":1,"prizeId":"jp10000"},{"message":"口红","prizeGarde":"2","prizeCount":2,"prizeId":"jp10001"},{"message":"被子","prizeGarde":"三等奖","prizeCount":3,"prizeId":"jp10002"},{"message":"杯子","prizeGarde":"4","prizeCount":4,"prizeId":"jp10003"}]}
     * code : 1
     * msg : 操作成功
     */
    /**
     * activityVo : {"id":"402881ef6bac3941016bac3941790001","startTime":"2019-09-01 10:40:08","startPeriod":"2019-08-01 10:39:54","endPeriod":"2019-08-31 10:40","userCount":100,"periodicState":"1"}
     * PrizeVos : [{"message":"华为手机","prizeGarde":"1","prizeCount":1,"prizeId":"jp10000"},{"message":"口红","prizeGarde":"2","prizeCount":2,"prizeId":"jp10001"},{"message":"被子","prizeGarde":"三等奖","prizeCount":3,"prizeId":"jp10002"},{"message":"杯子","prizeGarde":"4","prizeCount":4,"prizeId":"jp10003"}]
     */

    private ActivityVoBean activityVo;
    private List<PrizeVosBean> PrizeVos;

    public ActivityVoBean getActivityVo() {
        return activityVo;
    }

    public void setActivityVo(ActivityVoBean activityVo) {
        this.activityVo = activityVo;
    }

    public List<PrizeVosBean> getPrizeVos() {
        return PrizeVos;
    }

    public void setPrizeVos(List<PrizeVosBean> PrizeVos) {
        this.PrizeVos = PrizeVos;
    }

    public static class ActivityVoBean {
        /**
         * id : 402881ef6bac3941016bac3941790001
         * startTime : 2019-09-01 10:40:08
         * startPeriod : 2019-08-01 10:39:54
         * endPeriod : 2019-08-31 10:40
         * userCount : 100
         * periodicState : 1
         */

        private String id;
        private String startTime;
        private String startPeriod;
        private String endPeriod;
        private int userCount;
        private String periodicState;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getStartPeriod() {
            return startPeriod;
        }

        public void setStartPeriod(String startPeriod) {
            this.startPeriod = startPeriod;
        }

        public String getEndPeriod() {
            return endPeriod;
        }

        public void setEndPeriod(String endPeriod) {
            this.endPeriod = endPeriod;
        }

        public int getUserCount() {
            return userCount;
        }

        public void setUserCount(int userCount) {
            this.userCount = userCount;
        }

        public String getPeriodicState() {
            return periodicState;
        }

        public void setPeriodicState(String periodicState) {
            this.periodicState = periodicState;
        }
    }

    public static class PrizeVosBean {
        /**
         * message : 华为手机
         * prizeGarde : 1
         * prizeCount : 1
         * prizeId : jp10000
         */

        private String message;
        private String prizeGarde;
        private int prizeCount;
        private String prizeId;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPrizeGarde() {
            return prizeGarde;
        }

        public void setPrizeGarde(String prizeGarde) {
            this.prizeGarde = prizeGarde;
        }

        public int getPrizeCount() {
            return prizeCount;
        }

        public void setPrizeCount(int prizeCount) {
            this.prizeCount = prizeCount;
        }

        public String getPrizeId() {
            return prizeId;
        }

        public void setPrizeId(String prizeId) {
            this.prizeId = prizeId;
        }
    }
}

