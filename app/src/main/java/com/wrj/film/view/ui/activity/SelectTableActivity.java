package com.wrj.film.view.ui.activity;

import android.util.Log;
import android.view.View;

import com.qfdqc.views.seattable.SeatTable;
import com.tool.util.CollectionUtils;
import com.tool.util.DataUtils;
import com.tool.util.DialogHelper;
import com.tool.util.ToastHelp;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivitySelectTableBinding;
import com.wrj.film.model.FilmTime;
import com.wrj.film.model.OrderModel;
import com.wrj.film.model.OrderTypeEnum;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.SelectSeatViewModel;
import com.wrj.film.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SelectTableActivity extends BaseActivity<ActivitySelectTableBinding, SelectSeatViewModel> {
    public static final String FILM_SELECT_SEAT_INTENT_FILM_NAME_KEY = "film_select_seat_intent_film_name_key";
    public static final String FILM_SELECT_SEAT_INTENT_DATE_KEY = "film_select_seat_intent_date_key";
    public static final String FILM_SELECT_SEAT_INTENT_TIME_KEY = "film_select_seat_intent_time_key";
    public static final String FILM_SELECT_SEAT_INTENT_TYPE_KEY = "film_select_seat_intent_type_key";
    public static final String FILM_SELECT_SEAT_INTENT_MONEY_KEY = "film_select_seat_intent_money_key";
    private String filmName;
    private FilmTime filmTime;
    private String filmDate;
    private String filmType;
    private String filmMoney;
    private List<String> checks;
    private List<String> checked = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_table;
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "请选择场次");
        binding.seatView.setScreenName("超级豪华厅荧幕");//设置屏幕名称
        binding.seatView.setMaxSelected(3);//设置最多选中
        binding.seatView.setData(10, 15);
    }

    @Override
    protected void initListener() {
        binding.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1..查询是否有未完成的订单且时间在15分钟之内
                //2..如果没有，则进入购买，插入订单
                toBuy();
            }
        });
    }

    private void toBuy() {
        if (DataUtils.checkStrNotNull(BmobUser.getCurrentUser(UserViewModel.class).getPhone())) {
            UserViewModel user = BmobUser.getCurrentUser(UserViewModel.class);
            final int balance = Integer.valueOf(user.getBalance().replace("元", "").trim());
            if (balance < Float.valueOf(filmMoney)) {
                showToast("账户余额不足");
                return;
            }
            new DialogHelper().showContentDialog(this, "", "确认购票？", new DialogHelper.InputDialogCallBack() {
                @Override
                public void positive(String content) {
                    getOrderModel(OrderTypeEnum.getState(1)).save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                showToast("购票成功，请前往已完成订单查看");
                                updateFilmTime();
                                updateUserBalance(String.valueOf(Float.valueOf(filmMoney) - balance));
                                finish();
                            } else {
                                showToast("购票失败" + e.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void negative() {
                    getOrderModel(OrderTypeEnum.getState(2)).save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null) {
                                showToast("生成待支付订单成功，请前往待支付订单查看");
                                updateFilmTime();
                                updateUserBalance(String.valueOf(Float.valueOf(filmMoney) - balance));
                                finish();
                            } else {
                                showToast("生成待支付订单失败" + e.getMessage());
                            }
                        }
                    });
                }
            });
        } else {
            showToast("请先前往个人主页完善手机号码");
        }
    }

    private void updateUserBalance(String balance) {
        UserViewModel user = BmobUser.getCurrentUser(UserViewModel.class);
        user.setBalance(balance);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    Log.e(TAG, "更新余额失败" + e.getMessage());
                } else {
                    Log.e(TAG, "更新余额成功");
                }
            }
        });
    }

    private void updateFilmTime() {
        FilmTime time = new FilmTime(filmTime.getTime());
        checks.addAll(checked);
        time.setSelectedSeats(checks);
        time.setObjectId(filmTime.getObjectId());
        time.setSelectedSeats(checks);
        time.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e != null) {
                    Log.e(TAG, "更新场次座位失败" + e.getMessage());
                    showToast("更新场次座位失败" + e.getMessage());
                } else {
                    Log.e(TAG, "更新场次座位成功");
                }
            }
        });
    }

    private OrderModel getOrderModel(String status) {
        OrderModel model = new OrderModel();
        model.setCinema("广州大学华软软件学院礼堂一");
        model.setDate(filmDate);
        model.setFilmName(filmName);
        model.setMoney(filmMoney);
        model.setSeats(checked);
        model.setTime(filmTime.getTime());
        model.setUser(BmobUser.getCurrentUser(UserViewModel.class));
        model.setOrderStatus(status);
        return model;
    }

    @Override
    protected void initData() {
        super.initData();
        filmName = getIntent().getExtras().getString(FILM_SELECT_SEAT_INTENT_FILM_NAME_KEY);
        filmTime = (FilmTime) getIntent().getExtras().getSerializable(FILM_SELECT_SEAT_INTENT_TIME_KEY);
        filmDate = getIntent().getExtras().getString(FILM_SELECT_SEAT_INTENT_DATE_KEY);
        filmType = getIntent().getExtras().getString(FILM_SELECT_SEAT_INTENT_TYPE_KEY);
        filmMoney = getIntent().getExtras().getString(FILM_SELECT_SEAT_INTENT_MONEY_KEY);
        viewModel.setContent(filmDate, filmTime.getTime(), filmType);
        viewModel.setFilmName(filmName);
        viewModel.setMoney(filmMoney);
        binding.setData(viewModel);
        checks = CollectionUtils.collectionState(filmTime.getSelectedSeats())
                == CollectionUtils.COLLECTION_UNEMPTY ? filmTime.getSelectedSeats() : new ArrayList<String>();
        binding.seatView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                return column != 2;
            }

            @Override
            public boolean isSold(int row, int column) {
                for (String seat : checks) {
                    String[] s = seat.split("-");
                    if (row == Integer.valueOf(s[0]) - 1 && column == Integer.valueOf(s[1]) - 1) {
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                checked.add(row + "-" + column);
                ToastHelp.showToast("选择了第" + row + "排 ,第" + column + "座");
            }

            @Override
            public void unCheck(int row, int column) {
                if (checked.contains(row + "-" + column)) {
                    checked.remove(row + "-" + column);
                }
                ToastHelp.showToast("取消选择了第" + row + "排 ,第" + column + "座");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
    }

    @Override
    protected SelectSeatViewModel getViewModel() {
        return new SelectSeatViewModel();
    }
}
