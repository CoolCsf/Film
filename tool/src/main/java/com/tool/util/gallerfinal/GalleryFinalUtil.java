package com.tool.util.gallerfinal;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.tool.util.glide.GlideImageLoader;
import com.tool.util.glide.GlidePauseOnScrollListener;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ImageLoader;
import cn.finalteam.galleryfinal.PauseOnScrollListener;
import cn.finalteam.galleryfinal.ThemeConfig;

/**
 * 图库工具控制，控制图库，配置的图片个数，以及是否裁剪等
 *
 */
public class GalleryFinalUtil {

    private static final String TAG = "GalleryFinalUtil";
    public static final int REQUEST_CODE_GALLERY = 11;
    public static final int REQUEST_CODE_CAMERA = 22;

    private Context mContext;
    private GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback;

    public GalleryFinalUtil(Context mContext, GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback) {
        this.mContext = mContext;
        this.mOnHanlderResultCallback = mOnHanlderResultCallback;
    }

    public void camera(boolean isCrop) {
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        functionConfigBuilder.setMutiSelectMaxSize(1);
        //强制裁剪
        functionConfigBuilder.setEnableCrop(isCrop);
        functionConfigBuilder.setCropSquare(isCrop);
        functionConfigBuilder.setForceCrop(isCrop);
        functionConfigBuilder.setEnableEdit(true);//编辑
        functionConfigBuilder.setEnableRotate(true);//旋转
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        //打开图库
        GalleryFinal.openCamera(REQUEST_CODE_CAMERA, functionConfig, mOnHanlderResultCallback);

    }

    public void gallerMulti(int maxSize) {
        if (maxSize <= 0) {
            Log.e(TAG, "" + "不能是0个");
            return;
        }
        boolean muti = true;
        boolean isCrop = true;
        //公共配置都可以在application中配置，这里只是为了代码演示而写在此处
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
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        PauseOnScrollListener pauseOnScrollListener = null;

        //使用glide
        imageLoader = (ImageLoader) GlideImageLoader.getInstance();
        pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);
        boolean mutiSelect = muti;

        if (mutiSelect) {
            functionConfigBuilder.setMutiSelectMaxSize(maxSize);
            // 关闭裁剪
            functionConfigBuilder.setEnableCrop(false);
        } else {
            functionConfigBuilder.setMutiSelectMaxSize(1);
            functionConfigBuilder.setEnableCrop(isCrop);
            //强制裁剪
            functionConfigBuilder.setEnableCrop(isCrop);
            functionConfigBuilder.setCropSquare(isCrop);
            functionConfigBuilder.setForceCrop(isCrop);
        }
        functionConfigBuilder.setEnableEdit(true);
        functionConfigBuilder.setEnableRotate(true);
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)//快速滚动停止加载
                .setNoAnimcation(false)//设置加载动画
                .build();
        GalleryFinal.init(coreConfig);
        //打开图库
        if (mutiSelect) {
            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        } else {
            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        }
    }

    /**
     * 单图片选择，是否裁剪
     *
     * @param isCrop
     */
    public void gallerySingle(boolean isCrop) {
        int maxSize = 1;
        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        boolean mutiSelect = false;
        if (mutiSelect) {
            functionConfigBuilder.setMutiSelectMaxSize(maxSize);
            // 关闭裁剪
            functionConfigBuilder.setEnableCrop(false);
        } else {
            functionConfigBuilder.setMutiSelectMaxSize(1);
            functionConfigBuilder.setEnableCrop(isCrop);
            //强制裁剪
            functionConfigBuilder.setEnableCrop(isCrop);
            functionConfigBuilder.setCropSquare(isCrop);
            functionConfigBuilder.setForceCrop(isCrop);
        }
        functionConfigBuilder.setEnableEdit(true);//编辑
        functionConfigBuilder.setEnableRotate(true);//旋转
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        //打开图库
        if (mutiSelect) {
            GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        } else {
            GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
        }
    }

    public void customCropFromGallery(int width, int height) {
        //公共配置都可以在application中配置，这里只是为了代码演示而写在此处
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


        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        PauseOnScrollListener pauseOnScrollListener = null;

        //使用glide
        imageLoader = (ImageLoader) GlideImageLoader.getInstance();
        pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);


        functionConfigBuilder.setMutiSelectMaxSize(1);
        //强制裁剪
        functionConfigBuilder.setEnableCrop(true);
        functionConfigBuilder.setCropSquare(false);//是否方
        functionConfigBuilder.setForceCrop(true);
        functionConfigBuilder.setEnableEdit(true);//编辑
        functionConfigBuilder.setEnableRotate(true);//旋转
        functionConfigBuilder.setCropWidth(width);//设置裁剪的宽
        functionConfigBuilder.setCropHeight(height);//设置裁剪的高度

//        functionConfigBuilder.setCropReplaceSource(true);//裁剪后覆盖原图

//        functionConfigBuilder.setEnableCamera(true);//是否显示拍照图标
//        functionConfigBuilder.setEnablePreview(true);
//        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)//快速滚动停止加载
                .setNoAnimcation(false)//设置加载动画
                .build();
        GalleryFinal.init(coreConfig);
//        PhotoSelectActivity.showCheckbox = false;
//        initImageLoader(mContext);
        GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
    }

    public void customCropFromCamera(int width, int height) {
        //公共配置都可以在application中配置，这里只是为了代码演示而写在此处
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


        FunctionConfig.Builder functionConfigBuilder = new FunctionConfig.Builder();
        cn.finalteam.galleryfinal.ImageLoader imageLoader;
        PauseOnScrollListener pauseOnScrollListener = null;

        //使用glide
        imageLoader =GlideImageLoader.getInstance();
        pauseOnScrollListener = new GlidePauseOnScrollListener(false, true);
//        imageLoader = new UILImageLoader();
//        pauseOnScrollListener = new UILPauseOnScrollListener(false, true);


        functionConfigBuilder.setMutiSelectMaxSize(1);
        //强制裁剪
        functionConfigBuilder.setEnableCrop(true);
        functionConfigBuilder.setCropSquare(false);//是否方
        functionConfigBuilder.setForceCrop(true);
        functionConfigBuilder.setEnableEdit(true);//编辑
        functionConfigBuilder.setEnableRotate(true);//旋转
        functionConfigBuilder.setCropWidth(width);//设置裁剪的宽
        functionConfigBuilder.setCropHeight(height);//设置裁剪的高度

//        functionConfigBuilder.setCropReplaceSource(true);//裁剪后覆盖原图

//        functionConfigBuilder.setEnableCamera(true);//是否显示拍照图标
//        functionConfigBuilder.setEnablePreview(true);
//        functionConfigBuilder.setSelected(mPhotoList);//添加过滤集合
        final FunctionConfig functionConfig = functionConfigBuilder.build();
        CoreConfig coreConfig = new CoreConfig.Builder(mContext, imageLoader, themeConfig)
                .setFunctionConfig(functionConfig)
                .setPauseOnScrollListener(pauseOnScrollListener)//快速滚动停止加载
                .setNoAnimcation(false)//设置加载动画
                .build();
        GalleryFinal.init(coreConfig);
//        initImageLoader(mContext);
        GalleryFinal.openCamera(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
    }

}
