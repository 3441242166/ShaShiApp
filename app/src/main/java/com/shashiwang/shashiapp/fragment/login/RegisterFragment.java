package com.shashiwang.shashiapp.fragment.login;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.customizeview.LoginEditText;
import com.shashiwang.shashiapp.presenter.RegisterPresenter;
import com.shashiwang.shashiapp.view.IRegisterView;

import androidx.navigation.Navigation;
import butterknife.BindView;

import static androidx.navigation.Navigation.findNavController;

public class RegisterFragment extends LazyLoadFragment<RegisterPresenter> implements IRegisterView {

    @BindView(R.id.ev_phone)
    LoginEditText evPhone;
    @BindView(R.id.ev_code)
    LoginEditText evCode;
    @BindView(R.id.ev_img_code)
    LoginEditText evImageCode;
    @BindView(R.id.ev_password)
    LoginEditText evPassword;
    @BindView(R.id.bt_login)
    Button btRegister;

    @Override
    protected RegisterPresenter setPresenter() {
        return new RegisterPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_register;
    }

    @Override
    protected void init() {
        initEvent();
    }

    private void initEvent() {
        btRegister.setOnClickListener(view -> {
            presenter.register(evPhone.getContantText(),evPassword.getContantText()
            ,evCode.getContantText(),evImageCode.getContantText());
        });

        evCode.setOnLeftClickListener(() -> presenter.getCode(evImageCode.getContantText(), evPhone.getContantText()));

        evImageCode.setOnLeftClickListener(() -> presenter.getImageCode());
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {
        //Navigation.findNavController(btRegister).popBackStack(R.id.registerFragment, false);
        findNavController(getActivity(), R.id.login_fragment).navigateUp();
    }

    @Override
    public void errorMessage(String throwable) {

    }

    @Override
    public void showImage(Bitmap bitmap) {
        evImageCode.setRightImage(bitmap);
    }
}
