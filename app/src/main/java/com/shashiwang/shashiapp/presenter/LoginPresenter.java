package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.net.interceptors.LoggingInterceptor;
import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.view.ILoginView;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_JPUSHID;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_LOGIN;
import static com.shashiwang.shashiapp.constant.Constant.REGISTRATION_ID;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;
import static com.shashiwang.shashiapp.constant.Constant.USER_NAME;

public class LoginPresenter extends BasePresenter<ILoginView> {
    private static final String TAG = "LoginPresenter";

    private Disposable loginDisposable;

    public LoginPresenter(ILoginView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    @SuppressLint("CheckResult")
    public void login(String count, String password) {
        Log.i(TAG, "login: ");

        if(TextUtils.isEmpty(count)){
            Toast.makeText(mContext,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            Toast.makeText(mContext,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }

        loginDisposable = RxRetrofitClient.builder()
                .url(URL_LOGIN)
                .params("phone",count)
                .params("password",password)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "login: success " + s);
                    HttpResult<LoginBean> result = new Gson().fromJson(s,new TypeToken<HttpResult<LoginBean>>(){}.getType());

                    if(result.isSuccess()){
                        Log.i(TAG, "login: 登陆成功");

                        SharedPreferencesHelper.put(TOKEN,result.getData().token);
                        SharedPreferencesHelper.put(USER_NAME,count);

                        String registrationId = JPushInterface.getRegistrationID(mContext);
                        Log.i(TAG, "login: registrationId = "+registrationId);
                        SharedPreferencesHelper.put(REGISTRATION_ID,registrationId);
                        putRegistrationId(registrationId);

                        mView.loadDataSuccess("登录成功");
                    }else {
                        Log.i(TAG, "login: 登录失败 "+result.getMessage());
                        mView.errorMessage(result.getMessage());
                    }

                }, throwable -> {
                    Log.i(TAG, "login: error = " + throwable);
                    mView.errorMessage(throwable.toString());
                    Toast.makeText(mContext,throwable.toString(),Toast.LENGTH_LONG).show();
                });

    }

    @SuppressLint("CheckResult")
    private void putRegistrationId(String id){
        Log.i(TAG, "putRegistrationId:  = "+id);
        RxRetrofitClient.builder()
                .header(new TokenInterceptor())
                .url(URL_JPUSHID)
                .params("jpush_reg_id",id)
                .build()
                .post()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "login: success " + s);
                }, throwable -> {
                    Log.i(TAG, "login: error = " + throwable);
                });

    }

    @Override
    public void destroy() {
        if(loginDisposable != null && !loginDisposable.isDisposed()){
            loginDisposable.dispose();
        }
        super.destroy();
    }

    private static class LoginBean{
        String token;
    }

}
