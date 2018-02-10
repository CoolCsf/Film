package com.wrj.film.model;

import com.tool.util.ToastHelp;

import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/12/12.
 */

public class FilmModelUtil {
    public static void getFilmModelAll(final FilmModelCallBack callBack) {
        BmobQuery<FilmModel> query = new BmobQuery<>();
        queryFilm(query, callBack);
    }

    private static void queryFilm(BmobQuery<FilmModel> query, final FilmModelCallBack callBack) {
        query.findObjects(new FindListener<FilmModel>() {
            @Override
            public void done(List<FilmModel> list, BmobException e) {
                if (e == null)
                    callBack.getModel(list);
                else {
                    ToastHelp.showToast(e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    public static void getFilmModelFromId(String id, final FilmModelCallBack callBack) {
        BmobQuery<FilmModel> query = new BmobQuery<>();
        query.addWhereEqualTo("objectId", id);
        queryFilm(query, callBack);
    }

    public static void getFilmModelParam(final FilmModelCallBack callBack, BmobQuery<FilmModel> query) {
//        BmobQuery<FilmModel> query = new BmobQuery<>();
//        if (params != null && params.size() > 0) {
//            for (Map.Entry<String, String> entry : params.entrySet()) {
//                query.addWhereEqualTo(entry.getKey(), entry.getValue());
//            }
//        }
        queryFilm(query, callBack);
    }

    public interface FilmModelCallBack {
        void getModel(List<FilmModel> model);
    }
}
