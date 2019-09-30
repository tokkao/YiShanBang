package com.benben.yishanbang.ui.service.bean;

//服务分类bean
public class ServiceCateListBean {
    private String cate_name;

    public ServiceCateListBean(String cate_name) {
        this.cate_name = cate_name;
    }

    public String getCate_name() {
        return cate_name;
    }

    public void setCate_name(String cate_name) {
        this.cate_name = cate_name;
    }
}
