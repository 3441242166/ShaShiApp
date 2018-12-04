package com.shashiwang.shashiapp.fragment;

import android.graphics.Color;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.bean.BannerBean;
import com.shashiwang.shashiapp.presenter.MainFragmentPresenter;
import com.shashiwang.shashiapp.util.ImageLoader;
import com.shashiwang.shashiapp.view.IMainFragmentView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

public class MainFragment extends LazyLoadFragment<MainFragmentPresenter> implements IMainFragmentView {
    private static final String TAG = "MainFragment";

    @BindView(R.id.fg_main_banner)
    Banner banner;


    @Override
    protected MainFragmentPresenter setPresenter() {
        return new MainFragmentPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {
        StatusBarCompat.setStatusBarColor(Objects.requireNonNull(getActivity()), Color.parseColor("#FFD100"));

        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setImageLoader(new ImageLoader());

        presenter.getBannerData();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }


    @Override
    public void loadDataSuccess(List<BannerBean> data) {
        List<String> imgUrls = new ArrayList<>(data.size());
        List<String> titles = new ArrayList<>(data.size());
        for(BannerBean bean :data){
            imgUrls.add(bean.getImgUrl());
            titles.add(bean.getTitle());
        }

        banner.setImages(imgUrls);
        banner.setBannerTitles(titles);

        banner.start();
    }

    @Override
    public void errorMessage(String throwable) {

    }

}
