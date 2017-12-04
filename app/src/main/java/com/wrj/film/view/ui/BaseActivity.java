package com.wrj.film.view.ui;

import android.databinding.ViewDataBinding;

/**
 * Created by Administrator on 2017/11/10.
 */

public abstract class BaseActivity<BD extends ViewDataBinding, VM> extends AbsActivity<BD> {
    protected VM viewModel;

    @Override
    protected void initData() {
        viewModel = getViewModel();
    }

    protected abstract VM getViewModel();


}
