package com.wrj.film;

import com.tool.util.ToastHelp;
import com.wrj.film.view.ui.activity.AbsActivity;
import com.wrj.film.view.ui.activity.MainActivity;
import com.wrj.film.view.ui.activity.RootMainActivity;
import com.wrj.film.viewmodel.UserViewModel;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;

/**
 * Created by Administrator on 2017/12/2.
 */

public class LoginUtil {
    public static void login(String userName, String pwd, final AbsActivity activity) {
        activity.showLoading();
        BmobUser.loginByAccount(userName, pwd, new LogInListener<UserViewModel>() {
            @Override
            public void done(UserViewModel userViewModel, BmobException e) {
                if (userViewModel != null) {
                    if (!userViewModel.isRoot())
                        activity.startActivity(MainActivity.class, null);
                    else
                        activity.startActivity(RootMainActivity.class, null);
                } else {
                    ToastHelp.showToast("登录失败" + e.toString());
                    activity.closeLoading();
                }
            }
        });
    }
}
