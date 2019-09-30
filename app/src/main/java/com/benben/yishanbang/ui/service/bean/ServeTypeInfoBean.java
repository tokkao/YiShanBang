package com.benben.yishanbang.ui.service.bean;

import java.util.List;

/**
 * @author Jay
 * @email 1136189725@qq.com  有事来邮!!!
 * @date 2019/9/20 0020
 * 技能帮扶  更多里的bean
 */
public class ServeTypeInfoBean {
    private List<ServeTypeBean> serverType;
    private String name;
    private String id;

    public List<ServeTypeBean> getServerType() {
        return serverType;
    }

    public void setServerType(List<ServeTypeBean> serverType) {
        this.serverType = serverType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class ServeTypeBean {
        private String id;
        private String type;
        private String name;
        private String message;
        private String parent_id;
        private String url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
