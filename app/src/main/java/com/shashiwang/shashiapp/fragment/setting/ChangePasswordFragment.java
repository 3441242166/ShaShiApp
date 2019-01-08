package com.shashiwang.shashiapp.fragment.setting;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.customizeview.LoginEditText;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CHANGE_PASSWORD;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;

public class ChangePasswordFragment extends BaseFragment {
    private static final String TAG = "ChangePasswordFragment";

    @BindView(R.id.bt_finish)
    Button btFinish;
    @BindView(R.id.ev_password)
    LoginEditText evPassword;


    boolean isShowPassword = false;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_change_password;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        btFinish.setOnClickListener(view -> {
            changePassword(evPassword.getContentText());
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


    @SuppressLint("CheckResult")
    private void changePassword(String newPassword){

        RxRetrofitClient.builder()
                .url(URL_CHANGE_PASSWORD)
                .header(new TokenInterceptor())
                .params("password",newPassword)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);
                            HttpResult<Object> result = new Gson().fromJson(s,new TypeToken<HttpResult<Object>>(){}.getType());
                            if(result.isSuccess()){
                                //修改成功
                                if(!JPushInterface.isPushStopped(getContext())){
                                    JPushInterface.stopPush(getContext());
                                }
                                SharedPreferencesHelper.remove(TOKEN);

                                Toasty.warning(getContext(),"修改密码成功").show();
                                getActivity().finish();
                            }else {
                                Toasty.warning(getContext(),s).show();
                            }
                        }
                        , throwable -> {
                            Log.i(TAG, "accept: "+throwable);
                            Toasty.warning(getContext(),throwable.toString()).show();
                        });

    }
}
