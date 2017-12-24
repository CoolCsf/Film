package com.wrj.film.view.ui.activity;

import com.wrj.film.R;
import com.wrj.film.databinding.ActivityOrderDetailFinishBinding;
import com.wrj.film.model.OrderModel;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.OrderDetailViewModel;
import com.wrj.film.viewmodel.UserViewModel;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/12/19.
 */

public class OrderDetailActivity extends BaseActivity<ActivityOrderDetailFinishBinding, OrderDetailViewModel> {
    public final static String ORDER_KEY = "order_key";

    @Override
    protected OrderDetailViewModel getViewModel() {
        return new OrderDetailViewModel();
    }

    @Override
    protected void initView() {
        super.initView();
        OrderModel model = (OrderModel) getIntent().getExtras().getSerializable(ORDER_KEY);
        ViewUtil.initTitleBar(binding.titleBar, model.getOrderStatus());
    }

    @Override
    protected void initData() {
        super.initData();
        OrderModel model = (OrderModel) getIntent().getExtras().getSerializable(ORDER_KEY);
        if (model != null) {
            viewModel.setCinema(model.getCinema());
            viewModel.setFilmName(model.getFilmName());
            viewModel.setSeats(model.getSeats());
            viewModel.setMoney((Float.valueOf(model.getMoney()) * model.getSeats().size() + ""));
            UserViewModel user = BmobUser.getCurrentUser(UserViewModel.class);
            viewModel.setPhone(user.getPhone());
            viewModel.setTime(model.getDate() + " " + model.getTime());
        }
        binding.setData(viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail_finish;
    }
}
