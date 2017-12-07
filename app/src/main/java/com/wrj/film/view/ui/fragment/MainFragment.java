package com.wrj.film.view.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wrj.film.R;
import com.wrj.film.adapter.BDRVFastAdapter;
import com.wrj.film.databinding.FragmentMainBinding;
import com.wrj.film.databinding.ItemPlayRcyBinding;
import com.wrj.film.viewmodel.FilmPlayRcyViewModel;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/6.
 */

public class MainFragment extends BaseFragment<FragmentMainBinding> {
    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        super.initView();
        setRcyParam(binding.rvHotPlay);
        setRcyParam(binding.rvToBePlay);
        ArrayList<FilmPlayRcyViewModel> list = new ArrayList<>();
        FilmPlayRcyViewModel model = new FilmPlayRcyViewModel();
        model.setPhotoUrl("http://img4.imgtn.bdimg.com/it/u=2580957746,3642550698&fm=27&gp=0.jpg");
        list.add(model);
        FilmPlayRcyViewModel model1 = new FilmPlayRcyViewModel();
        model1.setPhotoUrl("http://img4.imgtn.bdimg.com/it/u=2580957746,3642550698&fm=27&gp=0.jpg");
        list.add(model1);
        FilmPlayRcyViewModel model2 = new FilmPlayRcyViewModel();
        model2.setPhotoUrl("http://img4.imgtn.bdimg.com/it/u=2580957746,3642550698&fm=27&gp=0.jpg");
        list.add(model2);
        FilmPlayRcyViewModel model3 = new FilmPlayRcyViewModel();
        model2.setPhotoUrl("http://img4.imgtn.bdimg.com/it/u=2580957746,3642550698&fm=27&gp=0.jpg");
        list.add(model3);
        FilmPlayRcyViewModel model4 = new FilmPlayRcyViewModel();
        model2.setPhotoUrl("http://img4.imgtn.bdimg.com/it/u=2580957746,3642550698&fm=27&gp=0.jpg");
        list.add(model4);
        FilmPlayRcyViewModel model5 = new FilmPlayRcyViewModel();
        model2.setPhotoUrl("http://img4.imgtn.bdimg.com/it/u=2580957746,3642550698&fm=27&gp=0.jpg");
        list.add(model5);
        FilmPlayRcyViewModel model6 = new FilmPlayRcyViewModel();
        model2.setPhotoUrl("http://img4.imgtn.bdimg.com/it/u=2580957746,3642550698&fm=27&gp=0.jpg");
        list.add(model6);
        BDRVFastAdapter<FilmPlayRcyViewModel, ItemPlayRcyBinding> adapter = new BDRVFastAdapter<>
                (R.layout.item_play_rcy, new ArrayList<FilmPlayRcyViewModel>());
        binding.rvToBePlay.setAdapter(adapter);
        BDRVFastAdapter<FilmPlayRcyViewModel, ItemPlayRcyBinding> adapter1 = new BDRVFastAdapter<>
                (R.layout.item_play_rcy, new ArrayList<FilmPlayRcyViewModel>());
        binding.rvHotPlay.setAdapter(adapter1);
        adapter.setNewData(list);
        adapter1.setNewData(list);
    }

    private void setRcyParam(RecyclerView rcy) {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcy.setLayoutManager(manager);
        rcy.setNestedScrollingEnabled(false);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }
}
