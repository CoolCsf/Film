package com.wrj.film.view.ui.fragment;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.tool.util.ScreenUtils;
import com.tool.util.glide.GlideImageLoader;
import com.wrj.film.AppContext;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentMainBinding;
import com.wrj.film.databinding.ItemPlayRcyBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.viewmodel.FilmPlayRcyItemViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MainFragment extends BaseFragment<FragmentMainBinding> {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        setRcyParam(binding.rvHotPlay);
        setRcyParam(binding.rvToBePlay);
        BDRVFastAdapter<FilmPlayRcyItemViewModel, ItemPlayRcyBinding> adapter = new BDRVFastAdapter<>
                (R.layout.item_play_rcy, new ArrayList<FilmPlayRcyItemViewModel>());
        binding.rvToBePlay.setAdapter(adapter);
        BDRVFastAdapter<FilmPlayRcyItemViewModel, ItemPlayRcyBinding> adapter1 = new BDRVFastAdapter<>
                (R.layout.item_play_rcy, new ArrayList<FilmPlayRcyItemViewModel>());
        binding.rvHotPlay.setAdapter(adapter1);
        initBananer();
    }

    private void setRcyParam(RecyclerView rcy) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcy.setLayoutManager(manager);
        rcy.setNestedScrollingEnabled(false);
    }

    private void initBananer() {
        List<String> listsImg = new ArrayList<>();
        listsImg.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=770928468,2499305594&fm=27&gp=0.jpg");
        listsImg.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=770928468,2499305594&fm=27&gp=0.jpg");
        int height = (ScreenUtils.getScreenHeight(getActivity()) - ScreenUtils.getStatusHeight(getActivity())) / 4;//高度为去除状态栏后的屏幕的1/4
        binding.convenientBannerRecommend.getLayoutParams().height = height;
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
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }
}
