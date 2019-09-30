package com.benben.yishanbang.ui.home.bean;

import java.util.List;

//消息列表
public class MessageListBean {


    /**
     * number : 2
     * announcementList : [{"id":"2","titile":"2","msg_content":"2","send_time":"2019-09-28 11:01:27","img_url":"1","del_flag":"1","create_by":"","create_date":"","update_by":"","update_date":"","create_name":"","update_name":""},{"id":"1","titile":"1","msg_content":"1","send_time":"2019-09-27 11:01:17","img_url":"1","del_flag":"1","create_by":"","create_date":"","update_by":"","update_date":"","create_name":"","update_name":""}]
     */

    private int number;
    private List<AnnouncementListBean> announcementList;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<AnnouncementListBean> getAnnouncementList() {
        return announcementList;
    }

    public void setAnnouncementList(List<AnnouncementListBean> announcementList) {
        this.announcementList = announcementList;
    }

    public static class AnnouncementListBean {
        /**
         * id : 2
         * titile : 2
         * msg_content : 2
         * send_time : 2019-09-28 11:01:27
         * img_url : 1
         * del_flag : 1
         * create_by :
         * create_date :
         * update_by :
         * update_date :
         * create_name :
         * update_name :
         */

        private String id;
        private String titile;
        private String msg_content;
        private String send_time;
        private String img_url;
        private String del_flag;
        private String create_by;
        private String create_date;
        private String update_by;
        private String update_date;
        private String create_name;
        private String update_name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitile() {
            return titile;
        }

        public void setTitile(String titile) {
            this.titile = titile;
        }

        public String getMsg_content() {
            return msg_content;
        }

        public void setMsg_content(String msg_content) {
            this.msg_content = msg_content;
        }

        public String getSend_time() {
            return send_time;
        }

        public void setSend_time(String send_time) {
            this.send_time = send_time;
        }

        public String getImg_url() {
            return img_url;
        }

        public void setImg_url(String img_url) {
            this.img_url = img_url;
        }

        public String getDel_flag() {
            return del_flag;
        }

        public void setDel_flag(String del_flag) {
            this.del_flag = del_flag;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getCreate_date() {
            return create_date;
        }

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public String getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(String update_by) {
            this.update_by = update_by;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public String getCreate_name() {
            return create_name;
        }

        public void setCreate_name(String create_name) {
            this.create_name = create_name;
        }

        public String getUpdate_name() {
            return update_name;
        }

        public void setUpdate_name(String update_name) {
            this.update_name = update_name;
        }
    }
}
