package com.example.wedless.myintentservice.bean;

/**
 * Created by Wedless on 2017/11/17.
 */

public class User {
    private Integer id;
    private String account;
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
