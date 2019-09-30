package com.benben.yishanbang.ui.discount.bean;

//店铺商品分类信息
public class ShopCateInfoBean {

    /**
     * sortId : 2
     * categoryName : 奶昔
     * categoryId : gc10001
     */

    private int sortId;
    private String categoryName;
    private String categoryId;
    private boolean isSelect = false;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
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
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
