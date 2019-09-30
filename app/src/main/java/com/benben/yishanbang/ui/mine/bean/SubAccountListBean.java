package com.benben.yishanbang.ui.mine.bean;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 子账号列表
 */
public class SubAccountListBean {


    /**
     * nickName :
     * control : 1,2,4
     * userId : 2c91f41d6cae67a9016caec0c3b2001e
     * iphone : 15936227018
     */

    private String nickName;
    private String control;
    private String userId;
    private String iphone;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getIphone() {
        return iphone;
    }

    public void setIphone(String iphone) {
        this.iphone = iphone;
    }
}
