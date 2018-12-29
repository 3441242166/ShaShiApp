package com.shashiwang.shashiapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

public class CustomerActivity extends BaseTopBarActivity {


    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_customer;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("联系我们");
    }
}
