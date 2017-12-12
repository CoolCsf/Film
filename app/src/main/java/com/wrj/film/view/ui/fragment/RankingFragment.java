package com.wrj.film.view.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.CollectionUtils;
import com.tool.util.ToastHelp;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentRankingBinding;
import com.wrj.film.databinding.ItemSortHeadRcyBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.view.ui.activity.FilmBuyActivity;
import com.wrj.film.view.ui.activity.FilmDetailActivity;
import com.wrj.film.viewmodel.FilmRcyItemViewModel;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Administrator on 2017/12/9.
 */

public class RankingFragment extends BaseFragment<FragmentRankingBinding> {
    private BDRVFastAdapter<FilmModel, ItemSortHeadRcyBinding> adapter;

    @Override
    protected void initData() {
        BmobQuery<FilmModel> query = new BmobQuery<>();
        query.order("score");
        FilmModelUtil.getFilmModelParam(new FilmModelUtil.FilmModelCallBack() {
            @Override
            public void getModel(List<FilmModel> model) {
                if (CollectionUtils.collectionState(model) == CollectionUtils.COLLECTION_UNEMPTY) {
                    adapter.setNewData(model);
                }
            }
        }, query);
    }

    @Override
    protected void initView() {
        ((CustomTitleBar) binding.titleBar).setTitle("票房排行");
        binding.rvRanking.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BDRVFastAdapter(R.layout.item_sort_rcy, new ArrayList<FilmModel>(), R.id.btn_buy);
        ViewUtil.rcyAddItemDecoration(binding.rvRanking);
        binding.rvRanking.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FilmDetailActivity.FILM_INTENT_KEY,
                        ((FilmModel) adapter.getData().get(position)).getObjectId());
                startActivity(FilmDetailActivity.class, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_buy) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_KEY,
                            ((FilmModel) adapter.getData().get(position)).getObjectId());
                    startActivity(FilmBuyActivity.class, bundle);
                }
            }
        });
    }

    @NonNull
    private ArrayList<FilmRcyItemViewModel> getSortRcyData() {
        ArrayList<FilmRcyItemViewModel> list = new ArrayList<>();
        FilmRcyItemViewModel model = new FilmRcyItemViewModel();
        model.setNum("8.3");
        model.setPhotoUrl("sssss");
        model.setTime("2016-20-50");
        model.setTitle("标题哦");
        model.setType("悬疑片");
        list.add(model);
        list.add(model);
        return list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }
}
