package com.shashiwang.shashiapp.fragment;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;

public class MyFragment extends LazyLoadFragment{
    @Override
    protected IBasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {

    }
}
