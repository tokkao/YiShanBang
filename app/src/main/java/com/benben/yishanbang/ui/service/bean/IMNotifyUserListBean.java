package com.benben.yishanbang.ui.service.bean;

import java.util.List;

public class IMNotifyUserListBean {


    /**
     * youshi : [{"mage":"孝顺"},{"mage":"善良"},{"mage":"素颜"},{"mage":"真善美"}]
     * user : {"id":"402881ef6bc55fe1016bc67825320003","password":"463408fdf59bb9d9","status":"","username":"","salt":"","qualification":"","imJurisdiction":"1","advantageTitle":"我太帅了","advantageMessage":"就是帅的不太明显","sevenStartTime":"1970-01-01 12:00","shopId":"cysc001","questionCount":23,"mobile":"18503840250","realname":"1255","nickname":"帅哥","avatar":"upload/user/20190905/15c859520bf24fa8b045e6c5da8d5eb6_1567666244182.jpg","sex":"男","messageHight":160,"messageWeight":49,"citizenNo":"410223198904027577","jobEndTime":"1970-01-01 12:00","lastcCity":"郑州","balance":"0","advantageShow":"","roleId":"","voiceUrl":"","jobStartTimer":"1970-01-01 12:00","sixStartTime":"1970-01-01 12:00","sixSendTime":"1970-01-01 12:00","sevenEndTime":"1970-01-01 12:00","birthday":"","loginfailure":"","age":"","examine":"","userType":"5","email":"","belongId":"","userLatitude":4351.23123,"userLongitude":123.342,"videoUrl":"1561651","isOldimg":"upload/user/20190902/e86591423dd54573b1ec3f4b964df166_1567426573154.png","messageConstellation":"双鱼座","createName":"","updateDate":"2019-09-11 18:02:36","createBy":"","updateBy":"admin","updateName":"管理员","createDate":"","roleName":"","delFlag":""}
     */

    private UserBean user;
    private List<YoushiBean> youshi;
    private String number;
    private String cgId;

    private boolean isSelected;


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCgId() {
        return cgId;
    }

    public void setCgId(String cgId) {
        this.cgId = cgId;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public List<YoushiBean> getYoushi() {
        return youshi;
    }

    public void setYoushi(List<YoushiBean> youshi) {
        this.youshi = youshi;
    }

    public static class UserBean {
        /**
         * id : 402881ef6bc55fe1016bc67825320003
         * password : 463408fdf59bb9d9
         * status :
         * username :
         * salt :
         * qualification :
         * imJurisdiction : 1
         * advantageTitle : 我太帅了
         * advantageMessage : 就是帅的不太明显
         * sevenStartTime : 1970-01-01 12:00
         * shopId : cysc001
         * questionCount : 23
         * mobile : 18503840250
         * realname : 1255
         * nickname : 帅哥
         * avatar : upload/user/20190905/15c859520bf24fa8b045e6c5da8d5eb6_1567666244182.jpg
         * sex : 男
         * messageHight : 160
         * messageWeight : 49
         * citizenNo : 410223198904027577
         * jobEndTime : 1970-01-01 12:00
         * lastcCity : 郑州
         * balance : 0
         * advantageShow :
         * roleId :
         * voiceUrl :
         * jobStartTimer : 1970-01-01 12:00
         * sixStartTime : 1970-01-01 12:00
         * sixSendTime : 1970-01-01 12:00
         * sevenEndTime : 1970-01-01 12:00
         * birthday :
         * loginfailure :
         * age :
         * examine :
         * userType : 5
         * email :
         * belongId :
         * userLatitude : 4351.23123
         * userLongitude : 123.342
         * videoUrl : 1561651
         * isOldimg : upload/user/20190902/e86591423dd54573b1ec3f4b964df166_1567426573154.png
         * messageConstellation : 双鱼座
         * createName :
         * updateDate : 2019-09-11 18:02:36
         * createBy :
         * updateBy : admin
         * updateName : 管理员
         * createDate :
         * roleName :
         * delFlag :
         */

        private String id;
        private String password;
        private String status;
        private String username;
        private String salt;
        private String qualification;
        private String imJurisdiction;
        private String advantageTitle;
        private String advantageMessage;
        private String sevenStartTime;
        private String shopId;
        private int questionCount;
        private String mobile;
        private String realname;
        private String nickname;
        private String avatar;
        private String sex;
        private int messageHight;
        private int messageWeight;
        private String citizenNo;
        private String jobEndTime;
        private String lastcCity;
        private String balance;
        private String advantageShow;
        private String roleId;
        private String voiceUrl;
        private String jobStartTimer;
        private String sixStartTime;
        private String sixSendTime;
        private String sevenEndTime;
        private String birthday;
        private String loginfailure;
        private String age;
        private String examine;
        private String userType;
        private String email;
        private String belongId;
        private double userLatitude;
        private double userLongitude;
        private String videoUrl;
        private String isOldimg;
        private String messageConstellation;
        private String createName;
        private String updateDate;
        private String createBy;
        private String updateBy;
        private String updateName;
        private String createDate;
        private String roleName;
        private String delFlag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSalt() {
            return salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }

        public String getQualification() {
            return qualification;
        }

        public void setQualification(String qualification) {
            this.qualification = qualification;
        }

        public String getImJurisdiction() {
            return imJurisdiction;
        }

        public void setImJurisdiction(String imJurisdiction) {
            this.imJurisdiction = imJurisdiction;
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

        public String getSevenStartTime() {
            return sevenStartTime;
        }

        public void setSevenStartTime(String sevenStartTime) {
            this.sevenStartTime = sevenStartTime;
        }

        public String getShopId() {
            return shopId;
        }

        public void setShopId(String shopId) {
            this.shopId = shopId;
        }

        public int getQuestionCount() {
            return questionCount;
        }

        public void setQuestionCount(int questionCount) {
            this.questionCount = questionCount;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public int getMessageHight() {
            return messageHight;
        }

        public void setMessageHight(int messageHight) {
            this.messageHight = messageHight;
        }

        public int getMessageWeight() {
            return messageWeight;
        }

        public void setMessageWeight(int messageWeight) {
            this.messageWeight = messageWeight;
        }

        public String getCitizenNo() {
            return citizenNo;
        }

        public void setCitizenNo(String citizenNo) {
            this.citizenNo = citizenNo;
        }

        public String getJobEndTime() {
            return jobEndTime;
        }

        public void setJobEndTime(String jobEndTime) {
            this.jobEndTime = jobEndTime;
        }

        public String getLastcCity() {
            return lastcCity;
        }

        public void setLastcCity(String lastcCity) {
            this.lastcCity = lastcCity;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
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

        public String getLoginfailure() {
            return loginfailure;
        }

        public void setLoginfailure(String loginfailure) {
            this.loginfailure = loginfailure;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getExamine() {
            return examine;
        }

        public void setExamine(String examine) {
            this.examine = examine;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getBelongId() {
            return belongId;
        }

        public void setBelongId(String belongId) {
            this.belongId = belongId;
        }

        public double getUserLatitude() {
            return userLatitude;
        }

        public void setUserLatitude(double userLatitude) {
            this.userLatitude = userLatitude;
        }

        public double getUserLongitude() {
            return userLongitude;
        }

        public void setUserLongitude(double userLongitude) {
            this.userLongitude = userLongitude;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getIsOldimg() {
            return isOldimg;
        }

        public void setIsOldimg(String isOldimg) {
            this.isOldimg = isOldimg;
        }

        public String getMessageConstellation() {
            return messageConstellation;
        }

        public void setMessageConstellation(String messageConstellation) {
            this.messageConstellation = messageConstellation;
        }

        public String getCreateName() {
            return createName;
        }

        public void setCreateName(String createName) {
            this.createName = createName;
        }

        public String getUpdateDate() {
            return updateDate;
        }

        public void setUpdateDate(String updateDate) {
            this.updateDate = updateDate;
        }

        public String getCreateBy() {
            return createBy;
        }

        public void setCreateBy(String createBy) {
            this.createBy = createBy;
        }

        public String getUpdateBy() {
            return updateBy;
        }

        public void setUpdateBy(String updateBy) {
            this.updateBy = updateBy;
        }

        public String getUpdateName() {
            return updateName;
        }

        public void setUpdateName(String updateName) {
            this.updateName = updateName;
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

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
    }

    public static class YoushiBean {
        /**
         * mage : 孝顺
         */

        private String mage;

        public String getMage() {
            return mage;
        }

        public void setMage(String mage) {
            this.mage = mage;
        }
    }
}
