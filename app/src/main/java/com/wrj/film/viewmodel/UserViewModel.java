package com.wrj.film.viewmodel;


import android.databinding.Bindable;
import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.text.Editable;
import android.text.TextWatcher;

import com.tool.util.DataUtils;
import com.wrj.film.BR;
import com.wrj.film.view.widget.TextWatcherListener;

import java.util.Objects;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/11/12.
 */

public class UserViewModel extends BmobUser implements Observable {
    private transient PropertyChangeRegistry mCallbacks;
    private String userName;
    private String pwd;
    private Boolean isRoot = false;
    private String balance;
    private String phone;
    public boolean isCanLogin = false;

    @Bindable
    public boolean isCanLogin() {
        return isCanLogin;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBalance() {
        return (DataUtils.checkStrNotNull(balance) ? balance : "0") + "元";
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

    public TextWatcher userNameWatcher = new TextWatcherListener() {
        @Override
        public void afterTextChanged(Editable s) {
            checkCanLogin();
        }
    };

    private void checkCanLogin() {
        if (DataUtils.checkStrNotNull(userName) && DataUtils.checkStrNotNull(pwd)) {
            isCanLogin = true;
        } else {
            isCanLogin = false;
        }
        notifyPropertyChanged(BR.canLogin);
    }

    public TextWatcher pwdWatcher = new TextWatcherListener() {
        @Override
        public void afterTextChanged(Editable s) {
            checkCanLogin();
        }
    };

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                mCallbacks = new PropertyChangeRegistry();
            }
        }
        mCallbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.remove(callback);
    }

    public void notifyPropertyChanged(int fieldId) {
        synchronized (this) {
            if (mCallbacks == null) {
                return;
            }
        }
        mCallbacks.notifyCallbacks(this, fieldId, null);
    }
}
