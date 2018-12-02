package com.shashiwang.shashiapp.activity.postactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.TopBarBaseActivity;

public class PostCarMessageActivity extends TopBarBaseActivity {

    @Override
    protected IBasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_car_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("石料厂");
    }

}
