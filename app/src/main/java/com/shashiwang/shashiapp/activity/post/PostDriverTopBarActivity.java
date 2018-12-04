package com.shashiwang.shashiapp.activity.post;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

import android.os.Bundle;

public class PostDriverTopBarActivity extends BaseTopBarActivity {

    @Override
    protected IBasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_driver;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {

    }


}
