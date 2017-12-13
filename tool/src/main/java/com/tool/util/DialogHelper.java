package com.tool.util;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.EditText;

import com.tool.R;

/**
 * Created by Administrator on 2017/12/1.
 */

public class DialogHelper {
    private AlertDialog mDialog;

    public void showLoading(Context context) {
        if (mDialog != null && mDialog.isShowing())
            return;
        if (mDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setView(R.layout.layout_loading);
            mDialog = builder.create();
        }
        mDialog.show();
    }

    public void hideLoading() {
        if (mDialog != null) {
            if (mDialog.isShowing())
                mDialog.dismiss();
            mDialog = null;
        }

    }

    public void showInputDialog(Context context, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(new EditText(context))
                .setPositiveButton("确定", listener)
                .setNegativeButton("取消", null)
                .show();
    }
}
