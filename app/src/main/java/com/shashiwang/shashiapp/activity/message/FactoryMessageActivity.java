package com.shashiwang.shashiapp.activity.message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

public class FactoryMessageActivity extends BaseTopBarActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_factory_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("详情");

    }
}
