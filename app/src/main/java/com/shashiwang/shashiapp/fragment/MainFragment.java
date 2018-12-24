package com.shashiwang.shashiapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.MessageListActivity;
import com.shashiwang.shashiapp.activity.message.CarListActivity;
import com.shashiwang.shashiapp.activity.message.CarMessageActivity;
import com.shashiwang.shashiapp.activity.message.DriverListActivity;
import com.shashiwang.shashiapp.activity.message.DriverMessageActivity;
import com.shashiwang.shashiapp.activity.message.FactoryListActivity;
import com.shashiwang.shashiapp.activity.message.FactoryMessageActivity;
import com.shashiwang.shashiapp.activity.message.FreightListActivity;
import com.shashiwang.shashiapp.activity.message.FreightMessageActivity;
import com.shashiwang.shashiapp.activity.message.StationListActivity;
import com.shashiwang.shashiapp.activity.message.StationMessageActivity;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.bean.BannerBean;
import com.shashiwang.shashiapp.presenter.MainFragmentPresenter;
import com.shashiwang.shashiapp.util.ImageLoader;
import com.shashiwang.shashiapp.view.IMainFragmentView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

import static com.shashiwang.shashiapp.constant.Constant.CLASS;
import static com.shashiwang.shashiapp.constant.Constant.TITLE;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.Constant.TYPE;
import static com.shashiwang.shashiapp.constant.Constant.URL;
import static com.shashiwang.shashiapp.constant.MessageType.*;

public class MainFragment extends LazyLoadFragment<MainFragmentPresenter> implements IMainFragmentView {
    private static final String TAG = "MainFragment";

    @BindView(R.id.fg_main_banner)
    Banner banner;
    @BindView(R.id.bt_facory)
    ImageView btFactory;
    @BindView(R.id.bt_station)
    ImageView btStation;
    @BindView(R.id.bt_freight)
    ImageView btFreight;
    @BindView(R.id.bt_driver)
    ImageView btDriver;
    @BindView(R.id.bt_car)
    ImageView btCar;

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

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new ImageLoader());

        presenter.getBannerData();
        initEvent();
    }

    private void initEvent() {
        btFactory.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),FactoryListActivity.class);
            startActivity(intent);
        });

        btStation.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),StationListActivity.class);
            startActivity(intent);
        });

        btFreight.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),FreightListActivity.class);
            startActivity(intent);
        });

        btDriver.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),DriverListActivity.class);
            startActivity(intent);
        });

        btCar.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),CarListActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }


    @Override
    public void loadDataSuccess(List<BannerBean> data) {
        List<Integer> imgUrls = new ArrayList<>(data.size());
        for(BannerBean bean :data){
            bean.setImgUrl(R.drawable.banner_one);
            imgUrls.add(bean.getImgId());
        }

        banner.setImages(imgUrls);

        banner.start();
    }

    @Override
    public void errorMessage(String throwable) {

    }

}
