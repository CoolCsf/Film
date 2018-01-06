package com.wrj.film.model;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;
import com.wrj.film.viewmodel.FilmViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobRelation;

/**
 * Created by Administrator on 2017/12/11.
 * 电影的属性类，对应bmob的film表
 * 包含电影的所有相关属性
 */

public class FilmModel extends BmobObject {
    private String title;
    private String type;
    private String money;
    private Integer number = 0;
    private String photoUrl;
    private String introduction;
    private String duration;
    private boolean isNowShowing;//是否热映
    private BmobRelation dates;
    private BmobRelation times;
    private boolean isBanner;

    public boolean isBanner() {
        return isBanner;
    }

    public void setBanner(boolean banner) {
        isBanner = banner;
    }

    public BmobRelation getTimes() {
        return times;
    }

    public void setTimes(BmobRelation times) {
        this.times = times;
    }

    public boolean isNowShowing() {
        return isNowShowing;
    }

    public BmobRelation getDates() {
        return dates;
    }

    public void setDates(BmobRelation dates) {
        this.dates = dates;
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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
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

    public static List<FilmViewModel> model2viewModel(List<FilmModel> models) {
        List<FilmViewModel> filmViewModels = new ArrayList<>();
        for (FilmModel model : models) {
            FilmViewModel viewModel = new FilmViewModel();
            viewModel.setDuration(model.getDuration());
            viewModel.setIntroduction(model.getIntroduction());
            viewModel.setMoney(model.getMoney());
            viewModel.setNowShowing(model.isNowShowing());
            viewModel.setPhotoUrl(model.getPhotoUrl());
            viewModel.setNumber(model.getNumber() == 0 ? "0" : String.valueOf(model.getNumber()));
            viewModel.setTitle(model.getTitle());
            viewModel.setType(model.getType());
            viewModel.setObjectId(model.getObjectId());
            filmViewModels.add(viewModel);
        }
        return filmViewModels;
    }
}
