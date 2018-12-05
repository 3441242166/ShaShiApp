package com.shashiwang.shashiapp.activity;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseMvpActivity;
import com.shashiwang.shashiapp.base.BasePresenter;

import butterknife.ButterKnife;

import static androidx.navigation.Navigation.findNavController;

public class LoginActivity<T extends BasePresenter> extends BaseMvpActivity<T> {

    private FrameLayout viewContent;
    private ImageView ivBack;


    @Override
    protected T setPresenter() {
        return null;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        ButterKnife.bind(this);


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return findNavController(this, R.id.login_fragment).navigateUp();
    }
}
