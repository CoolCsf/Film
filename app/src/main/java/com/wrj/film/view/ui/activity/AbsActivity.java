package com.wrj.film.view.ui.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jaeger.library.StatusBarUtil;
import com.wrj.film.view.widget.DialogHelper;
import com.tool.util.ToastHelp;
import com.wrj.film.AppContext;
import com.wrj.film.R;

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
        setStatusBar();
        beforeInitView();
        initView();
        initListener();
        initData();
    }

    protected void setStatusBar() {
        StatusBarUtil.setColor(this, getResources().getColor(R.color.main_color));
    }

    @Override
    public void showLoading() {
        if (helper == null)
            helper = new DialogHelper();
        helper.showLoading(this);
    }

    @Override
    public void closeLoading() {
        if (helper != null)
            helper.hideLoading();
    }

    @Override
    protected void onStop() {
        super.onStop();
        closeLoading();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppContext.instance.removeActivity(TAG);
    }

    protected void beforeInitView() {

    }

    protected abstract int getLayoutId();

    public void showToast(String content) {
        ToastHelp.showToast(content);
    }

    @Override
    public void startActivity(Class act, Bundle bundle) {
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
