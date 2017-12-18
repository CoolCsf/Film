package com.wrj.film.view.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.tool.util.DataUtils;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityFilmDetailBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.FilmRcyItemViewModel;

import java.util.List;

public class FilmDetailActivity extends BaseActivity<ActivityFilmDetailBinding, FilmRcyItemViewModel> {
    public static final String FILM_INTENT_KEY = "film_intent_key";
    private String filmId;
    private String filmMoney;
    private String filmType;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        filmId = getIntent().getExtras().getString(FILM_INTENT_KEY);
        showLoading();
        FilmModelUtil.getFilmModelFromId(filmId, new FilmModelUtil.FilmModelCallBack() {
            @Override
            public void getModel(List<FilmModel> model) {
                closeLoading();
                FilmModel film = model.get(0);
                viewModel.setDetail(film.getIntroduction());
                viewModel.setTitle(film.getTitle());
                viewModel.setType(film.getType());
                viewModel.setPhotoUrl(film.getPhotoUrl());
                viewModel.setNum(film.getScore());
                viewModel.setDuration(film.getDuration());
                filmMoney = film.getMoney();
                filmType = film.getType();
                binding.setData(viewModel);
            }
        });
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataUtils.checkStrNotNull(filmId)) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_KEY, filmId);
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_MONEY_KEY, filmMoney);
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_TYPE_KEY, filmType);
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_FILM_NAME_KEY, viewModel.getTitle());
                    startActivity(FilmBuyActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "电影详情");
    }

    @Override
    protected FilmRcyItemViewModel getViewModel() {
        return new FilmRcyItemViewModel();
    }
}
