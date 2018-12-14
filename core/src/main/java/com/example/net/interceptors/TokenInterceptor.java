package com.example.net.interceptors;

import android.util.Log;

import com.example.util.SharedPreferencesHelper;

import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor extends BaseInterceptor {
    private static final String TAG = "TokenInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {

        String token = "Bearer "+ SharedPreferencesHelper.getSharedPreference("token","");

        Log.i(TAG, "intercept: token = " + token);

        Log.i(TAG, "intercept: token = "+token);

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization",token)
                .build();

        return chain.proceed(request);
    }
}
