package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 我的抽奖
 */
public class MyLotteryListBean {

    /**
     * code : 1
     * msg : 操作成功
     * time : 1566986592692
     * data : [{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6eb81970003","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6ec4b560004","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6ec4f880005","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6ec53470006","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6ec570a0007","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6ec5b030008","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6ec5e6e0009","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:00","id":"402881106cd65c5a016cd6ece5a7000a","state":"2"},{"shopPlace":"郑州市中原区郑州市中原区","goodsName":["香蕉奶茶","叶子奶茶"],"startTime":"2019-09-01 10:40:08","id":"402881106cd65c5a016cd7012643000b","state":"2"}]
     */

    /**
     * shopPlace : 郑州市中原区郑州市中原区
     * goodsName : ["香蕉奶茶","叶子奶茶"]
     * startTime : 2019-09-01 10:00
     * id : 402881106cd65c5a016cd6eb81970003
     * state : 2
     */

    private String shopPlace;
    private String startTime;
    private String id;
    private String state;
    private List<String> goodsName;

    public String getShopPlace() {
        return shopPlace;
    }

    public void setShopPlace(String shopPlace) {
        this.shopPlace = shopPlace;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(List<String> goodsName) {
        this.goodsName = goodsName;
    }
}
