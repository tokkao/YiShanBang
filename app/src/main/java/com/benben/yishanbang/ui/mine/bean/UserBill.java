package com.benben.yishanbang.ui.mine.bean;

/**
 * @author Jay
 * @email 1136189725@qq.com  有事来邮!!!
 * @date 2019/9/25 0025
 */
public class UserBill {
    /*     "id": "1",
                    "user_id": "402881ef6bc55fe1016bc67825320003",
                    "card_id": "",
                    "before_money": 100,
                    "change_money": 50,
                    "after_mpney": 50,
                    "sign": "-",
                    "bill_type": "2",
                    "update_name": "",
                    "update_date": "",
                    "update_by": "",
                    "create_name": "",
                    "create_date": "2019-09-24 01:09:45", */
    private String id;
    private String before_money;
    private String change_money;
    private String after_mpney;
    private String sign;
    private String bill_type;
    private String create_date;
    private String create_name;
    private String update_by;
    private String update_date;
    private String update_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBefore_money() {
        return before_money;
    }

    public void setBefore_money(String before_money) {
        this.before_money = before_money;
    }

    public String getChange_money() {
        return change_money;
    }

    public void setChange_money(String change_money) {
        this.change_money = change_money;
    }

    public String getAfter_mpney() {
        return after_mpney;
    }

    public void setAfter_mpney(String after_mpney) {
        this.after_mpney = after_mpney;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBill_type() {
        return bill_type;
    }

    public void setBill_type(String bill_type) {
        this.bill_type = bill_type;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getCreate_name() {
        return create_name;
    }

    public void setCreate_name(String create_name) {
        this.create_name = create_name;
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

    public String getUpdate_name() {
        return update_name;
    }

    public void setUpdate_name(String update_name) {
        this.update_name = update_name;
    }
}
