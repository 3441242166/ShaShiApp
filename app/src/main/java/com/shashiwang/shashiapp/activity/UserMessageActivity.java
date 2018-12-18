package com.shashiwang.shashiapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

public class UserMessageActivity extends BaseTopBarActivity {

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_user_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initView();
        getUserMessage();
        setTopRightButton(R.drawable.icon_certain, this::postUserMessage);

    }

    private void initView() {
        setTitle("个人信息");
    }

    private void getUserMessage(){

    }

    private void postUserMessage(){

    }

}
