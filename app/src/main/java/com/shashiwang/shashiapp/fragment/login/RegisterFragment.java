package com.shashiwang.shashiapp.fragment.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.customizeview.LoginEditText;
import com.shashiwang.shashiapp.presenter.RegisterPresenter;
import com.shashiwang.shashiapp.view.IRegisterView;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static androidx.navigation.Navigation.findNavController;

public class RegisterFragment extends BaseFragment<RegisterPresenter> implements IRegisterView {

    @BindView(R.id.ev_phone)
    LoginEditText evPhone;
    @BindView(R.id.ev_password)
    LoginEditText evPassword;
    @BindView(R.id.bt_login)
    Button btRegister;

    boolean isShowPassword = false;

    @Override
    protected RegisterPresenter setPresenter() {
        return new RegisterPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initEvent();
    }

    private void initEvent() {
        btRegister.setOnClickListener(view -> {
            presenter.register(evPhone.getContentText(),evPassword.getContentText(),"");
        });

        evPassword.setOnRightClickListener(() -> {
            if(isShowPassword){
                evPassword.setRightImage(R.drawable.ic_open_eye);
                evPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }else {
                evPassword.setRightImage(R.drawable.ic_close_eye);
                evPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            isShowPassword = !isShowPassword;
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(String data) {
        Toasty.normal(getContext(),data).show();
        findNavController(getActivity(), R.id.login_fragment).navigateUp();
    }

    @Override
    public void errorMessage(String throwable) {
        Toasty.warning(getContext(), throwable).show();
    }

    @Override
    public void showImage(Bitmap bitmap) {

    }

    @Override
    public void setCodeText(String str) {

    }
}
