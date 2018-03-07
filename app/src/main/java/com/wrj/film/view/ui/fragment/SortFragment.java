package com.wrj.film.view.ui.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tool.util.CollectionUtils;
import com.tool.util.ToastHelp;
import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentSortBinding;
import com.wrj.film.databinding.ItemSortHeadRcyBinding;
import com.wrj.film.databinding.ItemSortRcyBinding;
import com.wrj.film.databinding.LayoutSortRcyHeadBinding;
import com.wrj.film.model.FilmModel;
import com.wrj.film.model.FilmModelUtil;
import com.wrj.film.model.SortTypeEnum;
import com.wrj.film.model.eventbus.UpdateFilmNumber;
import com.wrj.film.view.ui.ViewUtil;
import com.wrj.film.view.ui.activity.FilmBuyActivity;
import com.wrj.film.view.ui.activity.FilmDetailActivity;
import com.wrj.film.viewmodel.FilmViewModel;
import com.wrj.film.viewmodel.SimpleStringViewModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;

/**
 * Created by Administrator on 2017/12/9.
 */

public class SortFragment extends BaseFragment<FragmentSortBinding> {
    private BDRVFastAdapter<FilmViewModel, ItemSortRcyBinding> sortAdapter;
    private BDRVFastAdapter<SimpleStringViewModel, ItemSortHeadRcyBinding> headAdapter;
    private String type = SortTypeEnum.ALL.getType();
    private int selectPosition = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void helloEventBus(UpdateFilmNumber message) {
        initData();
    }

    @Override
    protected void initData() {
        headAdapter.getData().get(selectPosition).setCheck(true);
        headAdapter.notifyItemChanged(selectPosition);
        getDataFromBmob(SortTypeEnum.ALL.getType());
    }

    @Override
    protected void initView() {
        super.initView();
        ViewUtil.initTitleBar(binding.titleBar, "分类");
        initRcy();
    }

    private void initRcy() {
        LayoutSortRcyHeadBinding headBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_sort_rcy_head, null, false);
        headAdapter = initRcyParam(headBinding.rvHead, R.layout.item_sort_head_rcy, new GridLayoutManager(getActivity(), 5));
        headAdapter.setNewData(getSortRcyHeadData());
        ViewUtil.rcyAddItemDecoration(binding.rvSort);
        sortAdapter = initRcyParam(binding.rvSort, R.layout.item_sort_rcy, new LinearLayoutManager(getActivity()), R.id.btn_buy);
        sortAdapter.addHeaderView(headBinding.getRoot());
    }

    @Override
    protected void initListener() {
        headAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                headAdapter.getData().get(selectPosition).setCheck(false);
                headAdapter.notifyItemChanged(selectPosition);
                selectPosition = position;
                headAdapter.getData().get(position).setCheck(true);
                headAdapter.notifyItemChanged(position);
                String select = ((SimpleStringViewModel) adapter.getData().get(position)).getText();
                if (select.equals(type))
                    return;
                type = select;
                getDataFromBmob(type);
            }
        });
        sortAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putString(FilmDetailActivity.FILM_INTENT_KEY,
                        ((FilmViewModel) adapter.getData().get(position)).getObjectId());
                startActivity(FilmDetailActivity.class, bundle);
            }
        });
        sortAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.btn_buy) {
                    Bundle bundle = new Bundle();
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getObjectId());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_TYPE_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getType());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_MONEY_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getMoney());
                    bundle.putString(FilmBuyActivity.FILM_BUY_INTENT_FILM_NAME_KEY,
                            ((FilmViewModel) adapter.getData().get(position)).getTitle());
                    startActivity(FilmBuyActivity.class, bundle);
                }
            }
        });
    }

    private void getDataFromBmob(String type) {
        showLoading();
        sortAdapter.setNewData(new ArrayList<FilmViewModel>());
        if (type.equals(SortTypeEnum.ALL.getType())) {
            FilmModelUtil.getFilmModelAll(new FilmModelUtil.FilmModelCallBack() {
                @Override
                public void getModel(List<FilmModel> model) {
                    toRcy(model);
                }
            });
        } else {
            BmobQuery<FilmModel> query = new BmobQuery<>();
            query.addWhereEqualTo("type", type);
            FilmModelUtil.getFilmModelParam(new FilmModelUtil.FilmModelCallBack() {
                @Override
                public void getModel(List<FilmModel> model) {
                    toRcy(model);
                }
            }, query);
        }
    }

    private void toRcy(List<FilmModel> model) {
        closeLoading();
        if (CollectionUtils.collectionState(model) == CollectionUtils.COLLECTION_UNEMPTY) {
            sortAdapter.setNewData(FilmModel.model2viewModel(model));
        } else {
            ToastHelp.showToast("暂无该类别的电影");
        }
    }

    private List<SimpleStringViewModel> getSortRcyHeadData() {
        List<SimpleStringViewModel> list = new ArrayList<>();
        for (SortTypeEnum s : SortTypeEnum.values()) {
            SimpleStringViewModel viewModel = new SimpleStringViewModel();
            viewModel.setText(s.getType());
            list.add(viewModel);
        }
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
