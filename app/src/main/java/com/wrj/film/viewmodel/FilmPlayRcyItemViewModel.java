package com.wrj.film.viewmodel;

import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;

/**
 * Created by Administrator on 2017/12/7.
 */

public class FilmPlayRcyItemViewModel {
    private String photoUrl;
    private String filmId;
    private String type;
    private String money;
    private String filmName;

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
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

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    @BindingAdapter("image")
    public static void image(ImageView view, String imageId) {
        GlideImageLoader.getInstance().displayImage(AppContext.instance, view,
                imageId, ContextCompat.getDrawable(AppContext.instance, R.drawable.error_default),
                180, 170);
    }
}
