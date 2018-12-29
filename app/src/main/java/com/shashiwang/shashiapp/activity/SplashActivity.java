package com.shashiwang.shashiapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.presenter.SplashPresenter;
import com.shashiwang.shashiapp.view.ISplashView;

public class SplashActivity extends BaseMvpActivity<SplashPresenter> implements ISplashView {

    @Override
    protected SplashPresenter setPresenter() {
        return new SplashPresenter(this,this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        presenter.checkVersion();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_splash;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {

    }

    @Override
    public void errorMessage(String throwable) {

    }
}
