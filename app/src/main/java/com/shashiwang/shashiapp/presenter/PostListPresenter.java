package com.shashiwang.shashiapp.presenter;

import android.content.Context;

import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.IBaseView;
import com.shashiwang.shashiapp.view.PostListView;

public class PostListPresenter extends BasePresenter<PostListView> {
    public PostListPresenter(PostListView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init() {

    }
}
