package com.wrj.film.model;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/12/15.
 */

public class FilmTime extends BmobObject {
    private String time;

    public FilmTime(String time) {
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
