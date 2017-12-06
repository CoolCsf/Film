package com.wrj.film.view.ui.activity;


import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.tool.util.DataUtils;
import com.tool.util.ToastHelp;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.LoginUtil;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityRegisteredBinding;
import com.wrj.film.viewmodel.UserViewModel;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisteredActivity extends BaseActivity<ActivityRegisteredBinding, UserViewModel> {
    @Override
    protected void initView() {
        ((CustomTitleBar) binding.titleBar).setTitle("注册");
    }

    @Override
    protected void initListener() {
        binding.btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkETNotNull(binding.etUserName, "请输入用户名") && checkETNotNull(binding.etPwd, "请输入密码")
                        && checkETNotNull(binding.etPwdConfirm, "请输入确认密码") && checkPwdConfirm() && checkETNotNull(binding.etName, "请输入您的名字")) {
                    showLoading();
                    viewModel.setRoot(false);
                    viewModel.signUp(new SaveListener<UserViewModel>() {
                        @Override
                        public void done(UserViewModel o, BmobException e) {
                            closeLoading();
                            if (e == null) {
                                ToastHelp.showToast("注册成功，开始登录");
                                LoginUtil.login(o.getUserName(), o.getPwd(), RegisteredActivity.this);
                            } else {
                                Log.e(TAG, "注册失败" + e.getMessage());
                                ToastHelp.showToast("注册失败" + e.getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean checkETNotNull(EditText editText, String errorMsg) {
        if (DataUtils.checkStrNotNull(editText.getText().toString())) return true;
        ToastHelp.showToast(errorMsg);
        return false;
    }

    private boolean checkPwdConfirm() {
        return binding.etPwd.getText().toString().trim().equals(binding.etPwdConfirm.getText().toString().trim());
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_registered;
    }

    @Override
    protected UserViewModel getViewModel() {
        UserViewModel model = new UserViewModel();
        binding.setData(model);
        return model;
    }
}
