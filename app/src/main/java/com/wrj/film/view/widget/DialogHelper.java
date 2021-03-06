package com.wrj.film.view.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;

import com.tool.util.DataUtils;
import com.wrj.film.R;

import java.util.List;

/**
 * Created by Administrator on 2017/12/1.
 */

public class DialogHelper {
    private Dialog dialog;

    public void showLoading(Context context) {
        if (dialog != null && dialog.isShowing())
            return;
        if (dialog == null) {
            dialog = new Dialog(context, R.style.customDialog);
            dialog.setContentView(R.layout.layout_loading);
            dialog.setCanceledOnTouchOutside(false);
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

    public void showContentDialog(Context context, String title, String message, final InputDialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (DataUtils.checkStrNotNull(title))
            builder.setTitle(title);
        builder.setMessage(message)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.positive("");
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.negative();
                    }
                })
                .show();
    }

    public void showListDialog(Context context, String title, String checkedItem, final List<String> messages, final InputDialogCallBack callBack) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (DataUtils.checkStrNotNull(title))
            builder.setTitle(title);
        final String[] messageId = new String[messages.size()];
        int checkedItemPosition = 0;
        for (int i = 0; i < messages.size(); i++) {
            messageId[i] = messages.get(i);
            if (DataUtils.checkStrNotNull(checkedItem) && checkedItem.equals(messages.get(i))) {
                checkedItemPosition = i;
            }
        }
        builder.setSingleChoiceItems(messageId, checkedItemPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.positive(messageId[which]);
                dialog.dismiss();
            }
        }).show();
    }

    public void showInputDialog(Context context, String title, final InputDialogCallBack callBack) {
        showInputDialog(context, title, new EditText(context), callBack);
    }

    private void showInputDialog(Context context, String title, final EditText editText, final InputDialogCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(editText)
                .setTitle(title)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (DataUtils.checkStrNotNull(editText.getText().toString().trim()))
                            callBack.positive(editText.getText().toString().trim());
                        else
                            dialog.dismiss();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        callBack.negative();
                    }
                })
                .show();
    }

    public void showInputNumDialog(Context context, String title, final InputDialogCallBack callBack) {
        EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        showInputDialog(context, title, editText, callBack);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
        editText.setSingleLine();
    }

    public void showInputPhoneDialog(Context context, String title, final InputDialogCallBack callBack) {
        EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        showInputDialog(context, title, editText, callBack);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
        editText.setSingleLine();
    }

    public interface InputDialogCallBack {
        void positive(String content);

        void negative();
    }
}
