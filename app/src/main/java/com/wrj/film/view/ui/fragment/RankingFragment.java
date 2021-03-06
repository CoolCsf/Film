package com.wrj.film.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.tool.util.CollectionUtils;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.AppContext;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentRankingBinding;
import com.wrj.film.databinding.ItemSortHeadRcyBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.model.eventbus.UpdateFilmNumber;
import com.wrj.film.model.eventbus.UpdateOrderEvent;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.view.ui.activity.FilmBuyActivity;
import com.wrj.film.view.ui.activity.FilmDetailActivity;
import com.wrj.film.viewmodel.FilmPlayRcyItemViewModel;
import com.wrj.film.viewmodel.FilmViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Administrator on 2017/12/9.
 */
public class RankingFragment extends BaseFragment<FragmentRankingBinding> {
    private BDRVFastAdapter<FilmViewModel, ItemSortHeadRcyBinding> adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(UpdateFilmNumber message) {
        initData();
    }
    @Override
    protected void initData() {
        BmobQuery<FilmModel> query = new BmobQuery<>();
        query.order("-number");
        FilmModelUtil.getFilmModelParam(new FilmModelUtil.FilmModelCallBack() {
            @Override
            public void getModel(List<FilmModel> model) {
                if (CollectionUtils.collectionState(model) == CollectionUtils.COLLECTION_UNEMPTY) {
                    adapter.setNewData(FilmModel.model2viewModel(model));
                }
            }
        }, query);
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "票房排行");
        binding.rvRanking.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new BDRVFastAdapter(R.layout.item_sort_rcy, new ArrayList<FilmViewModel>(), R.id.btn_buy);
        ViewUtil.rcyAddItemDecoration(binding.rvRanking);
        binding.rvRanking.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FilmDetailActivity.FILM_INTENT_KEY,
                        ((FilmViewModel) adapter.getData().get(position)).getObjectId());
                startActivity(FilmDetailActivity.class, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_buy) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getObjectId());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_MONEY_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getMoney());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_TYPE_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getType());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_FILM_NAME_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getTitle());
                    startActivity(FilmBuyActivity.class, bundle);
                }
            }
        });
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ranking;
    }
}
