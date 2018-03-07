package com.wrj.film;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.tool.util.ToastHelp;
import com.tool.util.gallerfinal.GalleryFinalInit;

import java.util.Stack;

import cn.bmob.v3.Bmob;

/**
 * Created by Administrator on 2017/11/10.
 */

public class AppContext extends Application {
    private Stack<Activity> activities = new Stack<>();
    public static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        ToastHelp.init(this);
        GalleryFinalInit.getInstance(instance).init();//初始化图片选择器
        //第一：默认初始化
        Bmob.initialize(this, "dae581d33da7c783f488caf9628dfe8c");
    }

    //完全关闭app
    public void exitApp() {
        finishAll();
//        System.exit(0);
    }

    //清楚栈中所有的activity
    private void finishAll() {
        if (activities != null && !activities.isEmpty()) {
            for (Activity activity : activities) {
                activity.finish();
            }
            activities.clear();
        }
    }

    public void removeActivity(String strActivity) {
        for (Activity activity : activities) {
            String name = activity.getClass().getSimpleName();
            if (name.equals(strActivity)) {
                Log.i("application", "removeActivity:" + strActivity);
                activities.remove(activity);
                break;
            }
        }
    }

    public void addActivities(Activity activity) {
        activities.push(activity);
    }
}
