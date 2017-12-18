package com.wrj.film.viewmodel;

/**
 * Created by Ervin on 2017/12/18.
 */

public class SelectSeatViewModel {
    private String filmName;
    private String content;
    private String money;

    public String getMoney() {
        return money + "元";
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String date, String time, String type) {
        this.content = date + " " + time + " " + type;
    }
}
