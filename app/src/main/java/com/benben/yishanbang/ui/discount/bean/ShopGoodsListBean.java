package com.benben.yishanbang.ui.discount.bean;

public class ShopGoodsListBean {

    /**
     * price : 30.23
     * imgUrl : upload/img/photosucai/20190812/325717-130GG9515094_1565582765730.jpg
     * questionType : 1
     * isCollection : 0
     * name : 草莓奶昔
     * message : 红透的草莓配合奶茶口味绝美
     * id : yhk10001
     * number : 1
     */

    private double price;
    private String imgUrl;
    private String questionType;
    private String isCollection;
    private String name;
    private String message;
    private String id;
    private int number;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(String isCollection) {
        this.isCollection = isCollection;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
