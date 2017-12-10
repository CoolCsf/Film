package com.wrj.film.view.ui.fragment;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.ToastHelp;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentRankingBinding;
import com.wrj.film.databinding.ItemSortHeadRcyBinding;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.view.ui.activity.FilmBuyActivity;
import com.wrj.film.view.ui.activity.FilmDetailActivity;
import com.wrj.film.viewmodel.FilmRcyItemViewModel;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/9.
 */

public class RankingFragment extends BaseFragment<FragmentRankingBinding> {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ((CustomTitleBar) binding.titleBar).setTitle("票房排行");
        binding.rvRanking.setLayoutManager(new LinearLayoutManager(getActivity()));
        BDRVFastAdapter<FilmRcyItemViewModel, ItemSortHeadRcyBinding> adapter = new
                BDRVFastAdapter(R.layout.item_sort_rcy, new ArrayList<FilmRcyItemViewModel>(), R.id.btn_buy);
        ViewUtil.rcyAddItemDecoration(binding.rvRanking);
        binding.rvRanking.setAdapter(adapter);
        adapter.setNewData(getSortRcyData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(FilmDetailActivity.class, null);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_buy) {
                    startActivity(FilmBuyActivity.class, null);
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
