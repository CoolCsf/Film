package com.wrj.film.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/12/15.
 */

public class FilmDate extends BmobObject {
    private String date;

    public FilmDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
