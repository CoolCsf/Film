package com.wrj.film.viewmodel;


import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/11/12.
 */

public class UserViewModel extends BmobUser {
    private String userName;
    private String pwd;
    private Boolean isRoot;
    private String balance;

    public String getBalance() {
        return (balance.equals("") ? "0" : balance) + "元";
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public Boolean isRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        setUsername(userName);
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
        setPassword(pwd);
    }
}
