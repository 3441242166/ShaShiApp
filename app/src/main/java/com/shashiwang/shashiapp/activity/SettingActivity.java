package com.shashiwang.shashiapp.activity;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.base.BasePresenter;

import android.os.Bundle;

public class SettingActivity extends BaseTopBarActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_setting;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {

    }
}
