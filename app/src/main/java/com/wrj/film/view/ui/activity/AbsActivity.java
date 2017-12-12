package com.wrj.film.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tool.util.DialogHelper;
import com.tool.util.ToastHelp;
import com.wrj.film.AppContext;

/**
 * Created by Administrator on 2017/11/13.
 */

public abstract class AbsActivity<BD extends ViewDataBinding> extends AppCompatActivity implements IBaseActivity {
    protected BD binding;
    protected String TAG = this.getClass().getSimpleName();
    private DialogHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppContext.instance.addActivities(this);
        binding = DataBindingUtil.setContentView(this, getLayoutId());
        beforeInitView();
        initView();
        initListener();
        initData();
    }

    public void showLoading() {
        if (helper == null)
            helper = new DialogHelper();
        helper.showLoading(this);
    }

    public void closeLoading() {
        helper.hideLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeLoading();
        AppContext.instance.removeActivity(TAG);
    }

    protected void beforeInitView() {

    }

    protected abstract int getLayoutId();

    public void showToast(String content) {
        ToastHelp.showToast(content);
    }

    @Override
    public void goActivity(Class act, Bundle bundle) {
        Intent intent = new Intent(this, act);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void startActForResult(Class actClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, actClass);
        if (bundle != null)
            intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    protected void initListener() {

    }

    protected void initView() {

    }

    protected abstract void initData();
}
