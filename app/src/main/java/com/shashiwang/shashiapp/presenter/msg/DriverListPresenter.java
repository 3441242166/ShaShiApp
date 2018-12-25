package com.shashiwang.shashiapp.presenter.msg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.view.IDriverView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_DRIVER;

public class DriverListPresenter extends BasePresenter<IDriverView> {
    private static final String TAG = "DriverListPresenter";

    private Disposable disposable;

    public DriverListPresenter(IDriverView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void getList(boolean b){

        disposable = RxRetrofitClient.builder()
                .url(URL_DRIVER)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<MessageResult<DriverMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<DriverMessage>>>(){}.getType());

                    if(result.isSuccess()){
                        mView.loadDataSuccess(result.getData().getData());
                    }else {
                        mView.errorMessage(result.getMessage());
                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);
                    mView.errorMessage(throwable.toString());
                });

    }

    @Override
    public void destroy() {
        if(disposable != null && !disposable.isDisposed()){
            disposable.dispose();
        }
        super.destroy();
    }
}
