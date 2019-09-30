package com.benben.yishanbang.ui.mine.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by: wanghk 2019-08-08.
 * Describe: 我的服务
 */
public class MyServicesListBean implements Serializable {

    private List<OrderServerBean> orderServer;
    private List<UserBean> user;

    public List<OrderServerBean> getOrderServer() {
        return orderServer;
    }

    public void setOrderServer(List<OrderServerBean> orderServer) {
        this.orderServer = orderServer;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class OrderServerBean implements Serializable {
        private String id;
        private String server_category;
        private String user_id;
        private String user_ask_id;
        private String type;
        private String mark_id;
        private String takeType;
        private String time;
        private String end_time;
        private String name;
        private String address;
        private String message;
        private String price;
        private String status;
        private String nickname;
        private String mobile;

        public String getStatus() {
            return status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getServer_category() {
            return server_category;
        }

        public void setServer_category(String server_category) {
            this.server_category = server_category;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_ask_id() {
            return user_ask_id;
        }

        public void setUser_ask_id(String user_ask_id) {
            this.user_ask_id = user_ask_id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getMark_id() {
            return mark_id;
        }

        public void setMark_id(String mark_id) {
            this.mark_id = mark_id;
        }

        public String getTakeType() {
            return takeType;
        }

        public void setTakeType(String takeType) {
            this.takeType = takeType;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }

    public static class UserBean {
        private String takeType;
        private String nickname;
        private String mobile;

        public String getTakeType() {
            return takeType;
        }

        public void setTakeType(String takeType) {
            this.takeType = takeType;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
