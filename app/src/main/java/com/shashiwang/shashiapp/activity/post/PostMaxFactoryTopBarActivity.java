package com.shashiwang.shashiapp.activity.post;

import android.os.Bundle;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

public class PostMaxFactoryTopBarActivity extends BaseTopBarActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_max_factory;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {

    }

}
