package com.benben.yishanbang.ui.tea.bean;

import java.util.List;

/**
 * Author:zhn
 * Time:2019/8/26 0026 9:35
 */
public class ShowGoodsCategoryBean {
    public String imageUrl;

    public List<ShowGoodsCategoryBeanList> getShowGoodsCategory() {
        return showGoodsCategory;
    }

    public void setShowGoodsCategory(List<ShowGoodsCategoryBeanList> showGoodsCategory) {
        this.showGoodsCategory = showGoodsCategory;
    }

    @Override
    public String toString() {
        return "ShowGoodsCategoryBean{" +
                "imageUrl='" + imageUrl + '\'' +
                ", showGoodsCategory=" + showGoodsCategory +
                '}';
    }

    public List<ShowGoodsCategoryBeanList> showGoodsCategory;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public class ShowGoodsCategoryBeanList{
        public int sortId;

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

        @Override
        public String toString() {
            return "ShowGoodsCategoryBeanList{" +
                    "sortId=" + sortId +
                    ", categoryName='" + categoryName + '\'' +
                    ", categoryId='" + categoryId + '\'' +
                    ", select=" + select +
                    '}';
        }

        public String categoryName;
        public String categoryId;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        private boolean select = false;
    }
}
