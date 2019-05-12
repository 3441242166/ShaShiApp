package com.shashiwang.shashiapp.presenter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.util.DataUtil;
import com.shashiwang.shashiapp.view.ILoginView;

import java.util.List;

import cn.jpush.android.api.JPushInterface;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_FIND_PASSWORD;
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

        RxRetrofitClient.builder()
                .url("login")
                .params("count",count)
                .params("password",password)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                            Log.i(TAG, "accept: "+s);

                            HttpResult<Count> result = new Gson().fromJson(s,new TypeToken<HttpResult<Count>>(){}.getType());

                            if(result.isSuccess()){
                                mView.loadDataSuccess("登录成功");
                                DataUtil.nowCount = result.getData();
                            }else {
                                mView.errorMessage(result.getMessage());
                            }

                        }
                        , throwable -> {
                            Log.i(TAG, "accept: "+throwable);
                            mView.errorMessage(throwable.toString());
                        });
    }

    @Override
    public void destroy() {
        if(loginDisposable != null && !loginDisposable.isDisposed()){
            loginDisposable.dispose();
        }
        super.destroy();
    }

}
