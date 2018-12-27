package com.shashiwang.shashiapp.fragment.login;

import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.customizeview.LoginEditText;
import com.shashiwang.shashiapp.presenter.LoginPresenter;
import com.shashiwang.shashiapp.view.ILoginView;

import androidx.navigation.Navigation;
import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static com.shashiwang.shashiapp.constant.Constant.RESULT_SUCCESS;

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

    boolean isShowPassword = false;

    @Override
    protected LoginPresenter setPresenter() {
        return new LoginPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_login;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        tvRegister.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        tvForget.setOnClickListener(view -> {
            //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgetFragment);
        });

        btLogin.setOnClickListener(view -> presenter.login(evCount.getContentText(),evPassword.getContentText()));

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
        getActivity().setResult(RESULT_SUCCESS);
        getActivity().finish();
    }

    @Override
    public void errorMessage(String throwable) {
        Toasty.normal(getContext(),throwable).show();
    }
}
