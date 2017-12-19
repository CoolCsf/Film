package com.wrj.film.view.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.CollectionUtils;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.ActivityFilmBuyBinding;
import com.wrj.film.databinding.ItemFilmBuyRcyBinding;
import com.wrj.film.model.FilmDate;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmTime;
import com.wrj.film.model.eventbus.UpdateOrderEvent;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.FilmBuyRcyItemViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/12/10.
 */

public class FilmBuyActivity extends AbsActivity<ActivityFilmBuyBinding> {
    public static final String FILM_BUY_INTENT_KEY = "film_buy_intent_key";
    public static final String FILM_BUY_INTENT_TYPE_KEY = "film_buy_intent_type_key";
    public static final String FILM_BUY_INTENT_MONEY_KEY = "film_buy_intent_money_key";
    public static final String FILM_BUY_INTENT_FILM_NAME_KEY = "film_buy_intent_film_name_key";
    private BDRVFastAdapter<FilmBuyRcyItemViewModel, ItemFilmBuyRcyBinding> mAdapter;
    private String filmId;
    private String filmType;
    private String filmName;
    private String filmMoney;
    private String date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(UpdateOrderEvent message) {
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

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
        mAdapter = new BDRVFastAdapter<>(
                R.layout.item_film_buy_rcy, new ArrayList<FilmBuyRcyItemViewModel>(), R.id.btn_buy);
        binding.rvFilmBuy.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(SelectTableActivity.FILM_SELECT_SEAT_INTENT_TIME_KEY, mAdapter.getData().get(position).getFilmTime());
                bundle.putString(SelectTableActivity.FILM_SELECT_SEAT_INTENT_DATE_KEY, date);
                bundle.putString(SelectTableActivity.FILM_SELECT_SEAT_INTENT_TYPE_KEY, filmType);
                bundle.putString(SelectTableActivity.FILM_SELECT_SEAT_INTENT_FILM_NAME_KEY, filmName);
                bundle.putString(SelectTableActivity.FILM_SELECT_SEAT_INTENT_MONEY_KEY, filmMoney);
                startActivity(SelectTableActivity.class, bundle);
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
                if (tab.getText() != null) {
                    mAdapter.setNewData(new ArrayList<FilmBuyRcyItemViewModel>());
                    queryData(tab.getText().toString().split("天")[1]);
                }
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
        filmId = getIntent().getExtras().getString(FILM_BUY_INTENT_KEY);
        filmMoney = getIntent().getExtras().getString(FILM_BUY_INTENT_MONEY_KEY);
        filmType = getIntent().getExtras().getString(FILM_BUY_INTENT_TYPE_KEY);
        filmName = getIntent().getExtras().getString(FILM_BUY_INTENT_FILM_NAME_KEY);
        queryData(getNowDate(Calendar.getInstance()));
    }

    private void queryData(final String date) {
        this.date = date;
        showLoading();
        BmobQuery<FilmDate> query = new BmobQuery<>();
        final FilmModel filmModel = new FilmModel();
        filmModel.setObjectId(filmId);
        query.addWhereRelatedTo("dates", new BmobPointer(filmModel));
        query.findObjects(new FindListener<FilmDate>() {
            @Override
            public void done(List<FilmDate> list, BmobException e) {
                if (e == null) {
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                        for (FilmDate d : list) {
                            if (d.getDate().equals(date)) {
                                queryTimes(filmModel);
                                return;
                            }
                        }
                        showToast("该时间无场次");
                    } else {
                        showToast("该时间无场次");
                    }
                    closeLoading();
                } else {
                    closeLoading();
                    showToast("查询错误" + e.getMessage());
                }
            }
        });
    }

    private void queryTimes(FilmModel filmModel) {
        BmobQuery<FilmTime> timeQuery = new BmobQuery<>();
        timeQuery.addWhereRelatedTo("times", new BmobPointer(filmModel));
        timeQuery.findObjects(new FindListener<FilmTime>() {
            @Override
            public void done(List<FilmTime> list, BmobException e) {
                closeLoading();
                if (e == null) {
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                        List<FilmBuyRcyItemViewModel> datas = new ArrayList<>();
                        for (FilmTime time : list) {
                            FilmBuyRcyItemViewModel viewModel = new FilmBuyRcyItemViewModel();
                            viewModel.setTime(time.getTime());
                            viewModel.setMoney(filmMoney);
                            viewModel.setType(filmType);
                            viewModel.setFilmTime(time);
                            datas.add(viewModel);
                        }
                        mAdapter.setNewData(datas);
                    } else {
                        showToast("该时间无场次");
                    }
                } else {
                    showToast("查询失败" + e.getMessage());
                }
            }
        });
    }
}
