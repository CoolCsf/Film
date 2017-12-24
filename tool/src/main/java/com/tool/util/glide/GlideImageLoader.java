package com.tool.util.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.tool.R;

import cn.finalteam.galleryfinal.widget.GFImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by Administrator on 2016/9/26.
 */
public class GlideImageLoader implements cn.finalteam.galleryfinal.ImageLoader{
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

    @Override
    public void displayImage(Activity activity, String path, final GFImageView imageView, Drawable defaultDrawable, int width, int height) {
        Glide.with(activity)
                .load("file://" + path)
                .placeholder(defaultDrawable)
                .error(defaultDrawable)
                .override(width, height)
                .diskCacheStrategy(DiskCacheStrategy.NONE) //不缓存到SD卡
                .skipMemoryCache(true)
                //.centerCrop()
                .into(new ImageViewTarget<GlideDrawable>(imageView) {
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        imageView.setImageDrawable(resource);
                    }

                    @Override
                    public void setRequest(Request request) {
                        imageView.setTag(R.id.adapter_item_tag_key, request);
                    }

                    @Override
                    public Request getRequest() {
                        return (Request) imageView.getTag(R.id.adapter_item_tag_key);
                    }
                });
    }

    @Override
    public void clearMemoryCache() {

    }
}
