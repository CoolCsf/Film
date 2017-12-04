package com.wrj.film.viewmodel;


import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/11/12.
 */

public class UserViewModel extends BmobUser {
    public String userName;
    public String pwd;

    public Boolean isRoot() {
        return isRoot;
    }

    public void setRoot(Boolean root) {
        isRoot = root;
    }

    public Boolean isRoot;
    public String name;

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
