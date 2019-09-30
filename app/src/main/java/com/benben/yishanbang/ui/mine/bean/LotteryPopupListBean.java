package com.benben.yishanbang.ui.mine.bean;

/**
 * Created by lff
 * on 2019/8/26 0026
 * Describe:
 */
public class LotteryPopupListBean {

    /**
     * code : 1
     * msg : 操作成功
     * time : 1566815810264
     * data : {"userId":"402881ef6bc55fe1016bc67825320003","prizeGarde":"4","prizeNumber":9,"prizeId":"jp10003","userDrawId":"402881106ccd5450016ccd54b8910000","overCount":-9,"message":"杯子"}
     */
    /**
     * userId : 402881ef6bc55fe1016bc67825320003
     * prizeGarde : 4
     * prizeNumber : 9
     * prizeId : jp10003
     * userDrawId : 402881106ccd5450016ccd54b8910000
     * overCount : -9
     * message : 杯子
     */

    private String userId;
    private String prizeGarde;
    private int prizeNumber;
    private String prizeId;
    private String userDrawId;
    private int overCount;
    private String message;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPrizeGarde() {
        return prizeGarde;
    }

    public void setPrizeGarde(String prizeGarde) {
        this.prizeGarde = prizeGarde;
    }

    public int getPrizeNumber() {
        return prizeNumber;
    }

    public void setPrizeNumber(int prizeNumber) {
        this.prizeNumber = prizeNumber;
    }

    public String getPrizeId() {
        return prizeId;
    }

    public void setPrizeId(String prizeId) {
        this.prizeId = prizeId;
    }

    public String getUserDrawId() {
        return userDrawId;
    }

    public void setUserDrawId(String userDrawId) {
        this.userDrawId = userDrawId;
    }

    public int getOverCount() {
        return overCount;
    }

    public void setOverCount(int overCount) {
        this.overCount = overCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

