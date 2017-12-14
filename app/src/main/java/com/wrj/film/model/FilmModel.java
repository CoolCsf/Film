package com.wrj.film.model;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by Administrator on 2017/12/11.
 * 电影的属性类，对应bmob的film表
 * 包含电影的所有相关属性
 */

public class FilmModel extends BmobObject {
    private String title;
    private String date;
    private String time;
    private String type;
    private String money;
    private String score;
    private String photoUrl;
    private String introduction;
    private String duration;
    private boolean isNowShowing;//是否热映
    private FilmTime times;

    public FilmTime getTimes() {
        return times;
    }

    public void setTimes(FilmTime times) {
        this.times = times;
    }

    public boolean isNowShowing() {
        return isNowShowing;
    }

    public void setNowShowing(boolean isNowShowing) {
        this.isNowShowing = isNowShowing;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @BindingAdapter("imgFromFilmModel")
    public static void imgFromFilmModel(ImageView view, String imageId) {
        GlideImageLoader.getInstance().displayImage(AppContext.instance, view,
                imageId, ContextCompat.getDrawable(AppContext.instance, R.drawable.error_default_big),
                270, 255);
    }
}
