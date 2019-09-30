package com.benben.yishanbang.ui.mine.bean;

public class AddressBean {

    /**
     * id : 11
     * user_name : 张三
     * is_default : 1
     * user_phone : 15501296808
     * user_address : 河南省郑州市二七区张三
     */

    private String id;

    private String userId;
    private String detailedAddress;//详细地址
    private String areac;//城市
    private String name;
    private String mobile;
    private String defaultFlag;//是否默认（1：默认）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public String getAreac() {
        return areac;
    }

    public void setAreac(String areac) {
        this.areac = areac;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(String defaultFlag) {
        this.defaultFlag = defaultFlag;
    }
}
