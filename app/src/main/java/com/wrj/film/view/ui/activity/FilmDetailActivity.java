package com.wrj.film.view.ui.activity;

import android.support.v4.content.ContextCompat;

import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityFilmDetailBinding;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.FilmRcyItemViewModel;

public class FilmDetailActivity extends BaseActivity<ActivityFilmDetailBinding, FilmRcyItemViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_detail;
    }

    @Override
    protected void initData() {
        super.initData();
        ViewUtil.initTitleBar(binding.titleBar, "电影详情");
        binding.setData(viewModel);
        GlideImageLoader.getInstance().displayBlurImage(this,
                binding.ivBig,
                viewModel.getPhotoUrl(),
                ContextCompat.getDrawable(AppContext.instance, R.drawable.home_banner_default)
                , 720, 300);
        GlideImageLoader.getInstance().displayImage(this,
                binding.ivSmall,
                viewModel.getPhotoUrl(),
                ContextCompat.getDrawable(AppContext.instance, R.drawable.error_default_big)
                , 270, 255);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected FilmRcyItemViewModel getViewModel() {
        FilmRcyItemViewModel model = new FilmRcyItemViewModel();
        model.setNum("50");
        model.setPhotoUrl("http://img3.imgtn.bdimg.com/it/u=3214507998,4284254551&fm=27&gp=0.jpg");
        model.setTime("2016-20-20");
        model.setType("惊悚");
        model.setTitle("不知道什么电影");
        model.setDetail("不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电" +
                "影不知道什么电影不知道什么电影不知道什么电影不知道什么电影不知道什么电影");
        return model;
    }
}
