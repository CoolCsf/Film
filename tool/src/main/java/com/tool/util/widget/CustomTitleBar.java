package com.tool.util.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tool.R;

/**
 * 文件描述：封装ToolBar导航栏
 * Created by sf.chen on 2017/3/7.
 */
public class CustomTitleBar extends LinearLayout {

    private TextView tv_title;
    private TextView tv_title_right;
    private ImageView img_title_add;
//    private ImageView img_title_menu;
//    private ImageView img_title_right;
//    private ImageView img_title_right2;
    private ImageView img_return;
    private Context mContext;
    private OnClickListener onReturnListener; //在窗体返回前执行此事件
    private View shadowView;

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_title = (TextView) findViewById(R.id.titleBar_title);
        img_return = (ImageView) findViewById(R.id.img_return);
        tv_title_right = (TextView) findViewById(R.id.titleBar_right);
        img_title_add = (ImageView) findViewById(R.id.titleBar_img_add);
//        img_title_menu = (ImageView) findViewById(R.id.titleBar_menu);
//        img_title_right = (ImageView) findViewById(R.id.titleBar_img_right);
//        img_title_right2 = (ImageView) findViewById(R.id.titleBar_img_right2);
    }

//    public void showDropDownView(OnClickListener listener) {
//        ImageView imageView = (ImageView) findViewById(R.id.img_dropDown);
//        imageView.setVisibility(VISIBLE);
//        tv_title.setOnClickListener(listener);
//    }

    //设置主标题颜色
    public void setTitleColor(int color) {
        if (color != 0)
            tv_title.setTextColor(ContextCompat.getColor(mContext, color));
    }

    //设置主标题
    public void setTitle(String title) {
        if (!title.equals("")) {
            tv_title.setVisibility(VISIBLE);
            tv_title.setText(title);
        } else {
            tv_title.setVisibility(GONE);
            tv_title.setText(title);
        }
    }

    //获取主标题
    public String getTitle() {
        return tv_title.getText().toString();
    }

    //设置主标题
    public void setTitle(int title) {
        if (title != 0) {
            setTitle(mContext.getResources().getString(title));
        }
    }

    //设置右边标题
    public void setRightTitle(CharSequence rightTitle) {
        if (!rightTitle.equals("")) {
            tv_title_right.setVisibility(VISIBLE);
            tv_title_right.setText(rightTitle);
        } else {
            tv_title_right.setText("");
        }
    }

    //设置右边标题
    public void setRightTitle(int rightTitle) {
        if (rightTitle != 0) {
            setRightTitle(mContext.getResources().getString(rightTitle));
        }
    }

    //设置右边标题监听
    public void setRightTitleOnClickListener(OnClickListener listener) {
        tv_title_right.setOnClickListener(listener);
    }

    //显示添加图标
    public void showAdd() {
        img_title_add.setVisibility(VISIBLE);
    }
//
//    //设置右边图标(偏左边位置)
//    public void setImgRitht(int res) {
//        if (res != 0) {
//            img_title_right.setVisibility(VISIBLE);
//            img_title_right.setImageDrawable(ContextCompat.getDrawable(mContext, res));
//        }
//    }
//
//    //设置右边图标(偏右边位置)
//    public void setImgRitht2(int res) {
//        if (res != 0) {
//            img_title_right2.setVisibility(VISIBLE);
//            img_title_right2.setImageDrawable(ContextCompat.getDrawable(mContext, res));
//        }
//    }
//
//    //显示菜单按钮，调用者可通过监听id调用
//    public void showMenuIcon() {
//        img_title_menu.setVisibility(VISIBLE);
//    }

    /**
     * titlebar的菜单栏返回键方法
     *
     * @param activity       依附的activity
     */
    public void showReturnBack(final Activity activity) {
        img_return.setVisibility(VISIBLE);
        img_return.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
//                if (onReturnListener != null) {
//                    onReturnListener.onClick(view);
//                }
//                InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//                if (imm != null) {
//                    imm.hideSoftInputFromWindow(((Activity) mContext).getWindow().getDecorView().getWindowToken(), 0);
//                }
//                FragmentHelp.returnBack(activity, isDirectFinish);
            }
        });
    }

//        this.setNavigationOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//        });

    /**
     * 设置返回键样式
     */
    public void setReturnBackStyle(int drawable) {
        if (drawable != 0) {
            if (!img_return.isShown())
                img_return.setVisibility(VISIBLE);
            img_return.setImageDrawable(ContextCompat.getDrawable(mContext, drawable));
        }
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public void showShadowView() {
////        this.setElevation(0);
////        shadowView.setVisibility(VISIBLE);
//    }

    /**
     * 设置窗体返回时的响应事件
     */
    public void setOnReturnListener(OnClickListener listener) {
        onReturnListener = listener;
    }

    /**
     * 设置标题栏背景颜色
     *
     * @param color
     */
    public void setBgColor(int color) {
        this.setBackgroundColor(ContextCompat.getColor(mContext, color));
        if (color != R.color.white) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setElevation(0);
            }
//            if (shadowView.isShown()) {
////                shadowView.setVisibility(GONE);
//            }
//            findViewById(R.id.tv_shadowView).setVisibility(GONE);//如果不是白色的。则隐藏阴影
        }
    }
}