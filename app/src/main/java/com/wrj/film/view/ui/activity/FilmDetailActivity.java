package com.wrj.film.view.ui.activity;

import android.support.v4.content.ContextCompat;

import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityFilmDetailBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.FilmRcyItemViewModel;

import java.util.List;

public class FilmDetailActivity extends BaseActivity<ActivityFilmDetailBinding, FilmRcyItemViewModel> {
    public static final String FILM_INTENT_KEY = "film_intent_key";

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        String id = getIntent().getExtras().getString(FILM_INTENT_KEY);
        showLoading();
        FilmModelUtil.getFilmModelFromId(id, new FilmModelUtil.FilmModelCallBack() {
            @Override
            public void getModel(List<FilmModel> model) {
                closeLoading();
                FilmModel film = model.get(0);
                viewModel.setDetail(film.getIntroduction());
                viewModel.setTitle(film.getTitle());
                viewModel.setType(film.getType());
                viewModel.setTime(film.getTime());
                viewModel.setPhotoUrl(film.getPhotoUrl());
                viewModel.setNum(film.getScore());
                binding.setData(viewModel);
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
