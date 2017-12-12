package com.wrj.film.view.ui.activity;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/11/18.
 */

public interface IBaseActivity {
    void goActivity(Class act, Bundle bundle);

    void startActForResult(Class actClass, Bundle bundle, int requestCode);

    void showLoading();

    void closeLoading();
}
