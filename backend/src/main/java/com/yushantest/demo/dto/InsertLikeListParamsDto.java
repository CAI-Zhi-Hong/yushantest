package com.yushantest.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InsertLikeListParamsDto  {
    @NotNull(message = "uid may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String uid;    
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }

    @NotNull(message = "productName may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String productName;
    
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @NotNull(message = "productPrice may not be null")
    @Min(value = 0, message = "productPrice Can't Be negative")    
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT)
    private Integer productPrice;
    
    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    @NotNull(message = "productFeeRate may not be null")
    @Min(value = 0, message = "productFeeRate Can't Be negative")    
    @Max(value = 1, message = "productFeeRate Can't bigger than 1")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_FLOAT) 
    private Double productFeeRate;
    
    public Double getProductFeeRate() {
        return productFeeRate;
    }

    public void setProductFeeRate(Double productFeeRate) {
        this.productFeeRate = productFeeRate;
    }

    @NotNull(message = "defaultAccount may not be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING) 
    private String defaultAccount;

    public String getDefaultAccount() {
        return defaultAccount;
    }

    public void setDefaultAccount(String defaultAccount) {
        this.defaultAccount = defaultAccount;
    }

    @NotNull(message = "orderName may not be null")
    @Min(value = 0, message = "orderName Can't Be negative")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER_INT) 
    private Integer orderName;

    public Integer getOrderName() {
        return orderName;
    }

    public void setOrderName(Integer orderName) {
        this.orderName = orderName;
    }
    
}
