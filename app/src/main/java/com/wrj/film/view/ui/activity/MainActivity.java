package com.wrj.film.view.ui.activity;

import android.support.v4.app.Fragment;

import com.wrj.film.R;
import com.wrj.film.adapter.ViewPagerFragmentAdapter;
import com.wrj.film.databinding.ActivityMainBinding;
import com.wrj.film.view.ui.fragment.MainFragment;
import com.wrj.film.view.ui.fragment.MineFragment;
import com.wrj.film.view.ui.fragment.RankingFragment;
import com.wrj.film.view.ui.fragment.SortFragment;

import java.util.ArrayList;

public class MainActivity extends AbsActivity<ActivityMainBinding> {

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> list = new ArrayList<>();
        MainFragment fragment1 = new MainFragment();
        list.add(fragment1);
        SortFragment fragment3 = new SortFragment();
        list.add(fragment3);
        RankingFragment fragment2 = new RankingFragment();
        list.add(fragment2);
        MineFragment fragment = new MineFragment();
        list.add(fragment);
        return list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        super.initView();
        String[] tabTitle = getResources().getStringArray(R.array.main_tab);
        ViewPagerFragmentAdapter adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager(), tabTitle, getFragments());
        binding.vpMain.setAdapter(adapter);
        binding.tlMain.setupWithViewPager(binding.vpMain);
    }

    @Override
    protected void initData() {

    }
}
