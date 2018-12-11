package com.shashiwang.shashiapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.MessageListActivity;
import com.shashiwang.shashiapp.activity.message.CarMessageActivity;
import com.shashiwang.shashiapp.activity.message.DriverMessageActivity;
import com.shashiwang.shashiapp.activity.message.FactoryMessageActivity;
import com.shashiwang.shashiapp.activity.message.FreightMessageActivity;
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
import static com.shashiwang.shashiapp.constant.Constant.TYPE;
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

        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        banner.setImageLoader(new ImageLoader());

        presenter.getBannerData();
        initEvent();
    }

    private void initEvent() {
        btFactory.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,FACTORY);
            intent.putExtra(TITLE,FACTORY_TITLE);
            intent.putExtra(CLASS,FactoryMessageActivity.class);
            startActivity(intent);
        });

        btStation.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,STATION);
            intent.putExtra(TITLE,STATION_TITLE);
            intent.putExtra(CLASS,StationMessageActivity.class);
            startActivity(intent);
        });

        btFreight.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,FREIGHT);
            intent.putExtra(TITLE,FREIGHT_TITLE);
            intent.putExtra(CLASS,FreightMessageActivity.class);
            startActivity(intent);
        });

        btDriver.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,DRIVER);
            intent.putExtra(TITLE,DRIVER_TITLE);
            intent.putExtra(CLASS,DriverMessageActivity.class);
            startActivity(intent);
        });

        btCar.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,CAR);
            intent.putExtra(TITLE,CAR_TITLE);
            intent.putExtra(CLASS,CarMessageActivity.class);
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
        List<String> titles = new ArrayList<>(data.size());
        for(BannerBean bean :data){
            bean.setImgUrl(R.drawable.banner_one);
            imgUrls.add(bean.getImgId());
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
