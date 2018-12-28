package com.shashiwang.shashiapp.fragment.setting;

import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.base.BasePresenter;

public class AboutFragment extends BaseFragment {


    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_about;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
