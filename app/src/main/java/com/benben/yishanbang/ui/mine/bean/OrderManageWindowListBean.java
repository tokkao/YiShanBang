package com.benben.yishanbang.ui.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 奶茶-订单管理
 */
public class OrderManageWindowListBean implements Serializable {

    /**
     * "goodsCount":1,
     * "orderTime":"2019-09-20 09:38:29",
     * "orderNo":"8520041325",
     * "headImg":"upload/user/20190902/ecbbe126e1da43479ee209f64f755be9_1567430440826.png",
     * "orderMoney":48,
     * "mebile":"15936227019",
     * "orderId":"2c91f41d6d4c37ea016d4c520f51000a",
     * "nicekName":"李钟硕",
     * "goods":[
     * {
     * "id":"1",
     * "del_price":"",
     * "img_url":"假装有路径",
     * "count":"10",
     * "commission":"18",
     * "nice":"3",
     * "price":"48",
     * "name":"破洞牛仔裤",
     * "megssage":"锅盖头豆豆鞋紧身裤",
     * "category_id":"cysc001",
     * "delivery":"1",
     * "bottom_imgs":"假装有路径",
     * "create_date":"",
     * "create_by":"",
     * "create_name":"",
     * "update_date":"",
     * "update_by":"",
     * "update_name":""
     * }
     * ],
     * "userId":"2c91f41d6cae67a9016cb19b5388002b"
     */

    private String goodsCount;
    private String orderTime;
    private String orderNo;
    private String headImg;
    private String orderMoney;
    private String mebile;
    private String orderId;
    private String nicekName;
    private String userId;
    private List<GoodBean> goods;

    public String getGoodsCount() {
        return goodsCount;
    }

    public void setGoodsCount(String goodsCount) {
        this.goodsCount = goodsCount;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getMebile() {
        return mebile;
    }

    public void setMebile(String mebile) {
        this.mebile = mebile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getNicekName() {
        return nicekName;
    }

    public void setNicekName(String nicekName) {
        this.nicekName = nicekName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<GoodBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodBean> goods) {
        this.goods = goods;
    }

    public static class GoodBean {
        private String id;
        private String del_price;
        private String img_url;
        private String count;
        private String commission;
        private String nice;
        private String price;
        private String name;
        private String megssage;

        private String category_id;
        private String delivery;
        private String bottom_imgs;
        private String create_date;
        private String create_by;
        private String create_name;
        private String update_date;
        private String update_by;
        private String update_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDel_price() {
            return del_price;
        }

        public void setDel_price(String del_price) {
            this.del_price = del_price;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCommission() {
            return commission;
        }

        public void setCommission(String commission) {
            this.commission = commission;
        }

        public String getNice() {
            return nice;
        }

        public void setNice(String nice) {
            this.nice = nice;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMegssage() {
            return megssage;
        }

        public void setMegssage(String megssage) {
            this.megssage = megssage;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getDelivery() {
            return delivery;
        }

        public void setDelivery(String delivery) {
            this.delivery = delivery;
        }

        public String getBottom_imgs() {
            return bottom_imgs;
        }

        public void setBottom_imgs(String bottom_imgs) {
            this.bottom_imgs = bottom_imgs;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getCreate_name() {
            return create_name;
        }

        public void setCreate_name(String create_name) {
            this.create_name = create_name;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(String update_by) {
            this.update_by = update_by;
        }

        public String getUpdate_name() {
            return update_name;
        }

        public void setUpdate_name(String update_name) {
            this.update_name = update_name;
        }
    }

}
