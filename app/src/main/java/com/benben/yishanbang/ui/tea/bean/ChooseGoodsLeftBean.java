package com.benben.yishanbang.ui.tea.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2019/8/14 0014
 * Describe:选择商品左侧
 */
public class ChooseGoodsLeftBean {

    public List<ChooseList> list;

    public List<ChooseList> getList() {
        return list;
    }

    public void setList(List<ChooseList> list) {
        this.list = list;
    }

    public class ChooseList {
        private boolean select;

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }


}
