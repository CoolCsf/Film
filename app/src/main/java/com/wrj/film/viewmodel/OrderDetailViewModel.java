package com.wrj.film.viewmodel;

import java.util.List;

/**
 * Created by Administrator on 2017/12/19.
 */

public class OrderDetailViewModel {
    private String filmName;
    private String cinema;
    private String time;
    private String seats;
    private String phone;
    private String money;

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeats() {
        return seats;
    }

    public void setSeats(List<String> seats) {
        StringBuilder seat = new StringBuilder();
        for (String s : seats) {
            String[] s1 = s.split("-");
            seat.append(s1[0]).append("排").append(s1[1]).append("座").append("  ");
        }
        this.seats = seat.toString();
    }

    public String getPhone() {
        return "手机号码：" + phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMoney() {
        return "￥ " + money + "元";
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
