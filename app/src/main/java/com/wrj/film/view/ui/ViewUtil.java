package com.wrj.film.view.ui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.AppContext;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

/**
 * Created by Administrator on 2017/12/10.
 */

public class ViewUtil {
    public static void initTitleBar(View titleBar,String title) {
        ((CustomTitleBar)titleBar).setTitle(title);
    }
    public static void rcyAddItemDecoration(RecyclerView recyclerView){
        recyclerView.addItemDecoration(new HorizontalDividerItemDecoration
                .Builder(AppContext.instance).size(3).color(Color.parseColor("#eeeeee")).build());
    }
}
