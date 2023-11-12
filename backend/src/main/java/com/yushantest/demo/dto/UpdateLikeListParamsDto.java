package com.yushantest.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class UpdateLikeListParamsDto  {
    @NotNull(message = "uid may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String uid;    
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @NotNull(message = "likeListSN may not be null")
    @Min(value = 0, message = "likeListSN Can't Be negative")    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer likeListSN;
    
    public Integer getLikeListSN() {
        return likeListSN;
    }

    public void setLikeListSN(Integer likeListSN) {
        this.likeListSN = likeListSN;
    }

    @NotNull(message = "account may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String account;

    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }

    @NotNull(message = "orderName may not be null")
    @Min(value = 0, message = "orderName Can't Be negative")    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer orderName;
    public Integer getOrderName() {
        return orderName;
    }
    public void setOrderName(Integer orderName) {
        this.orderName = orderName;
    }

    @NotNull(message = "feeRate may not be null")
    @Min(value = 0, message = "feeRate Can't Be negative")    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT)
    private Double feeRate;
    public Double getFeeRate() {
        return feeRate;
    }
    public void setFeeRate(Double feeRate) {
        this.feeRate = feeRate;
    }

    @NotNull(message = "price may not be null")
    @Min(value = 0, message = "price Can't Be negative")    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer price;
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    @NotNull(message = "productName may not be null")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private String productName;
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
}   

