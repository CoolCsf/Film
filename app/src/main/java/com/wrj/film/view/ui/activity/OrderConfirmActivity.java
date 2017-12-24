package com.wrj.film.view.ui.activity;

import android.util.Log;
import android.view.View;

import com.wrj.film.view.widget.DialogHelper;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityOrderConfimBinding;
import com.wrj.film.model.OrderModel;
import com.wrj.film.model.OrderTypeEnum;
import com.wrj.film.model.eventbus.UpdateOrderEvent;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.viewmodel.OrderDetailViewModel;
import com.wrj.film.viewmodel.UserViewModel;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class OrderConfirmActivity extends BaseActivity<ActivityOrderConfimBinding, OrderDetailViewModel> {
    public final static String ORDER_KEY = "order_key";
    private OrderModel model;
    private float money;

    @Override
    protected OrderDetailViewModel getViewModel() {
        return new OrderDetailViewModel();
    }


    @Override
    protected void initView() {
        ViewUtil.initTitleBar(binding.titleBar, "待支付订单");
    }

    @Override
    protected void initListener() {
        super.initListener();
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserViewModel user = BmobUser.getCurrentUser(UserViewModel.class);
                final float balance = Float.valueOf(user.getBalance().replace("元", "").trim());
                if (balance < money) {
                    showToast("账户余额不足");
                    return;
                }
                new DialogHelper().showContentDialog(OrderConfirmActivity.this, "", "确认支付?", new DialogHelper.InputDialogCallBack() {
                    @Override
                    public void positive(String content) {
                        model.setOrderStatus(OrderTypeEnum.getState(1));
                        model.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                updateUserBalance(new DecimalFormat("##0.0").format(balance - money));
                                EventBus.getDefault().post(new UpdateOrderEvent());
                                finish();
                            }
                        });
                    }

                    @Override
                    public void negative() {

                    }
                });
            }
        });
    }

    private void updateUserBalance(String balance) {
        UserViewModel user = BmobUser.getCurrentUser(UserViewModel.class);
        user.setBalance(balance);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                closeLoading();
                if (e != null) {
                    Log.e(TAG, "更新余额失败" + e.getMessage());
                } else {
                    EventBus.getDefault().post(new UpdateOrderEvent());
                    Log.e(TAG, "更新余额成功");
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        model = (OrderModel) getIntent().getExtras().getSerializable(ORDER_KEY);
        if (model != null) {
            viewModel.setCinema(model.getCinema());
            viewModel.setFilmName(model.getFilmName());
            viewModel.setSeats(model.getSeats());
            money = Float.valueOf(model.getMoney()) * model.getSeats().size();
            viewModel.setMoney(money + "");
            UserViewModel user = BmobUser.getCurrentUser(UserViewModel.class);
            viewModel.setPhone(user.getPhone());
            viewModel.setTime(model.getDate() + " " + model.getTime());
        }
        binding.setData(viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_order_confim;
    }
}
