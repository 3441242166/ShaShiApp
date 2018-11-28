package com.example.net.interceptors;

import com.example.util.SharedPreferencesHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor extends BaseInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", (String) SharedPreferencesHelper.getSharedPreference("token",""))
                .build();

        return chain.proceed(request);
    }
}
