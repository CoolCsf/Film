package com.tool.util.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2016/9/26.
 */
public class GlideImageLoader {
    private static GlideImageLoader instance;

    private GlideImageLoader() {

    }

    //单例
    public static GlideImageLoader getInstance() {
        if (instance == null) {
            synchronized (GlideImageLoader.class) {
                if (instance == null) {
                    instance = new GlideImageLoader();
                }
            }
        }
        return instance;
    }

    public void displayCircleImage(Context context, ImageView view, String path, Drawable defaultDrawable, int width, int height) {
        Glide.with(context)
                .load(path)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true).into(view);
    }

    public void displayCircleImage(Context context, ImageView view, String path, Drawable defaultDrawable) {
        Glide.with(context)
                .load(path)
                .bitmapTransform(new CropCircleTransformation(context))
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true).into(view);
    }

    public void displayImage(Context context, ImageView view, String path, Drawable defaultDrawable) {
        Glide.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true).into(view);
    }

    public void displayImage(Context context, ImageView view, String path, Drawable defaultDrawable, int width, int height) {
        Glide.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true).into(view);
    }

    public void displayLocalImage(Context context, ImageView view, int path, Drawable defaultDrawable) {
        Glide.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true).into(view);
    }

    public void displayLocalImage(Context context, ImageView view, int path, Drawable defaultDrawable, int width, int height) {
        Glide.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .skipMemoryCache(true).into(view);
    }

    //高斯模糊效果
    public void displayBlurImage(Context context, ImageView view, String path, Drawable defaultDrawable, int width, int height) {
        Glide.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .bitmapTransform(new BlurTransformation(context, 100))
                .override(width, height)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true).into(view);
    }

    //高斯模糊效果
    public void displayCropCircleImage(Context context, ImageView view, String path, Drawable defaultDrawable, int width, int height) {
        Glide.with(context)
                .load(path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .bitmapTransform(new RoundedCornersTransformation(context, 20, 0))
                .override(width, height)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(true).into(view);
    }
}
