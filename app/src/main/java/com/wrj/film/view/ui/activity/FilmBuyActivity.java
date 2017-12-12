package com.wrj.film.view.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.DateUtils;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.ActivityFilmBuyBinding;
import com.wrj.film.databinding.ItemFilmBuyRcyBinding;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.FilmBuyRcyItemViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/12/10.
 */

public class FilmBuyActivity extends AbsActivity<ActivityFilmBuyBinding> {
    public static final String FILM_BUY_INTENT_KEY = "film_buy_intent_key";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_film_buy;
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "选择场次");
        initTab();
        initRcy();
    }

    private void initRcy() {
        binding.rvFilmBuy.setLayoutManager(new LinearLayoutManager(this));
        ViewUtil.rcyAddItemDecoration(binding.rvFilmBuy);
        BDRVFastAdapter adapter = new BDRVFastAdapter<FilmBuyRcyItemViewModel, ItemFilmBuyRcyBinding>(
                R.layout.item_film_buy_rcy, new ArrayList<FilmBuyRcyItemViewModel>());
        binding.rvFilmBuy.setAdapter(adapter);
        adapter.setNewData(getList());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                goActivity(SelectTableActivity.class, null);
            }
        });
    }

    private ArrayList<FilmBuyRcyItemViewModel> getList() {
        ArrayList<FilmBuyRcyItemViewModel> list = new ArrayList<>();
        FilmBuyRcyItemViewModel model = new FilmBuyRcyItemViewModel();
        model.setMoney("28元");
        model.setTime("20:00");
        model.setType("英文3D");
        list.add(model);
        list.add(model);
        return list;
    }

    private void initTab() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String[] date = {"今天", "明天", "后天"};
        for (int i = 0; i < 3; i++) {
            binding.tlDate.addTab(binding.tlDate.newTab().setText(
                    date[i] + DateUtils.DateToString(cal.getTime(), "yyyy-mm-dd")));
            cal.add(Calendar.DATE, 1);
        }
        binding.tlDate.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initData() {

    }
}
