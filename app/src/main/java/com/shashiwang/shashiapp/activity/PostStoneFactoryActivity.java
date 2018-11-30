package com.shashiwang.shashiapp.activity;

import android.os.Bundle;

import com.shashiwang.shashiapp.base.TopBarBaseActivity;
import com.shashiwang.shashiapp.presenter.IssueActivityPresenter;
import com.shashiwang.shashiapp.view.IIssueActivityView;
import com.shashiwang.shashiapp.R;

public class PostStoneFactoryActivity extends TopBarBaseActivity<IssueActivityPresenter> implements IIssueActivityView {
    private static final String TAG = "PostStoneFactoryActivity";


    @Override
    protected IssueActivityPresenter setPresenter() {
        return new IssueActivityPresenter(this,this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_post_stone_factory;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        setTopLeftButton(this::finish);
        setTitle("石料厂");
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
