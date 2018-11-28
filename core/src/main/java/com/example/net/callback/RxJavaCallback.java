package com.example.net.callback;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class RxJavaCallback implements Observer<Response<ResponseBody>> {
    private static final String TAG = "RxJavaCallback";

    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;


    public RxJavaCallback(IRequest irequest, ISuccess isuccess, IError ierror, IFailure ifailure) {
        IREQUEST = irequest;
        ISUCCESS = isuccess;
        IERROR = ierror;
        IFAILURE = ifailure;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(Response<ResponseBody> value) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
