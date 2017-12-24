package com.wrj.film.view.ui.activity;

import android.util.Log;
import android.view.View;

import com.tool.util.CollectionUtils;
import com.wrj.film.LoginUtil;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityLoginBinding;
import com.wrj.film.model.FilmDate;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmTime;
import com.wrj.film.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;


public class LoginActivity extends BaseActivity<ActivityLoginBinding, UserViewModel> {
    private List<FilmTime> timesAll;
    private List<FilmDate> datesAll;

    @Override
    protected void initListener() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                loginToBmob();
            }
        });
        binding.tvRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterAct();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        // TODO  以下方法可以给管理员的插入数据使用
//        showLoading();
//        insertTimes();
//        insertDates();
    }

    private void insertDates() {
        final List<BmobObject> dates = new ArrayList<>();
        dates.add(new FilmDate("2017-12-19"));
        new BmobBatch().insertBatch(dates).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                        datesAll = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            FilmDate date = (FilmDate) dates.get(i);
                            date.setObjectId(list.get(i).getObjectId());
                            datesAll.add(date);
                        }
                        insertFilmModel();
                    }
                } else {
                    closeLoading();
                    Log.e(TAG, e.getMessage());
                    showToast("存储数据失败" + e.getMessage());
                }
            }
        });
    }

    private void insertTimes() {
        final List<BmobObject> times = new ArrayList<>();
        FilmTime time = new FilmTime("13:00");
        List<String> seats = new ArrayList<String>();
        seats.add("07-08");
        seats.add("09-08");
        time.setSelectedSeats(seats);
        times.add(time);
        new BmobBatch().insertBatch(times).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if (e == null) {
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                        timesAll = new ArrayList<>();
                        for (int i = 0; i < list.size(); i++) {
                            FilmTime time = (FilmTime) times.get(i);
                            time.setObjectId(list.get(i).getObjectId());
                            timesAll.add(time);
                        }
                        insertFilmModel();
                    }
                } else {
                    closeLoading();
                    Log.e(TAG, e.getMessage());
                    showToast("存储数据失败" + e.getMessage());
                }
            }
        });
    }

    private void insertFilmModel() {
        if (CollectionUtils.collectionState(timesAll) == CollectionUtils.COLLECTION_UNEMPTY
                && CollectionUtils.collectionState(datesAll) == CollectionUtils.COLLECTION_UNEMPTY) {
            FilmModel filmModel = new FilmModel();
            filmModel.setType("惊悚");
            filmModel.setTitle("孤胆英雄");
            filmModel.setScore("8.0");
            filmModel.setPhotoUrl("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1557179855,74992354&fm=27&gp=0.jpg");
            filmModel.setNowShowing(true);
            filmModel.setMoney("28.9");
            filmModel.setIntroduction("30");
            filmModel.setDuration("120");
            BmobRelation r_dates = new BmobRelation();
            BmobRelation r_times = new BmobRelation();
            for (FilmTime time : timesAll) {
                r_times.add(time);
            }
            for (FilmDate date : datesAll) {
                r_dates.add(date);
            }
            filmModel.setTimes(r_times);
            filmModel.setDates(r_dates);
            filmModel.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    closeLoading();
                    if (e == null) {
                        showToast("保存FilmModel数据成功");
                    } else {
                        Log.e(TAG, e.getMessage());
                        showToast("保存数据失败" + e.getMessage());
                    }
                }
            });
        }
    }

    private void toRegisterAct() {
        startActForResult(RegisteredActivity.class, null, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    private void loginToBmob() {
        LoginUtil.login(viewModel.getUserName(), viewModel.getPwd(), this);
    }


    @Override
    protected void initView() {
        binding.setLogin(viewModel);
    }

    @Override
    protected UserViewModel getViewModel() {
        UserViewModel model = new UserViewModel();
        binding.setLogin(model);
        return model;
    }
}
