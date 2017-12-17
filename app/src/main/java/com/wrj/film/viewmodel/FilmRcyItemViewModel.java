package com.wrj.film.viewmodel;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;

/**
 * Created by Administrator on 2017/12/9.
 */

public class FilmRcyItemViewModel {
    private String photoUrl;
    private String title;
    private String type;
    private String num;
    private String detail;
    private String duration;

    public String getDuration() {
        return duration + "分钟";
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    @BindingAdapter("imageBig")
    public static void imageBig(ImageView view, String imageId) {
        GlideImageLoader.getInstance().displayImage(AppContext.instance, view,
                imageId, ContextCompat.getDrawable(AppContext.instance, R.drawable.error_default_big),
                270, 255);
    }

    @BindingAdapter("imageBlur")
    public static void imageBlur(ImageView view, String imageId) {
        GlideImageLoader.getInstance().displayBlurImage(AppContext.instance, view,
                imageId, ContextCompat.getDrawable(AppContext.instance, R.drawable.home_banner_default),
                720, 300);
    }
}
