package com.wrj.film.view.ui.fragment;

import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.R;
import com.wrj.film.databinding.FragmentMineBinding;

/**
 * Created by Administrator on 2017/12/8.
 */

public class MineFragment extends BaseFragment<FragmentMineBinding> {
    @Override
    protected void initData() {

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
