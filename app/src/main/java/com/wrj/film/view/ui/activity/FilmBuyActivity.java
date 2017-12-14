package com.wrj.film.view.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.DateUtils;
import com.tool.util.ToastHelp;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.ActivityFilmBuyBinding;
import com.wrj.film.databinding.ItemFilmBuyRcyBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmTime;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.FilmBuyRcyItemViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/12/10.
 */

public class FilmBuyActivity extends AbsActivity<ActivityFilmBuyBinding> {
    public static final String FILM_BUY_INTENT_KEY = "film_buy_intent_key";
    private BDRVFastAdapter<FilmBuyRcyItemViewModel, ItemFilmBuyRcyBinding> adapter;

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
        adapter = new BDRVFastAdapter<FilmBuyRcyItemViewModel, ItemFilmBuyRcyBinding>(
                R.layout.item_film_buy_rcy, new ArrayList<FilmBuyRcyItemViewModel>(), R.id.btn_buy);
        binding.rvFilmBuy.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(SelectTableActivity.class, null);
            }
        });
    }

    private void initTab() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(System.currentTimeMillis()));
        String[] date = {"今天", "明天", "后天"};
        for (int i = 0; i < 3; i++) {
            binding.tlDate.addTab(binding.tlDate.newTab().setText(
                    date[i] + getNowDate(cal)));
            cal.add(Calendar.DATE, 1);
        }
        binding.tlDate.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText() != null)
                    queryData(tab.getText().toString().split("天")[1]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private String getNowDate(Calendar cal) {
        return cal.get(Calendar.YEAR) +
                "-" + (cal.get(Calendar.MONTH) + 1) +
                "-" + cal.get(Calendar.DAY_OF_MONTH);
    }

    @Override
    protected void initData() {
        queryData(getNowDate(Calendar.getInstance()));
    }

    private void queryData(String date) {
        BmobQuery<FilmModel> query = new BmobQuery<>();
        query.addWhereEqualTo("date", date);
        query.findObjects(new FindListener<FilmModel>() {
            @Override
            public void done(List<FilmModel> list, BmobException e) {
                if (e == null) {
//                    adapter.setNewData(list);
                } else {
                    ToastHelp.showToast("查询失败:" + e.getMessage());
                }
            }
        });
    }
}
