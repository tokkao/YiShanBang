package com.benben.yishanbang.ui.tea.bean;

import java.io.Serializable;
import java.util.List;

//用户主页信息
public class UserDetailsInfoBean implements Serializable {


    /**
     * skills : [{"id":"2c91f41d6d5d17c0016d5d421ae70008","status":1,"userId":"402881ef6bc55fe1016bc67825320003","skillName":"吃饭","skillGrade":"1","skillImgUrl":"231","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":""},{"id":"2c91f41d6d5d17c0016d5d42f3940009","status":1,"userId":"402881ef6bc55fe1016bc67825320003","skillName":"吃饭","skillGrade":"1","skillImgUrl":"213435465567657868","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":""},{"id":"2c91f41d6d5d17c0016d5d46396a000c","status":1,"userId":"402881ef6bc55fe1016bc67825320003","skillName":"牛掰技能","skillGrade":"4","skillImgUrl":"upload/user/20190923/6838588950864d45a58902a488ea88e2_1569227224961.png","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":""},{"id":"402881ee6b88a01b016b88a01b550000","status":1,"userId":"402881ef6bc55fe1016bc67825320003","skillName":"asdas","skillGrade":"ads","skillImgUrl":"asdsad","createName":"管理员","updateDate":"","createBy":"admin","updateBy":"","updateName":"","createDate":"2019-06-24 16:35:22"}]
     * userEntity : {"id":"402881ef6bc55fe1016bc67825320003","password":"463408fdf59bb9d9","status":"","username":"","salt":"","qualification":"","imJurisdiction":"1","advantageTitle":"我太帅了","advantageMessage":"就是帅的不太明显","sevenStartTime":"1970-01-01 12:00","shopId":"cysc001","questionCount":23,"mobile":"18503840250","realname":"1255","nickname":"帅哥","avatar":"upload/user/20190905/15c859520bf24fa8b045e6c5da8d5eb6_1567666244182.jpg","sex":"男","messageHight":160,"messageWeight":49,"citizenNo":"410223198904027577","jobEndTime":"1970-01-01 12:00","lastcCity":"郑州","balance":"0","advantageShow":"","roleId":"","voiceUrl":"","jobStartTimer":"1970-01-01 12:00","sixStartTime":"1970-01-01 12:00","sixSendTime":"1970-01-01 12:00","sevenEndTime":"1970-01-01 12:00","birthday":"","loginfailure":"","age":"","examine":"","userType":"5","email":"","belongId":"","userLatitude":4351.23123,"userLongitude":123.342,"videoUrl":"1561651","isOldimg":"upload/user/20190902/e86591423dd54573b1ec3f4b964df166_1567426573154.png","messageConstellation":"双鱼座","createName":"","updateDate":"2019-09-11 18:02:36","createBy":"","updateBy":"admin","updateName":"管理员","createDate":"","roleName":"","delFlag":""}
     * distance : .0
     * advantageLabelEnties : [{"id":"4","mage":"真善美","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":""},{"id":"3","mage":"素颜","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":""}]
     * serveType : [{"price":"23","serveTypeEntity":{"name":"阿达","message":"","id":"2","type":"","url":"","parentId":"402881ee6b884e4e016b884e4ebd0000","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":""}},{"price":"432","serveTypeEntity":{"name":"新版","message":"","id":"402881ee6b884e4e016b884e4ebd0000","type":"","url":"","parentId":"","createName":"管理员","updateDate":"","createBy":"admin","updateBy":"","updateName":"123","createDate":"2019-06-24 15:06:01"}}]
     * dynamic : [{"message":"1","id":"2c91f41d6d4d8653016d4dd529d30006","type":"0","time":"2019-09-20 16:41:19","video":"","imgUrl":"1","userId":"402881ef6bc55fe1016bc67825320003","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":"","delFlag":"1"},{"message":"上传多张","id":"2c91f41d6d3e2405016d3e3e1e310008","type":"0","time":"2019-09-17 16:02:01","video":"","imgUrl":"upload/user/20190917/d3dc2f4ede744f88a9a38497704e38b7_1568707321203.png,upload/user/20190917/d1e6e0e0936c4d47937c065269ef5814_1568707321203.png,upload/user/20190917/ae43fee90b8d42ef946605638a0ccb9d_1568707321204.png","userId":"402881ef6bc55fe1016bc67825320003","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":"","delFlag":"1"},{"message":"上传单张","id":"2c91f41d6d3e2405016d3e3da1b50007","type":"0","time":"2019-09-17 16:01:29","video":"","imgUrl":"upload/user/20190917/0da69cef39314e7cb256970bfe7a00ed_1568707289328.png","userId":"402881ef6bc55fe1016bc67825320003","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":"","delFlag":"1"},{"message":"发布","id":"2c91f41d6d3e2405016d3e2acb6e0002","type":"1","time":"2019-09-17 15:40:55","video":"http://yishanbang.oss-cn-beijing.aliyuncs.com/C69B89C6-6880-43F4-88E3-23098C7564B2.mp4","imgUrl":"","userId":"402881ef6bc55fe1016bc67825320003","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":"","delFlag":"1"}]
     */

    private UserEntityBean userEntity;
    private String distance;
    private List<SkillsBean> skills;
    private List<AdvantageLabelEntiesBean> advantageLabelEnties;
    private List<ServeTypeBean> serveType;
    private List<DynamicBean> dynamic;

    public UserEntityBean getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntityBean userEntity) {
        this.userEntity = userEntity;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<SkillsBean> getSkills() {
        return skills;
    }

    public void setSkills(List<SkillsBean> skills) {
        this.skills = skills;
    }

    public List<AdvantageLabelEntiesBean> getAdvantageLabelEnties() {
        return advantageLabelEnties;
    }

    public void setAdvantageLabelEnties(List<AdvantageLabelEntiesBean> advantageLabelEnties) {
        this.advantageLabelEnties = advantageLabelEnties;
    }

    public List<ServeTypeBean> getServeType() {
        return serveType;
    }

    public void setServeType(List<ServeTypeBean> serveType) {
        this.serveType = serveType;
    }

    public List<DynamicBean> getDynamic() {
        return dynamic;
    }

    public void setDynamic(List<DynamicBean> dynamic) {
        this.dynamic = dynamic;
    }

    public static class UserEntityBean implements Serializable{
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

    public static class SkillsBean implements Serializable{
        /**
         * id : 2c91f41d6d5d17c0016d5d421ae70008
         * status : 1
         * userId : 402881ef6bc55fe1016bc67825320003
         * skillName : 吃饭
         * skillGrade : 1
         * skillImgUrl : 231
         * createName :
         * updateDate :
         * createBy :
         * updateBy :
         * updateName :
         * createDate :
         */

        private String id;
        private int status;
        private String userId;
        private String skillName;
        private String skillGrade;
        private String skillImgUrl;
        private String createName;
        private String updateDate;
        private String createBy;
        private String updateBy;
        private String updateName;
        private String createDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getSkillName() {
            return skillName;
        }

        public void setSkillName(String skillName) {
            this.skillName = skillName;
        }

        public String getSkillGrade() {
            return skillGrade;
        }

        public void setSkillGrade(String skillGrade) {
            this.skillGrade = skillGrade;
        }

        public String getSkillImgUrl() {
            return skillImgUrl;
        }

        public void setSkillImgUrl(String skillImgUrl) {
            this.skillImgUrl = skillImgUrl;
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
    }

    public static class AdvantageLabelEntiesBean implements Serializable{
        /**
         * id : 4
         * mage : 真善美
         * createName :
         * updateDate :
         * createBy :
         * updateBy :
         * updateName :
         * createDate :
         */

        private String id;
        private String mage;
        private String createName;
        private String updateDate;
        private String createBy;
        private String updateBy;
        private String updateName;
        private String createDate;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMage() {
            return mage;
        }

        public void setMage(String mage) {
            this.mage = mage;
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
    }

    public static class ServeTypeBean implements Serializable{
        /**
         * price : 23
         * serveTypeEntity : {"name":"阿达","message":"","id":"2","type":"","url":"","parentId":"402881ee6b884e4e016b884e4ebd0000","createName":"","updateDate":"","createBy":"","updateBy":"","updateName":"","createDate":""}
         */

        private String price;
        private ServeTypeEntityBean serveTypeEntity;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public ServeTypeEntityBean getServeTypeEntity() {
            return serveTypeEntity;
        }

        public void setServeTypeEntity(ServeTypeEntityBean serveTypeEntity) {
            this.serveTypeEntity = serveTypeEntity;
        }

        public static class ServeTypeEntityBean implements Serializable{
            /**
             * name : 阿达
             * message :
             * id : 2
             * type :
             * url :
             * parentId : 402881ee6b884e4e016b884e4ebd0000
             * createName :
             * updateDate :
             * createBy :
             * updateBy :
             * updateName :
             * createDate :
             */

            private String name;
            private String message;
            private String id;
            private String type;
            private String url;
            private String parentId;
            private String createName;
            private String updateDate;
            private String createBy;
            private String updateBy;
            private String updateName;
            private String createDate;

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

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getParentId() {
                return parentId;
            }

            public void setParentId(String parentId) {
                this.parentId = parentId;
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
        }
    }

    public static class DynamicBean implements Serializable{
        /**
         * message : 1
         * id : 2c91f41d6d4d8653016d4dd529d30006
         * type : 0
         * time : 2019-09-20 16:41:19
         * video :
         * imgUrl : 1
         * userId : 402881ef6bc55fe1016bc67825320003
         * createName :
         * updateDate :
         * createBy :
         * updateBy :
         * updateName :
         * createDate :
         * delFlag : 1
         */

        private String message;
        private String id;
        private String type;
        private String time;
        private String video;
        private String imgUrl;
        private String userId;
        private String createName;
        private String updateDate;
        private String createBy;
        private String updateBy;
        private String updateName;
        private String createDate;
        private String delFlag;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

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

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getVideo() {
            return video;
        }

        public void setVideo(String video) {
            this.video = video;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
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

        public String getDelFlag() {
            return delFlag;
        }

        public void setDelFlag(String delFlag) {
            this.delFlag = delFlag;
        }
    }
}
