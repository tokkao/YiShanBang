package com.benben.yishanbang.ui.discount.bean;

//优惠卡-答题bean
public class QuestionBean {

    /**
     * title : 脑经急转弯
     * msgA : 棉花重
     * msgB : 女生重
     * msgC : 都重
     * msgD : 都不对
     * titlebankid : 1
     * mags : 一百斤棉花和一个一百斤的女生那个重？
     * id : wt10000
     */

    private String title;
    private String msgA;
    private String msgB;
    private String msgC;
    private String msgD;
    private String titlebankid;
    private String mags;
    private String id;
    private boolean a_status;//0选择 1未选
    private boolean b_status;//0选择 1未选
    private boolean c_status;//0选择 1未选
    private boolean d_status;//0选择 1未选

    public boolean isA_status() {
        return a_status;
    }

    public void setA_status(boolean a_status) {
        this.a_status = a_status;
    }

    public boolean isB_status() {
        return b_status;
    }

    public void setB_status(boolean b_status) {
        this.b_status = b_status;
    }

    public boolean isC_status() {
        return c_status;
    }

    public void setC_status(boolean c_status) {
        this.c_status = c_status;
    }

    public boolean isD_status() {
        return d_status;
    }

    public void setD_status(boolean d_status) {
        this.d_status = d_status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsgA() {
        return msgA;
    }

    public void setMsgA(String msgA) {
        this.msgA = msgA;
    }

    public String getMsgB() {
        return msgB;
    }

    public void setMsgB(String msgB) {
        this.msgB = msgB;
    }

    public String getMsgC() {
        return msgC;
    }

    public void setMsgC(String msgC) {
        this.msgC = msgC;
    }

    public String getMsgD() {
        return msgD;
    }

    public void setMsgD(String msgD) {
        this.msgD = msgD;
    }

    public String getTitlebankid() {
        return titlebankid;
    }

    public void setTitlebankid(String titlebankid) {
        this.titlebankid = titlebankid;
    }

    public String getMags() {
        return mags;
    }

    public void setMags(String mags) {
        this.mags = mags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
