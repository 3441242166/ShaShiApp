package com.shashiwang.shashiapp.fragment.login;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.presenter.RegisterPresenter;
import com.shashiwang.shashiapp.view.IRegisterView;

import butterknife.BindView;

public class RegisterFragment extends LazyLoadFragment<RegisterPresenter> implements IRegisterView {

    @BindView(R.id.ev_phone)
    EditText evPhone;
    @BindView(R.id.ev_code)
    EditText evCode;
    @BindView(R.id.ev_image_code)
    EditText evImageCode;
    @BindView(R.id.ev_password)
    EditText evPassword;
    @BindView(R.id.iv_code)
    ImageView ivCode;
    @BindView(R.id.bt_code)
    Button btCode;
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
            presenter.register(evPhone.getText().toString(),evPassword.getText().toString()
            ,evCode.getText().toString(),evImageCode.getText().toString());
        });

        btCode.setOnClickListener(view -> {
            presenter.getCode(evImageCode.getText().toString(),evPhone.getText().toString());
        });

        ivCode.setOnClickListener(view -> {
            presenter.getImageCode();
        });
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

    @Override
    public void showImage(Bitmap bitmap) {
        ivCode.setImageBitmap(bitmap);
    }
}
