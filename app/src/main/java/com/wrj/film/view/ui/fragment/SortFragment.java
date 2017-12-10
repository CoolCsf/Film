package com.wrj.film.view.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.ToastHelp;
import com.tool.util.widget.CustomTitleBar;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentSortBinding;
import com.wrj.film.databinding.ItemSortHeadRcyBinding;
import com.wrj.film.databinding.ItemSortRcyBinding;
import com.wrj.film.databinding.LayoutSortRcyHeadBinding;
import com.wrj.film.model.SortTypeEnum;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.view.ui.activity.FilmBuyActivity;
import com.wrj.film.view.ui.activity.FilmDetailActivity;
import com.wrj.film.viewmodel.FilmRcyItemViewModel;
import com.wrj.film.viewmodel.SimpleStringViewModel;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/9.
 */

public class SortFragment extends BaseFragment<FragmentSortBinding> {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        ViewUtil.initTitleBar(binding.titleBar, "分类");
        initRcy();
    }

    private void initRcy() {
        LayoutSortRcyHeadBinding headBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_sort_rcy_head, null, false);
        BDRVFastAdapter<SimpleStringViewModel, ItemSortHeadRcyBinding> headAdapter
                = initRcyParam(headBinding.rvHead, R.layout.item_sort_head_rcy, new GridLayoutManager(getActivity(), 5));
        headAdapter.setNewData(getSortRcyHeadData());
        ViewUtil.rcyAddItemDecoration( binding.rvSort);
        BDRVFastAdapter<FilmRcyItemViewModel, ItemSortRcyBinding> adapter
                = initRcyParam(binding.rvSort, R.layout.item_sort_rcy, new LinearLayoutManager(getActivity()), R.id.btn_buy);
        adapter.addHeaderView(headBinding.getRoot());
        adapter.setNewData(getSortRcyData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(FilmDetailActivity.class, null);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_buy) {
                    startActivity(FilmBuyActivity.class, null);
                }
            }
        });
    }

    private List<SimpleStringViewModel> getSortRcyHeadData() {
        List<SimpleStringViewModel> list = new ArrayList<>();
        for (String s : SortTypeEnum.getAllType()) {
            SimpleStringViewModel viewModel = new SimpleStringViewModel();
            viewModel.setText(s);
            list.add(viewModel);
        }
        return list;
    }

    @NonNull
    private ArrayList<FilmRcyItemViewModel> getSortRcyData() {
        ArrayList<FilmRcyItemViewModel> list = new ArrayList<>();
        FilmRcyItemViewModel model = new FilmRcyItemViewModel();
        model.setNum("8.3");
        model.setPhotoUrl("sssss");
        model.setTime("2016-20-50");
        model.setTitle("标题哦");
        model.setType("悬疑片");
        list.add(model);
        list.add(model);
        return list;
    }

    private <VM, BD extends ViewDataBinding> BDRVFastAdapter<VM, BD> initRcyParam(RecyclerView rcy, int resId, RecyclerView.LayoutManager manager) {
        return initRcyParam(rcy, resId, manager, 0);
    }

    private <VM, BD extends ViewDataBinding> BDRVFastAdapter<VM, BD> initRcyParam(RecyclerView rcy, int resId, RecyclerView.LayoutManager manager, int id) {
        rcy.setLayoutManager(manager);
        BDRVFastAdapter<VM, BD> adapter = new
                BDRVFastAdapter<>(resId, new ArrayList<VM>(), id);
        rcy.setAdapter(adapter);
        return adapter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort;
    }
}
