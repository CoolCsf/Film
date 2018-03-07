package com.wrj.film.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import com.wrj.film.BR;
/**
 * Created by Administrator on 2017/12/9.
 */

public class SimpleStringViewModel extends BaseObservable {
    private String text;
    private int index;
    private boolean isCheck = false;

    @Bindable
    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        notifyPropertyChanged(BR.check);
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
