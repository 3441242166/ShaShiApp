package com.shashiwang.shashiapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.CustomerActivity;
import com.shashiwang.shashiapp.activity.MessageListActivity;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.presenter.MainFragmentPresenter;
import com.shashiwang.shashiapp.util.ImageLoader;
import com.shashiwang.shashiapp.view.IMainFragmentView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

import static com.shashiwang.shashiapp.constant.Constant.TITLE;
import static com.shashiwang.shashiapp.constant.Constant.TYPE;
import static com.shashiwang.shashiapp.constant.MessageType.*;

public class MainFragment extends BaseFragment<MainFragmentPresenter> implements IMainFragmentView {
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
    @BindView(R.id.ac_base_right)
    Button btRight;

    @Override
    protected MainFragmentPresenter setPresenter() {
        return new MainFragmentPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        StatusBarCompat.setStatusBarColor(Objects.requireNonNull(getActivity()), Color.parseColor("#FFD100"));

        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new ImageLoader());

        presenter.getBannerData();
        initEvent();
    }

    private void initEvent() {
        btFactory.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,FACTORY);
            intent.putExtra(TITLE,getContext().getString(R.string.name_factory));
            startActivity(intent);
        });

        btStation.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,STATION);
            intent.putExtra(TITLE,getContext().getString(R.string.name_station));
            startActivity(intent);
        });

        btFreight.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,FREIGHT);
            intent.putExtra(TITLE,getContext().getString(R.string.name_freight));
            startActivity(intent);
        });

        btDriver.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,DRIVER);
            intent.putExtra(TITLE,getContext().getString(R.string.name_driver));
            startActivity(intent);
        });

        btCar.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),MessageListActivity.class);
            intent.putExtra(TYPE,CAR);
            intent.putExtra(TITLE,getContext().getString(R.string.name_car));
            startActivity(intent);
        });

        btRight.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(),CustomerActivity.class);
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
    public void loadDataSuccess(List<String> data) {
        banner.setImages(data);

        banner.start();
    }

    @Override
    public void errorMessage(String throwable) {

    }

}
