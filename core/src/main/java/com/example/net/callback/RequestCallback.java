package com.example.net.callback;

import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.ui.dialog.Loader;
import com.example.ui.dialog.LoaderStyle;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RequestCallback implements Callback<String>{
    private static final String TAG = "RequestCallback";

    private final IRequest IREQUEST;
    private final ISuccess ISUCCESS;
    private final IError IERROR;
    private final IFailure IFAILURE;
    private final LoaderStyle LOADER_STYLE;
    private static final Handler HANDLER = new Handler();

    public RequestCallback(IRequest irequest, ISuccess isuccess, IError ierror, IFailure ifailure,LoaderStyle loaderStyle) {
        IREQUEST = irequest;
        ISUCCESS = isuccess;
        IERROR = ierror;
        IFAILURE = ifailure;
        LOADER_STYLE = loaderStyle;
    }

    @Override
    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
        Log.i(TAG, "onResponse.errorBody: " + response.errorBody());

        if(response.isSuccessful()){
            if(call.isExecuted()){
                if (ISUCCESS != null){
                    ISUCCESS.onSuccess(response.body());
                }
            }
        }else {
            if(IERROR != null){
                IERROR.onError(response.code(),response.message());
            }
        }
        stopLoading();
    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        Log.i(TAG, "onFailure: " + t);
        if(IFAILURE != null){
            IFAILURE.onFailure(t);
        }
        if(IREQUEST != null){
            IREQUEST.onRequestFinish();
        }
        stopLoading();
    }

    private void stopLoading(){
        Log.i(TAG, "stopLoading");
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                Loader.stopLoading();
            }
        },3000);
//        if(LOADER_STYLE != null){
//            HANDLER.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Loader.stopLoading();
//                }
//            },3000);
//        }
    }
}
