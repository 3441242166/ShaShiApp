package com.shashiwang.shashiapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.BasePresenter;

public class SettingBroadcastActivity extends BaseMvpActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTitle("语音播报设置");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_setting_broadcast;
    }

}
