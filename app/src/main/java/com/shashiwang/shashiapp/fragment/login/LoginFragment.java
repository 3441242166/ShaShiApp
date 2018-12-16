package com.shashiwang.shashiapp.fragment.login;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.customizeview.LoginEditText;
import com.shashiwang.shashiapp.presenter.LoginPresenter;
import com.shashiwang.shashiapp.view.ILoginView;

import androidx.navigation.Navigation;
import butterknife.BindView;

public class LoginFragment extends LazyLoadFragment<LoginPresenter> implements ILoginView {

    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_forget)
    TextView tvForget;
    @BindView(R.id.ev_count)
    LoginEditText evCount;
    @BindView(R.id.ev_password)
    LoginEditText evPassword;

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this,getContext());
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

        btLogin.setOnClickListener(view -> presenter.login(evCount.getContantText(),evPassword.getContantText()));

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {

        getActivity().finish();
    }

    @Override
    public void errorMessage(String throwable) {

    }
}
