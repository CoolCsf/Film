package com.wrj.film.view.ui.activity;

import android.support.v4.app.Fragment;

import com.wrj.film.R;
import com.wrj.film.adapter.ViewPagerFragmentAdapter;
import com.wrj.film.databinding.ActivityMainBinding;
import com.wrj.film.view.ui.fragment.MainFragment;

import java.util.ArrayList;

public class MainActivity extends AbsActivity<ActivityMainBinding> {

    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> list = new ArrayList<>();
        MainFragment fragment = new MainFragment();
        list.add(fragment);
        MainFragment fragment1 = new MainFragment();
        list.add(fragment1);
        MainFragment fragment2 = new MainFragment();
        list.add(fragment2);
        MainFragment fragment3 = new MainFragment();
        list.add(fragment3);
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
//    private void setBanner(final IndexDataBean dataBean) {
//        //设置banner图
//        List<String> listsImg = new ArrayList<>();
//        int height = (ScreenUtils.getScreenHeight(getActivity()) - ScreenUtils.getStatusHeight(getActivity())) / 4;//高度为去除状态栏后的屏幕的1/4
//        convenientBanner.getLayoutParams().height = height;
//        convenientBanner.getLayoutParams().width = ScreenUtils.getScreenWidth(getActivity());
//        convenientBanner.requestLayout();
//        for (int i = 0; i < dataBean.getData().get(0).getBanner().size(); i++) {
//            listsImg.add(dataBean.getData().get(0).getBanner().get(i).getImage());
//        }
//        convenientBanner.setPages(new CBViewHolderCreator() {
//            @Override
//            public Object createHolder() {
//                return new WebImageHolderView();
//            }
//        }, listsImg)
//                .setPointViewVisible(true)
//                //设置指示器的位置
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
//                .setOnItemClickListener(new OnItemClickListener() {
//                    @Override
//                    public void onItemClick(int position) {
//                        String custom = dataBean.getData().get(0).getBanner().get(position).getCustom();
//                        switch (custom) {
//                            case "serveHealthUI":
//                                Bundle bundleP = new Bundle();
//                                bundleP.putString("code", OrderCodeUtil.getServiceHealth());
//                                goActivity(HealthPreserveFragment.class.getName(), ServiceMainActivity.class, bundleP);
//                                break;
//                            case "serveConsultUI":
//                                goActivity(HealthConsultFragment.class.getName(), ServiceMainActivity.class, null);
//                                break;
//                            case "serveLessonUI":
//                                goActivity(HealthLessonMainFragment.class.getName(), ServiceMainActivity.class, null);
//                                break;
//                            case "serveHomeBasedCareUI":
//                                Bundle bundlePension = new Bundle();
//                                bundlePension.putString("code", OrderCodeUtil.getServicePersion());
//                                goActivity(HealthPreserveFragment.class.getName(), ServiceMainActivity.class, bundlePension);
//                                break;
//                            case "measureSiteUI":
//                                goActivity(FreeMeasureFragment.class.getName(), FreeMeasureActivity.class, null);
//                                break;
//                        }
//                    }
//                })
//                .startTurning(4000)
//                //设置手动影响
//                .setManualPageable(true);
//    }
}
