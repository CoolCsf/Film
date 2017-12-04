package com.tool.util;

import android.app.Dialog;
import android.content.Context;

import com.tool.R;

/**
 * Created by Administrator on 2017/12/1.
 */

public class DialogHelper {
    private Dialog dialog;

    public void showLoading(Context context) {
        if (dialog != null && dialog.isShowing())
            return;
        if (dialog == null) {
            dialog = new Dialog(context);
            dialog.setContentView(R.layout.layout_loading);
        }
        dialog.show();
    }

    public void hideLoading() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }

    }

}
