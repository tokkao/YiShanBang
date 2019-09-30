package com.benben.yishanbang.ui.mine.bean;

import java.util.List;

/**
 * Created by lff
 * on 2019/8/27 0027
 * Describe://核销奶茶列表
 */
public class AfterMyTeaBean {

    /**
     * code : 1
     * msg : 操作成功
     * time : 1566901154837
     * data : [{"userEntity":{"updateName":"","updateBy":"","updateDate":"","createDate":"","roleName":"","email":"","userType":"3","sex":"1","citizenNo":"410223198904027577","mobile":"10010","realname":"1255","createName":"","createBy":"","shopId":"402880316c8da513016c8f4aef60000a","nickname":"李易峰","avatar":"upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg","messageConstellation":"天平座","messageHight":167,"messageWeight":59,"jobEndTime":"","questionCount":11,"delFlag":"","salt":"","age":"","belongId":"","advantageTitle":"","advantageMessage":"","advantageShow":"","roleId":"","voiceUrl":"","videoUrl":"","jobStartTimer":"","sixStartTime":"","sixSendTime":"","sevenStartTime":"","sevenEndTime":"","birthday":"","qualification":"","loginfailure":"","isOldImg":"upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg","username":"","status":"","password":"0096f88a26c1a9ed","id":"402881ef6bc55fe1016bc67825320003"},"goodsName":"茉莉茶","goodsOrder":{"updateName":"","updateBy":"","updateDate":"","createDate":"","createName":"","createBy":"","goodsId":"sp10000","goodsCount":1,"specValue":"中","temperatureValue":"多","sugarValue":"常温","perPrice":"","orderId":"402880386c99e985016c9a045931000f","storeId":"402880126c73e54d016c752139e90005","status":"3","id":"2c91f41d6cb3c6fb016cb782fad70060"}}]
     */
    /**
     * userEntity : {"updateName":"","updateBy":"","updateDate":"","createDate":"","roleName":"","email":"","userType":"3","sex":"1","citizenNo":"410223198904027577","mobile":"10010","realname":"1255","createName":"","createBy":"","shopId":"402880316c8da513016c8f4aef60000a","nickname":"李易峰","avatar":"upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg","messageConstellation":"天平座","messageHight":167,"messageWeight":59,"jobEndTime":"","questionCount":11,"delFlag":"","salt":"","age":"","belongId":"","advantageTitle":"","advantageMessage":"","advantageShow":"","roleId":"","voiceUrl":"","videoUrl":"","jobStartTimer":"","sixStartTime":"","sixSendTime":"","sevenStartTime":"","sevenEndTime":"","birthday":"","qualification":"","loginfailure":"","isOldImg":"upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg","username":"","status":"","password":"0096f88a26c1a9ed","id":"402881ef6bc55fe1016bc67825320003"}
     * goodsName : 茉莉茶
     * goodsOrder : {"updateName":"","updateBy":"","updateDate":"","createDate":"","createName":"","createBy":"","goodsId":"sp10000","goodsCount":1,"specValue":"中","temperatureValue":"多","sugarValue":"常温","perPrice":"","orderId":"402880386c99e985016c9a045931000f","storeId":"402880126c73e54d016c752139e90005","status":"3","id":"2c91f41d6cb3c6fb016cb782fad70060"}
     */

    private UserEntityBean userEntity;
    private String goodsName;
    private GoodsOrderBean goodsOrder;

    public UserEntityBean getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntityBean userEntity) {
        this.userEntity = userEntity;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public GoodsOrderBean getGoodsOrder() {
        return goodsOrder;
    }

    public void setGoodsOrder(GoodsOrderBean goodsOrder) {
        this.goodsOrder = goodsOrder;
    }

    public static class UserEntityBean {
        /**
         * updateName :
         * updateBy :
         * updateDate :
         * createDate :
         * roleName :
         * email :
         * userType : 3
         * sex : 1
         * citizenNo : 410223198904027577
         * mobile : 10010
         * realname : 1255
         * createName :
         * createBy :
         * shopId : 402880316c8da513016c8f4aef60000a
         * nickname : 李易峰
         * avatar : upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg
         * messageConstellation : 天平座
         * messageHight : 167.0
         * messageWeight : 59.0
         * jobEndTime :
         * questionCount : 11
         * delFlag :
         * salt :
         * age :
         * belongId :
         * advantageTitle :
         * advantageMessage :
         * advantageShow :
         * roleId :
         * voiceUrl :
         * videoUrl :
         * jobStartTimer :
         * sixStartTime :
         * sixSendTime :
         * sevenStartTime :
         * sevenEndTime :
         * birthday :
         * qualification :
         * loginfailure :
         * isOldImg : upload/user/20190826/8639176ab23d4c04ad23586c174f58d0_1566805226599.jpg
         * username :
         * status :
         * password : 0096f88a26c1a9ed
         * id : 402881ef6bc55fe1016bc67825320003
         */

        private String updateName;
        private String updateBy;
        private String updateDate;
        private String createDate;
        private String roleName;
        private String email;
        private String userType;
        private String sex;
        private String citizenNo;
        private String mobile;
        private String realname;
        private String createName;
        private String createBy;
        private String shopId;
        private String nickname;
        private String avatar;
        private String messageConstellation;
        private double messageHight;
        private double messageWeight;
        private String jobEndTime;
        private int questionCount;
        private String delFlag;
        private String salt;
        private String age;
        private String belongId;
        private String advantageTitle;
        private String advantageMessage;
        private String advantageShow;
        private String roleId;
        private String voiceUrl;
        private String videoUrl;
        private String jobStartTimer;
        private String sixStartTime;
        private String sixSendTime;
        private String sevenStartTime;
        private String sevenEndTime;
        private String birthday;
        private String qualification;
        private String loginfailure;
        private String isOldImg;
        private String username;
        private String status;
        private String password;
        private String id;

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getCitizenNo() {
            return citizenNo;
        }

        public void setCitizenNo(String citizenNo) {
            this.citizenNo = citizenNo;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getMessageConstellation() {
            return messageConstellation;
        }

        public void setMessageConstellation(String messageConstellation) {
            this.messageConstellation = messageConstellation;
        }

        public double getMessageHight() {
            return messageHight;
        }

        public void setMessageHight(double messageHight) {
            this.messageHight = messageHight;
        }

        public double getMessageWeight() {
            return messageWeight;
        }

        public void setMessageWeight(double messageWeight) {
            this.messageWeight = messageWeight;
        }

        public String getJobEndTime() {
            return jobEndTime;
        }

        public void setJobEndTime(String jobEndTime) {
            this.jobEndTime = jobEndTime;
        }

        public int getQuestionCount() {
            return questionCount;
        }

        public void setQuestionCount(int questionCount) {
            this.questionCount = questionCount;
        }

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getBelongId() {
            return belongId;
        }

        public void setBelongId(String belongId) {
            this.belongId = belongId;
        }

        public String getAdvantageTitle() {
            return advantageTitle;
        }

        public void setAdvantageTitle(String advantageTitle) {
            this.advantageTitle = advantageTitle;
        }

        public String getAdvantageMessage() {
            return advantageMessage;
        }

        public void setAdvantageMessage(String advantageMessage) {
            this.advantageMessage = advantageMessage;
        }

        public String getAdvantageShow() {
            return advantageShow;
        }

        public void setAdvantageShow(String advantageShow) {
            this.advantageShow = advantageShow;
        }

        public String getRoleId() {
            return roleId;
        }

        public void setRoleId(String roleId) {
            this.roleId = roleId;
        }

        public String getVoiceUrl() {
            return voiceUrl;
        }

        public void setVoiceUrl(String voiceUrl) {
            this.voiceUrl = voiceUrl;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getJobStartTimer() {
            return jobStartTimer;
        }

        public void setJobStartTimer(String jobStartTimer) {
            this.jobStartTimer = jobStartTimer;
        }

        public String getSixStartTime() {
            return sixStartTime;
        }

        public void setSixStartTime(String sixStartTime) {
            this.sixStartTime = sixStartTime;
        }

        public String getSixSendTime() {
            return sixSendTime;
        }

        public void setSixSendTime(String sixSendTime) {
            this.sixSendTime = sixSendTime;
        }

        public String getSevenStartTime() {
            return sevenStartTime;
        }

        public void setSevenStartTime(String sevenStartTime) {
            this.sevenStartTime = sevenStartTime;
        }

        public String getSevenEndTime() {
            return sevenEndTime;
        }

        public void setSevenEndTime(String sevenEndTime) {
            this.sevenEndTime = sevenEndTime;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getLoginfailure() {
            return loginfailure;
        }

        public void setLoginfailure(String loginfailure) {
            this.loginfailure = loginfailure;
        }

        public String getIsOldImg() {
            return isOldImg;
        }

        public void setIsOldImg(String isOldImg) {
            this.isOldImg = isOldImg;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class GoodsOrderBean {
        /**
         * updateName :
         * updateBy :
         * updateDate :
         * createDate :
         * createName :
         * createBy :
         * goodsId : sp10000
         * goodsCount : 1
         * specValue : 中
         * temperatureValue : 多
         * sugarValue : 常温
         * perPrice :
         * orderId : 402880386c99e985016c9a045931000f
         * storeId : 402880126c73e54d016c752139e90005
         * status : 3
         * id : 2c91f41d6cb3c6fb016cb782fad70060
         */

        private String updateName;
        private String updateBy;
        private String updateDate;
        private String createDate;
        private String createName;
        private String createBy;
        private String goodsId;
        private int goodsCount;
        private String specValue;
        private String temperatureValue;
        private String sugarValue;
        private String perPrice;
        private String orderId;
        private String storeId;
        private String status;
        private String id;

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(String goodsId) {
            this.goodsId = goodsId;
        }

        public int getGoodsCount() {
            return goodsCount;
        }

        public void setGoodsCount(int goodsCount) {
            this.goodsCount = goodsCount;
        }

        public String getSpecValue() {
            return specValue;
        }

        public void setSpecValue(String specValue) {
            this.specValue = specValue;
        }

        public String getTemperatureValue() {
            return temperatureValue;
        }

        public void setTemperatureValue(String temperatureValue) {
            this.temperatureValue = temperatureValue;
        }

        public String getSugarValue() {
            return sugarValue;
        }

        public void setSugarValue(String sugarValue) {
            this.sugarValue = sugarValue;
        }

        public String getPerPrice() {
            return perPrice;
        }

        public void setPerPrice(String perPrice) {
            this.perPrice = perPrice;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getStoreId() {
            return storeId;
        }

        public void setStoreId(String storeId) {
            this.storeId = storeId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}

