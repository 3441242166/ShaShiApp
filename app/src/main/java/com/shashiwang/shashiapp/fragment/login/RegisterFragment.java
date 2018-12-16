package com.shashiwang.shashiapp.fragment.login;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.customizeview.LoginEditText;
import com.shashiwang.shashiapp.presenter.RegisterPresenter;
import com.shashiwang.shashiapp.view.IRegisterView;

import androidx.navigation.Navigation;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static androidx.navigation.Navigation.findNavController;

public class RegisterFragment extends LazyLoadFragment<RegisterPresenter> implements IRegisterView {

    @BindView(R.id.ev_phone)
    LoginEditText evPhone;
    @BindView(R.id.ev_code)
    EditText evCode;
    @BindView(R.id.bt_code)
    Button btCode;
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
            ,evCode.getText().toString(),evImageCode.getContantText());
        });

        btCode.setOnClickListener(view -> presenter.getCode(evImageCode.getContantText(), evPhone.getContantText()));

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
        findNavController(getActivity(), R.id.login_fragment).navigateUp();
    }

    @Override
    public void errorMessage(String throwable) {
        Toasty.warning(getContext(), throwable).show();
    }

    @Override
    public void showImage(Bitmap bitmap) {
        evImageCode.setRightImage(bitmap);
    }

    @Override
    public void setCodeText(String str) {
        btCode.setText(str);
        if(str.equals("获取验证码")){
            btCode.setClickable(true);
        }
        btCode.setClickable(false);
    }
}
