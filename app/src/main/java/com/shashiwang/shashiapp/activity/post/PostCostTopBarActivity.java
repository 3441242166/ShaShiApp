package com.shashiwang.shashiapp.activity.post;

import android.os.Bundle;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

public class PostCostTopBarActivity extends BaseTopBarActivity {

    @Override
    protected IBasePresenter setPresenter() {
        return null;
    }


    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_cost;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {

    }


}
