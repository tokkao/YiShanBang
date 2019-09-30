package com.benben.yishanbang.bean;

import com.flyco.tablayout.listener.CustomTabEntity;

/**
 * 目前用于首页，也可以使用与其他地方
 */
public class TabBean<T> implements CustomTabEntity {

    private T tabKey;
    private String tabName;

    public TabBean(T tabKey, String tabName) {
        this.tabKey = tabKey;
        this.tabName = tabName;
    }

    public T getTabKey() {
        return tabKey;
    }

    @Override
    public String getTabTitle() {
        return tabName;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }

}
