package com.benben.yishanbang.ui.service.bean;

public class TabEntityBean {
    private  String tab_name;
    private  boolean isSelected;

    public TabEntityBean(String tab_name, boolean isSelected) {
        this.tab_name = tab_name;
        this.isSelected = isSelected;
    }

    public String getTab_name() {
        return tab_name;
    }

    public void setTab_name(String tab_name) {
        this.tab_name = tab_name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
