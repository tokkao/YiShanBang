package com.benben.yishanbang.ui.mine.bean;

//收藏的商品
public class CollectionGoodsListBean {


    /**
     * price : 0.01
     * imgUrl : upload/user/20190903/49466c677fac40a4a2b67fb93e1c2117_1567492255757.png
     * questionType : 0
     * cardId : yhk100044
     * name : 蔬果奶茶4
     * id : 2c91f41d6d09a4b0016d09c0412a0015
     * number : 123
     */

    private double price;
    private String imgUrl;
    private String questionType;
    private String cardId;
    private String name;
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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
