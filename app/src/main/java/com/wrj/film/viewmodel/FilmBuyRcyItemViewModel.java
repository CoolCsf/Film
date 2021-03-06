package com.wrj.film.viewmodel;

import com.wrj.film.model.FilmTime;

/**
 * Created by Administrator on 2017/12/10.
 */

public class FilmBuyRcyItemViewModel {
    private String time;
    private String type;
    private String money;
    private FilmTime filmTime;

    public FilmTime getFilmTime() {
        return filmTime;
    }

    public void setFilmTime(FilmTime filmTime) {
        this.filmTime = filmTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money + "元";
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
