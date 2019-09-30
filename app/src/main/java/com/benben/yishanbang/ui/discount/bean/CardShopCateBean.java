package com.benben.yishanbang.ui.discount.bean;

import com.benben.commoncore.utils.StringUtils;

//优惠卡店铺分类列表
public class CardShopCateBean {


    /**
     * sortId : 10
     * categoryName : 美食
     * categoryId : gc10009
     */

    private int sortId;
    private String id;
    private String categoryName;
    private String categoryId;
    private boolean isSelected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

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
        if (StringUtils.isEmpty(categoryId)) {
            return id;
        }
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
