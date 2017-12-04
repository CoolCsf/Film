package com.tool.util.Bmob;

import com.tool.util.ToastHelp;
import com.tool.util.image.ImageFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.LogInListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/11/12.
 */

public class BmobHelper {
//    public static <BVM extends BmobUser> void Login(BVM viewModel, String userName, String pwd, final IBmobListener listener) {
//       viewModel.getClass().getGenericSuperclass();
//        BmobUser.loginByAccount(userName, pwd, new LogInListener<BVM>() {
//            @Override
//            public void done(BVM bvm, BmobException e) {
//                if (bvm != null) {
//                    listener.LoginSuccessCallBack(bvm);
//                } else {
//                    ToastHelp.showToast(e.toString());
//                }
//            }
//        });
//    }
//
//    public interface IBmobListener {
//        <BM> void LoginSuccessCallBack(BM viewModel);
//    }
}
