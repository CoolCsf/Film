package com.wrj.film.view.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaeger.library.StatusBarUtil;
import com.tool.util.CollectionUtils;
import com.tool.util.ScreenUtils;
import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentMainBinding;
import com.wrj.film.databinding.ItemPlayRcyBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.view.ui.activity.FilmBuyActivity;
import com.wrj.film.view.ui.activity.FilmDetailActivity;
import com.wrj.film.viewmodel.FilmPlayRcyItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MainFragment extends BaseFragment<FragmentMainBinding> {
    private BDRVFastAdapter<FilmPlayRcyItemViewModel, ItemPlayRcyBinding> unShowAdapter;
    private BDRVFastAdapter<FilmPlayRcyItemViewModel, ItemPlayRcyBinding> showAdapter;

    @Override
    protected void initData() {
        showLoading();
        FilmModelUtil.getFilmModelAll(new FilmModelUtil.FilmModelCallBack() {
            @Override
            public void getModel(List<FilmModel> models) {
                closeLoading();
                showFilmRcy(models);
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        setListener(showAdapter);
        setListener(unShowAdapter);
    }

    private void setListener(BDRVFastAdapter adapter) {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FilmDetailActivity.FILM_INTENT_KEY,
                        ((FilmPlayRcyItemViewModel) adapter.getData().get(position)).getFilmId());
                startActivity(FilmDetailActivity.class, bundle);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_buy) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_KEY,
                            ((FilmPlayRcyItemViewModel) adapter.getData().get(position)).getFilmId());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_TYPE_KEY,
                            ((FilmPlayRcyItemViewModel) adapter.getData().get(position)).getType());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_MONEY_KEY,
                            ((FilmPlayRcyItemViewModel) adapter.getData().get(position)).getMoney());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_FILM_NAME_KEY,
                            ((FilmPlayRcyItemViewModel) adapter.getData().get(position)).getFilmName());
                    startActivity(FilmBuyActivity.class, bundle);
                }
            }
        });
    }

    private void showFilmRcy(List<FilmModel> list) {
        if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
            List<FilmPlayRcyItemViewModel> showList = new ArrayList<>();
            List<FilmPlayRcyItemViewModel> unShowList = new ArrayList<>();
            List<String> banners = new ArrayList<>();
            for (FilmModel film : list) {
                FilmPlayRcyItemViewModel model = new FilmPlayRcyItemViewModel();
                model.setPhotoUrl(film.getPhotoUrl());
                model.setFilmId(film.getObjectId());
                model.setMoney(film.getMoney());
                model.setType(film.getType());
                model.setFilmName(film.getTitle());
                if (film.isBanner())
                    banners.add(film.getPhotoUrl());
                if (film.isNowShowing()) {
                    showList.add(model);
                } else {
                    unShowList.add(model);
                }
            }
            initBananer(banners);
            showAdapter.setNewData(showList);
            unShowAdapter.setNewData(unShowList);
        }
    }

    @Override
    protected void initView() {
        super.initView();
        setRcyParam(binding.rvHotPlay);
        setRcyParam(binding.rvToBePlay);
        unShowAdapter = new BDRVFastAdapter<>
                (R.layout.item_play_rcy, new ArrayList<FilmPlayRcyItemViewModel>(), R.id.btn_buy);
        binding.rvToBePlay.setAdapter(unShowAdapter);
        showAdapter = new BDRVFastAdapter<>
                (R.layout.item_play_rcy, new ArrayList<FilmPlayRcyItemViewModel>(), R.id.btn_buy);
        binding.rvHotPlay.setAdapter(showAdapter);
    }

    private void setRcyParam(RecyclerView rcy) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcy.setLayoutManager(manager);
        rcy.setNestedScrollingEnabled(false);
    }

    private void initBananer(List<String> listsImg) {
        binding.convenientBannerRecommend.getLayoutParams().height = ScreenUtils.getScreenHeight(getActivity()) / 3;//高度为屏幕的1/4
        binding.convenientBannerRecommend.getLayoutParams().width = ScreenUtils.getScreenWidth(getActivity());
        binding.convenientBannerRecommend.requestLayout();
        binding.convenientBannerRecommend.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new WebImageHolderView();
            }
        }, listsImg)
                .setPointViewVisible(true)
                //设置指示器的位置
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .startTurning(4000)
                //设置手动影响
                .setManualPageable(true);
    }

    public class WebImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, int position, String data) {
            GlideImageLoader.getInstance().displayImage(context,
                    imageView,
                    data,
                    ContextCompat.getDrawable(AppContext.instance, R.drawable.home_banner_default));
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }
}
