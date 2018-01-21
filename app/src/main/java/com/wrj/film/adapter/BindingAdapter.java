package com.wrj.film.adapter;

import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;

/**
 * Created by Administrator on 2018/1/21.
 */

public class BindingAdapter {
    @android.databinding.BindingAdapter("image")
    public static void image(ImageView view, String imageId) {
        GlideImageLoader.getInstance().displayImage(AppContext.instance, view,
                imageId, ContextCompat.getDrawable(AppContext.instance, R.drawable.error_default),
                180, 170);
    }
}
