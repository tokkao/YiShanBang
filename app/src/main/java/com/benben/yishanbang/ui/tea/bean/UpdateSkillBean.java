package com.benben.yishanbang.ui.tea.bean;

/**
 * Created by Administrator on 2019/8/16 0016
 * Describe:修改用户技能
 */
public class UpdateSkillBean  {

    /**
     * id : 402881ee6b88a01b016b88a01b550000
     * status : 0
     * userId : 402881ef6bc55fe1016bc67825320003
     * updateName :
     * createName : 管理员
     * createDate : 2019-06-24 16:35:22
     * updateBy :
     * createBy : admin
     * updateDate :
     * skillName : asdas
     * skillGrade : ads
     * skillImgUrl : asdsad
     */
    private boolean isSelected;
    private String id;
    private int status;
    private String userId;
    private String updateName;
    private String createName;
    private String createDate;
    private String updateBy;
    private String createBy;
    private String updateDate;
    private String skillName;
    private String skillGrade;
    private String skillImgUrl;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


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

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
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
}
