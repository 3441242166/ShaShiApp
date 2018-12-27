package com.shashiwang.shashiapp.fragment.setting;


import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.customizeview.SettingSwitchLayout;

import butterknife.BindView;

public class SettingBroadcastFragment  extends LazyLoadFragment {
    private static final String TAG = "SettingBroadcastFragment";

    @BindView(R.id.sl_all)
    SettingSwitchLayout slAll;
    @BindView(R.id.sl_factory)
    SettingSwitchLayout slFactory;
    @BindView(R.id.sl_freight)
    SettingSwitchLayout slFreight;
    @BindView(R.id.sl_station)
    SettingSwitchLayout slStation;


    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_setting_broadcast;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        slAll.setOnSwitchListener(this::allSwitch);


    }

    private void allSwitch(boolean is){

        slFactory.setCheckable(is);
        slFreight.setCheckable(is);
        slStation.setCheckable(is);

    }

}