package com.wrj.film.view.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.tool.util.DataUtils;
import com.tool.util.DialogHelper;
import com.tool.util.RegulrlyUtils;
import com.tool.util.ToastHelp;
import com.wrj.film.R;
import com.wrj.film.databinding.FragmentMineBinding;
import com.wrj.film.model.eventbus.UpdateOrderEvent;
import com.wrj.film.view.ui.activity.LoginActivity;
import com.wrj.film.viewmodel.UserViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import static com.tool.util.ToastHelp.showToast;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MineFragment extends BaseFragment<FragmentMineBinding> {
    private UserViewModel model;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(UpdateOrderEvent message) {
        initData();
    }

    @Override
    protected void initData() {
        model = BmobUser.getCurrentUser(UserViewModel.class);
        if (model != null)
            binding.setData(model);
        else {
            ToastHelp.showToast("请先登录");
            startActivity(LoginActivity.class, null);
        }
    }

    @Override
    protected void initListener() {
        binding.tvMineBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                new DialogHelper().showInputNumDialog(getActivity(), "请输入充值金额", new DialogHelper.InputDialogCallBack() {
                    @Override
                    public void positive(final String content) {
                        showLoading();
                        final float total = Float.valueOf(model.getBalance().replace("元", "").trim()) + Float.valueOf(content.trim());
                        model.setBalance(String.valueOf(total));
                        model.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                closeLoading();
                                if (e == null) {
                                    binding.tvMineBalance.setText(String.valueOf(total) + "元");
                                    showToast("充值成功");
                                } else {
                                    showToast("充值失败" + e.getMessage());
                                }
                            }
                        });
                    }

                    @Override
                    public void negative() {

                    }
                });
            }
        });
        binding.tvMinePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DialogHelper().showInputNumDialog(getActivity(), "请输入手机号码", new DialogHelper.InputDialogCallBack() {
                    @Override
                    public void positive(final String content) {
                        if (DataUtils.checkStrNotNull(content) && RegulrlyUtils.isCellphone(content)) {
                            showLoading();
                            model.setPhone(content);
                            model.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    closeLoading();
                                    if (e == null) {
                                        binding.tvMinePhone.setText(content);
                                        showToast("设置手机号码成功");
                                    } else {
                                        showToast("设置手机号码失败" + e.getMessage());
                                    }
                                }
                            });
                        } else {
                            showToast("请输入正确的手机号码");
                        }
                    }

                    @Override
                    public void negative() {

                    }
                });
            }
        });
        binding.tvOrderFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvOrderPayed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        binding.tvOrderUnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void initView() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
