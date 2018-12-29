package com.shashiwang.shashiapp.fragment.login;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
    @BindView(R.id.ev_code)
    EditText evCode;
    @BindView(R.id.bt_code)
    Button btCode;
    @BindView(R.id.ev_image_input)
    EditText evImageCode;
    @BindView(R.id.iv_image_code)
    ImageView ivCode;
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
            presenter.register(evPhone.getContentText(),evPassword.getContentText()
            ,evCode.getText().toString());
        });

        btCode.setOnClickListener(view -> presenter.getCode(evImageCode.getText().toString(), evPhone.getContentText()));

        ivCode.setOnClickListener(v -> presenter.getImageCode());

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
        ivCode.setImageBitmap(bitmap);
    }

    @Override
    public void setCodeText(String str) {
        btCode.setText(str);

        if(str.equals("获取验证码")){
            Log.i(TAG, "setCodeText: clickable = true");
            btCode.setClickable(true);
        }

        btCode.setClickable(false);
    }
}
