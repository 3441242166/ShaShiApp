package com.shashiwang.shashiapp.presenter.msg;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.view.ICarView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;

public class CarListPresenter extends BasePresenter<ICarView> {
    private static final String TAG = "CarListPresenter";

    private Disposable disposable;


    public CarListPresenter(ICarView view, Context context) {
        super(view, context);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

    }

    public void getList(boolean b){

        disposable = RxRetrofitClient.builder()
                .url(URL_CAR)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<MessageResult<CarMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<CarMessage>>>(){}.getType());

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
