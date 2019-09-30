package com.benben.yishanbang.ui.mine.bean;

/**
 * Created by Administrator on 2019/8/23 0023
 * Describe:实名认证返回数据
 */
public class VerifiedBean {

    /**
     * code : 1
     * msg : 操作成功
     * time : 1566551834280
     * data : {"citizenNo":"1111","name":"嗯嗯","id":"2c91f41d6cb3c6fb016cb823a6b9007e"}
     */
    /**
     * citizenNo : 1111
     * name : 嗯嗯
     * id : 2c91f41d6cb3c6fb016cb823a6b9007e
     */

    private String citizenNo;
    private String name;
    private String id;

    public String getCitizenNo() {
        return citizenNo;
    }

    public void setCitizenNo(String citizenNo) {
        this.citizenNo = citizenNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
