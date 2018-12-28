package com.shashiwang.shashiapp.fragment.login;

import android.os.Bundle;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.base.BasePresenter;

public class ForgetFragment extends BaseFragment {
    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_forget;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }
}
