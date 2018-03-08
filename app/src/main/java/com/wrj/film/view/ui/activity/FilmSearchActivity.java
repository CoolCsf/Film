package com.wrj.film.view.ui.activity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.CollectionUtils;
import com.tool.util.DataUtils;
import com.tool.util.ToastHelp;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.ActivityFilmSearchBinding;
import com.wrj.film.databinding.ItemSortRcyBinding;
import com.wrj.film.databinding.LayoutSearchHeadBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.FilmViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2018/3/8.
 */

public class FilmSearchActivity extends AbsActivity<ActivityFilmSearchBinding> {
    private BDRVFastAdapter<FilmViewModel, ItemSortRcyBinding> mAdapter;
    private LayoutSearchHeadBinding headBinding;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_search;
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "电影搜索");
        headBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_search_head, null, false);
        mAdapter = initRcyParam(binding.rvSearch, R.layout.item_sort_rcy, new LinearLayoutManager(this));
        mAdapter.addHeaderView(headBinding.getRoot());
    }

    private <VM, BD extends ViewDataBinding> BDRVFastAdapter<VM, BD> initRcyParam(RecyclerView rcy, int resId, RecyclerView.LayoutManager manager) {
        rcy.setLayoutManager(manager);
        BDRVFastAdapter<VM, BD> adapter = new
                BDRVFastAdapter<>(resId, new ArrayList<VM>());
        rcy.setAdapter(adapter);
        return adapter;
    }

    @Override
    protected void initListener() {
        headBinding.tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataUtils.checkStrNotNull(headBinding.etSearch.getText().toString())) {
                    showLoading();
                    BmobQuery<FilmModel> query = new BmobQuery<>();
                    query.addWhereEqualTo("title", headBinding.etSearch.getText().toString().trim());
                    query.findObjects(new FindListener<FilmModel>() {
                        @Override
                        public void done(List<FilmModel> list, BmobException e) {
                            closeLoading();
                            if (e == null) {
                                if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                                    toRcy(list);
                                } else {
                                    mAdapter.getData().clear();
                                    mAdapter.notifyDataSetChanged();
                                    ToastHelp.showToast("查询不到电影数据");
                                }
                            } else {
                                ToastHelp.showToast(e.getMessage());
                            }
                        }
                    });
                }
            }
        });
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FilmDetailActivity.FILM_INTENT_KEY,
                        ((FilmViewModel) adapter.getData().get(position)).getObjectId());
                startActivity(FilmDetailActivity.class, bundle);
            }
        });
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_buy) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getObjectId());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_TYPE_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getType());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_MONEY_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getMoney());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_FILM_NAME_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getTitle());
                    startActivity(FilmBuyActivity.class, bundle);
                }
            }
        });
    }

    private void toRcy(List<FilmModel> model) {
        if (CollectionUtils.collectionState(model) == CollectionUtils.COLLECTION_UNEMPTY) {
            mAdapter.setNewData(FilmModel.model2viewModel(model));
        } else {
            ToastHelp.showToast("暂无该类别的电影");
        }
    }

    @Override
    protected void initData() {

    }
}
