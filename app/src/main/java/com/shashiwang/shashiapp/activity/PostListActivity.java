package com.shashiwang.shashiapp.activity;

import android.os.Bundle;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.base.BasePresenter;

public class PostListActivity extends BaseTopBarActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {

    }
}
