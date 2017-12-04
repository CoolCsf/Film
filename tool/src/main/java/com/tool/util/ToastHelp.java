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

    public static void init(Application con) {
        content = con;
    }

    public static void showToast(String text) {
        Toast.makeText(content, text, Toast.LENGTH_SHORT).show();
    }
}
