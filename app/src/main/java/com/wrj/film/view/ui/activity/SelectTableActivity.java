package com.wrj.film.view.ui.activity;

import com.qfdqc.views.seattable.SeatTable;
import com.tool.util.ToastHelp;
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

    }
}
