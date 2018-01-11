package com.wrj.film.view.ui.activity;

import android.support.v7.widget.StaggeredGridLayoutManager;

import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.ActivityRootMainBinding;

public class RootMainActivity extends AbsActivity<ActivityRootMainBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_root_main;
    }

    @Override
    protected void initView() {
        super.initView();
        ((CustomTitleBar) binding.titleBar).setTitle("电影列表");
        ((CustomTitleBar) binding.titleBar).setRightTitle("添加");
        binding.rvFilm.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//设置
//        binding.rvFilm.setAdapter(new BDRVFastAdapter());
    }

    @Override
    protected void initData() {

    }
}
