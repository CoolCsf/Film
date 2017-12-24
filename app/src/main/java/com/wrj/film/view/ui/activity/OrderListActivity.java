package com.wrj.film.view.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.CollectionUtils;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.ActivityOrderListBinding;
import com.wrj.film.databinding.ItemOrderListBinding;
import com.wrj.film.model.OrderModel;
import com.wrj.film.model.OrderTypeEnum;
import com.wrj.film.model.eventbus.UpdateOrderEvent;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.Order_List_Item_ViewModel;
import com.wrj.film.viewmodel.UserViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/12/22.
 */

public class OrderListActivity extends AbsActivity<ActivityOrderListBinding> {
    public final static String ORDER_STATUS_KEY = "order_status_key";
    private BDRVFastAdapter<Order_List_Item_ViewModel, ItemOrderListBinding> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(UpdateOrderEvent message) {
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_list;
    }

    @Override
    protected void initData() {
        String status = getIntent().getExtras().getString(ORDER_STATUS_KEY);
        showLoading();
        BmobQuery<OrderModel> query = new BmobQuery<>();
        query.addWhereEqualTo("orderStatus", status);
        query.addWhereEqualTo("user", new BmobPointer(BmobUser.getCurrentUser(UserViewModel.class)));
        query.include("model");
        query.findObjects(new FindListener<OrderModel>() {
            @Override
            public void done(List<OrderModel> list, BmobException e) {
                closeLoading();
                if (e == null) {
                    List<Order_List_Item_ViewModel> models = new ArrayList<>();
                    if (CollectionUtils.collectionState(list) == CollectionUtils.COLLECTION_UNEMPTY) {
                        for (OrderModel model : list) {
                            Order_List_Item_ViewModel viewModel = new Order_List_Item_ViewModel();
                            viewModel.setCinema(model.getCinema());
                            viewModel.setFilmName(model.getFilmName());
                            viewModel.setFilmPhoto(model.getModel().getPhotoUrl());
                            viewModel.setMoney((Float.valueOf(model.getMoney()) * model.getSeats().size()) + "");
                            viewModel.setSeats(model.getSeats());
                            viewModel.setTime(model.getDate() + model.getTime());
                            viewModel.setStatus(model.getOrderStatus());
                            viewModel.setModel(model);
                            models.add(viewModel);
                        }
                    } else {
                        showToast("暂无订单");
                    }
                    mAdapter.setNewData(models);
                } else {
                    showToast("查询订单失败" + e.getMessage());
                }
            }
        });
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mAdapter.getData().get(position).getStatus().equals(OrderTypeEnum.getState(2))) {//待支付
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(OrderConfirmActivity.ORDER_KEY, mAdapter.getData().get(position).getModel());
                    startActivity(OrderConfirmActivity.class, bundle);
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(OrderDetailActivity.ORDER_KEY, mAdapter.getData().get(position).getModel());
                    startActivity(OrderDetailActivity.class, bundle);
                }
            }
        });
    }

    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "订单列表");
        binding.rvOrderList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new BDRVFastAdapter(R.layout.item_order_list, new ArrayList<Order_List_Item_ViewModel>());
        binding.rvOrderList.setAdapter(mAdapter);
    }
}
