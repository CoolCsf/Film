package com.wrj.film.view.ui.fragment;

import android.content.DialogInterface;
import android.view.View;

import com.tool.util.DialogHelper;
import com.tool.util.ToastHelp;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.R;
import com.wrj.film.databinding.FragmentMineBinding;
import com.wrj.film.view.ui.activity.LoginActivity;
import com.wrj.film.viewmodel.UserViewModel;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MineFragment extends BaseFragment<FragmentMineBinding> {
    private UserViewModel model;

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
            public void onClick(View v) {
                new DialogHelper().showInputDialog(getActivity(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        
                    }
                });
            }
        });
    }

    @Override
    protected void initView() {
        ((CustomTitleBar) binding.titleBar).setTitle("我的");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }
}
