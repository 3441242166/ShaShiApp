package com.shashiwang.shashiapp.fragment.login;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;

import androidx.navigation.Navigation;
import butterknife.BindView;

public class LoginFragment extends LazyLoadFragment {

    @BindView(R.id.bt_login)
    Button btLonin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget)
    TextView tvForget;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void init() {

        tvRegister.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        tvForget.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgetFragment);
        });

    }
}
