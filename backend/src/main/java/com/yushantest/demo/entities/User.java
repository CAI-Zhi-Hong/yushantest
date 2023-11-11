package com.yushantest.demo.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name="user_id")
    private String userId;
    @Column(name = "account", nullable = false)
    private String account;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "user_name", nullable = false)
    private String userName;

    public User() {}

    public User(String userId, String account, String email , String userName) {
        this.userId = userId;
        this.account = account;
        this.email = email;
        this.userName = userName;

    }

    public User (Object[] row) {
        this.userId = (String) row[0];
        this.account =  (String) row[1];
        this.email =  (String) row[2];
        this.userName = (String) row[3];
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}