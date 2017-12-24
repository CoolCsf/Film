package com.tool.util.gallerfinal;



import android.content.Context;
import android.graphics.Color;

import com.tool.util.glide.GlideImageLoader;
import com.tool.util.glide.GlidePauseOnScrollListener;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * GalleryFinal的初始化配置类
 */
public class GalleryFinalInit {
    private static GalleryFinalInit instance;
    private Context context;

    private GalleryFinalInit(Context context) {
        this.context = context;
    }

    public static GalleryFinalInit getInstance(Context context) {
        if (instance == null) {
            synchronized (GalleryFinalInit.class) {
                if (instance == null) {
                    instance = new GalleryFinalInit(context);
                }
            }
        }
        return instance;
    }

    public void init() {
        ThemeConfig themeConfig = null;
        //主题
        ThemeConfig theme = new ThemeConfig.Builder()
                .setTitleBarBgColor(Color.rgb(0xFF, 0xFF, 0xFF))
                .setTitleBarTextColor(Color.BLACK)
                .setTitleBarIconColor(Color.BLACK)
                .setFabNornalColor(Color.RED)
                .setFabPressedColor(Color.BLUE)
                .setCheckNornalColor(Color.WHITE)
                .setCheckSelectedColor(Color.BLACK)
                .build();
        themeConfig = theme;
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
    //使用glide
        imageLoader =  GlideImageLoader.getInstance();
        PauseOnScrollListener pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);
        CoreConfig coreConfig = new CoreConfig.Builder(context, imageLoader, themeConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)//快速滚动停止加载
                .setNoAnimcation(false)//设置加载动画
                .build();
        GalleryFinal.init(coreConfig);
    }
}
