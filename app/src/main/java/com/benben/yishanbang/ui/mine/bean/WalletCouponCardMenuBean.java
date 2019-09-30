package com.benben.yishanbang.ui.mine.bean;

/**
 * Created by Administrator on 2019/8/22 0022
 * Describe:我的优惠卡菜单
 */
public class WalletCouponCardMenuBean {

    /**
     * sortId : 10
     * categoryName : 美食
     * categoryId : gc10009
     */

    private int sortId;
    private String categoryName;
    private String categoryId;

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
