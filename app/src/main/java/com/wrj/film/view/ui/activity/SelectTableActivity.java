package com.wrj.film.view.ui.activity;

import com.qfdqc.views.seattable.SeatTable;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivitySelectTableBinding;

public class SelectTableActivity extends AbsActivity<ActivitySelectTableBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select_table;
    }

    @Override
    protected void initView() {
        binding.seatView.setScreenName("8号厅荧幕");//设置屏幕名称
        binding.seatView.setMaxSelected(3);//设置最多选中

        binding.seatView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if(column==2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if(row==6&&column==6){
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {

            }

            @Override
            public void unCheck(int row, int column) {

            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        binding.seatView.setData(10,15);
    }

    @Override
    protected void initData() {

    }
}
