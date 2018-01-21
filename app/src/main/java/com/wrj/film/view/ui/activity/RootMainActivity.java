package com.wrj.film.view.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.CollectionUtils;
import com.tool.util.ToastHelp;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.AppContext;
import com.wrj.film.BR;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.ActivityRootMainBinding;
import com.wrj.film.databinding.ItemSortRcyBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.view.widget.DialogHelper;
import com.wrj.film.viewmodel.FilmViewModel;
import com.wrj.film.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class RootMainActivity extends AbsActivity<ActivityRootMainBinding> {
    private BDRVFastAdapter<FilmViewModel, ItemSortRcyBinding> mAdapter;
    private long time = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_root_main;
    }

    @Override
    protected void initView() {
        super.initView();
        ((CustomTitleBar) binding.titleBar).setTitle("电影列表");
        ((CustomTitleBar) binding.titleBar).setRightTitle("添加");
        binding.rvFilm.setLayoutManager(new LinearLayoutManager(this));//设置
        mAdapter = new BDRVFastAdapter(R.layout.item_sort_rcy, new ArrayList<FilmViewModel>());
        mAdapter.setVariable(BR.user, BmobUser.getCurrentUser(UserViewModel.class));
        mAdapter.setStaggeredViewId(R.id.iv_film);
        binding.rvFilm.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        binding.titleBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RootAddFilmActivity.class, null);
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(RootUpdateFilmActivity.KEY_FILM_ID, mAdapter.getData().get(position).getObjectId());
                startActivity(RootUpdateFilmActivity.class, bundle);
            }
        });
        mAdapter.setOnItemLongClickListener(new BaseQuickAdapter.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(BaseQuickAdapter adapter, View view, final int position) {
                new DialogHelper().showContentDialog(RootMainActivity.this, "", "确定下架此电影?", new DialogHelper.InputDialogCallBack() {
                    @Override
                    public void positive(String content) {
                        deleteFilm(mAdapter.getData().get(position).getObjectId(), position);
                    }

                    @Override
                    public void negative() {

                    }
                });
                return true;
            }
        });
    }

    private void deleteFilm(String objectId, final int position) {
        showLoading();
        FilmModel model = new FilmModel();
        model.setObjectId(objectId);
        model.delete(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                closeLoading();
                if (e == null) {
                    mAdapter.getData().remove(position);
                    mAdapter.notifyDataSetChanged();
                    ToastHelp.showToast("下架成功");
                } else {
                    ToastHelp.showToast(e.getMessage());
                }
            }
        });
    }

    @Override
    protected void initData() {
        showLoading();
        BmobQuery<FilmModel> query = new BmobQuery<>();
        query.order("-number");
        FilmModelUtil.getFilmModelParam(new FilmModelUtil.FilmModelCallBack() {
            @Override
            public void getModel(List<FilmModel> model) {
                closeLoading();
                if (CollectionUtils.collectionState(model) == CollectionUtils.COLLECTION_UNEMPTY) {
                    mAdapter.setNewData(FilmModel.model2viewModel(model));
                } else {
                    ToastHelp.showToast("暂无数据");
                }
            }
        }, query);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - time > 2000) {
                ToastHelp.showToast("再按一次退出");
                time = System.currentTimeMillis();
            } else {
                AppContext.instance.exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
