package com.shashiwang.shashiapp.activity.postactivity;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.TopBarBaseActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PostDriverActivity extends TopBarBaseActivity {

    @Override
    protected IBasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_post_driver;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

}
