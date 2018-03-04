package com.wrj.film.view.ui.activity;

import com.tool.util.CollectionUtils;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityOrderDetailFinishBinding;
import com.wrj.film.model.OrderModel;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.OrderDetailViewModel;
import com.wrj.film.viewmodel.UserViewModel;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

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
    }

    @Override
    protected void initData() {
        super.initData();
        String orderId = getIntent().getExtras().getString(ORDER_KEY);
        showLoading();
        BmobQuery<OrderModel> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", orderId);
        query.include("model");
        query.findObjects(new FindListener<OrderModel>() {

            @Override
            public void done(List<OrderModel> list, BmobException e) {
                closeLoading();
                if (e == null) {
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY && list.get(0) != null) {
                        OrderModel model = list.get(0);
                        ViewUtil.initTitleBar(binding.titleBar, model.getOrderStatus());
                        viewModel.setCinema(model.getCinema());
                        viewModel.setFilmName(model.getFilmName());
                        viewModel.setSeats(model.getSeats());
                        viewModel.setMoney(Float.valueOf(model.getMoney()) + "");
                        UserViewModel user = BmobUser.getCurrentUser(UserViewModel.class);
                        viewModel.setPhone(user.getPhone());
                        viewModel.setTime(model.getDate() + " " + model.getTime());
                    }
                    binding.setData(viewModel);
                } else {
                    showToast("查找订单失败" + e.getMessage());
                }
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_detail_finish;
    }
}
