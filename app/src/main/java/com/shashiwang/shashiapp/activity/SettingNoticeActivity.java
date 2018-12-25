package com.shashiwang.shashiapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

public class SettingNoticeActivity extends BaseTopBarActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_setting_notice;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("语音播报设置");
    }

}
