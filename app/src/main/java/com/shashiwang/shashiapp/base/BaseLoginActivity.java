package com.shashiwang.shashiapp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.shashiwang.shashiapp.R;

import butterknife.ButterKnife;

public abstract class BaseLoginActivity<T extends BasePresenter> extends BaseMvpActivity<T> {

    private FrameLayout viewContent;
    private ImageView ivBack;

    protected abstract int getFrameContentView();

    protected abstract void initFrame(Bundle savedInstanceState);

    @Override
    protected void init(Bundle savedInstanceState) {
        viewContent = findViewById(R.id.content_view);
        ivBack = findViewById(R.id.iv_back);

        LayoutInflater.from(BaseLoginActivity.this).inflate(getFrameContentView(),viewContent);
        ButterKnife.bind(this);

        initFrame(savedInstanceState);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_base_login;
    }

}
