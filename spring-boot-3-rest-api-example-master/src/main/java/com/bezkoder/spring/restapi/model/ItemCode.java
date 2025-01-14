package com.bezkoder.spring.restapi.model;

public class ItemCode {
    private String singleCode;

    private String productName;

    private int qty;
    private String expireDay;

    public String getSingleCode() {
        return singleCode;
    }

    public void setSingleCode(String singleCode) {
        this.singleCode = singleCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(String expireDay) {
        this.expireDay = expireDay;
    }

    public ItemCode(String singleCode, String productName, int qty, String expireDay) {
        this.singleCode = singleCode;
        this.productName = productName;
        this.qty = qty;
        this.expireDay = expireDay;
    }
}
