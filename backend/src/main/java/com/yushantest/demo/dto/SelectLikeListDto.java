package com.yushantest.demo.dto;

public class SelectLikeListDto {
    private Integer sn;    
    private String account;
    private Integer orderName;
    private Integer totalAmount;
    private Integer totalFee;
    private Integer no;
    private Double feeRate;
    private Integer price;
    private String productName;

    public Integer getSn() {
        return sn;
    }
    public void setSn(int sn) {
        this.sn = sn;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public Integer getOrderName() {
        return orderName;
    }
    public void setOrderName(Integer orderName) {
        this.orderName = orderName;
    }
    public Integer getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Integer getTotalFee() {
        return totalFee;
    }
    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }
    public Integer getNo() {
        return no;
    }
    public void setNo(Integer no) {
        this.no = no;
    }
    public Double getFeeRate() {
        return feeRate;
    }
    public void setFeeRate(Double feeRate) {
        this.feeRate = feeRate;
    }
    public Integer getPrice() {
        return price;
    }
    public void setPrice(Integer price) {
        this.price = price;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
 
    public SelectLikeListDto(){}
    public SelectLikeListDto(
        Integer sn,    
        String account,
        Integer orderName,
        Integer totalAmount,
        Integer totalFee,
        Integer no,
        Double feeRate,
        Integer price,
        String productName
    ){
        this.sn = sn;
        this.account = account;
        this.orderName = orderName;
        this.totalAmount = totalAmount;
        this.totalFee = totalFee;
        this.no = no;
        this.feeRate = feeRate;
        this.price = price;
        this.productName = productName;
        
    }

    public SelectLikeListDto(
        Object[] row
    ){
        this.sn = (Integer) row[0]; 
        this.account = (String) row[1]; 
        this.orderName = (Integer) row[2]; 
        this.totalAmount = (Integer) row[3]; 
        this.totalFee =(Integer) row[4]; 
        this.no = (Integer) row[5]; 
        this.feeRate = (Double) row[6]; 
        this.price = (Integer) row[7]; 
        this.productName =(String) row[8]; 
    }
}
