package com.yushantest.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "like list")
public class LikeList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sn;

    @Column(nullable = false)
    private int orderName;

    @Column(nullable = false)
    private String account;

    @Column(nullable = false)
    private int totalFee;

    @Column(nullable = false)
    private int totalAmount;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "no", nullable = false)
    private Product product;

    public LikeList() {}

    public LikeList(int orderName, String account, int totalFee, int totalAmount, User user, Product product) {
        this.orderName = orderName;
        this.account = account;
        this.totalFee = totalFee;
        this.totalAmount = totalAmount;
        this.user = user;
        this.product = product;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public int getOrderName() {
        return orderName;
    }

    public void setOrderName(int orderName) {
        this.orderName = orderName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}