package com.shashiwang.shashiapp.interceptor;

import com.example.net.interceptors.BaseInterceptor;
import com.example.util.SharedPreferencesHelper;

import java.io.IOException;

import okhttp3.Request;
import okhttp3.Response;

import static com.shashiwang.shashiapp.constant.Constant.TOKEN;

public class TokenInterceptor extends BaseInterceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request()
                .newBuilder()
                .addHeader("Authorization", (String) SharedPreferencesHelper.getSharedPreference(TOKEN,""))
                .build();

        return chain.proceed(request);
    }
}
