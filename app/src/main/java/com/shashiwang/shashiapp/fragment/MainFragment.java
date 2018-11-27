package com.shashiwang.shashiapp.fragment;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.IBasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.youth.banner.Banner;

import butterknife.BindView;

public class MainFragment extends LazyLoadFragment {

    @BindView(R.id.fg_main_banner)
    Banner banner;

    @Override
    protected IBasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_main;
    }

    @Override
    protected void init() {

    }
}
