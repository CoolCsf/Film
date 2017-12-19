package com.tool.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/11/10.
 */

public class ToastHelp {
    @SuppressLint("StaticFieldLeak")
    private static Application content;
    private static long time = 0;

    public static void init(Application con) {
        content = con;
    }

    public static void showToast(String text) {
        if (time == 0 || System.currentTimeMillis() - time >= 1000 * 2) {//2秒内只能弹一次
            Toast.makeText(content, text, Toast.LENGTH_SHORT).show();
            time = System.currentTimeMillis();
        }
    }
}
