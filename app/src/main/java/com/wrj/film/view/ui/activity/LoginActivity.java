package com.wrj.film.view.ui.activity;

import android.view.View;

import com.wrj.film.LoginUtil;
import com.wrj.film.R;
import com.wrj.film.databinding.ActivityLoginBinding;
import com.wrj.film.model.FilmDate;
import com.wrj.film.model.FilmTime;
import com.wrj.film.viewmodel.UserViewModel;

import java.util.List;


public class LoginActivity extends BaseActivity<ActivityLoginBinding, UserViewModel> {

    @Override
    protected void initListener() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                loginToBmob();
            }
        });
        binding.tvRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toRegisterAct();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
    }

    private void toRegisterAct() {
        startActForResult(RegisteredActivity.class, null, 0);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }


    private void loginToBmob() {
        LoginUtil.login(viewModel.getUserName(), viewModel.getPwd(), this);
    }


    @Override
    protected void initView() {
        binding.setLogin(viewModel);
    }

    @Override
    protected UserViewModel getViewModel() {
        UserViewModel model = new UserViewModel();
        binding.setLogin(model);
        return model;
    }
}
