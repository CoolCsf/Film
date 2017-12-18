package com.wrj.film.view.ui.activity;

import com.qfdqc.views.seattable.SeatTable;
import com.tool.util.ToastHelp;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivitySelectTableBinding;
import com.wrj.film.model.FilmTime;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.SelectSeatViewModel;

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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_table;
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "请选择场次");
        binding.seatView.setScreenName("8号厅荧幕");//设置屏幕名称
        binding.seatView.setMaxSelected(3);//设置最多选中

        binding.seatView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                return column != 2;
            }

            @Override
            public boolean isSold(int row, int column) {
                return row == 6 && column == 6;
            }

            @Override
            public void checked(int row, int column) {
                ToastHelp.showToast("选择了第" + row + "排 ,第" + column + "座");
            }

            @Override
            public void unCheck(int row, int column) {
                ToastHelp.showToast("取消选择了第" + row + "排 ,第" + column + "座");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        binding.seatView.setData(10, 15);
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
    }

    @Override
    protected SelectSeatViewModel getViewModel() {
        return new SelectSeatViewModel();
    }
}
