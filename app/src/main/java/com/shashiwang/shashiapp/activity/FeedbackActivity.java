package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.GridAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

import java.io.Serializable;

public class FeedbackActivity extends BaseTopBarActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("意见反馈");

    }
}
