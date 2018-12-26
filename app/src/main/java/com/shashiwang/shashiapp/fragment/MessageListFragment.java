package com.shashiwang.shashiapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;

public class MessageListFragment extends LazyLoadFragment{

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private MessageAdapter adapter;

    public static MessageListFragment newInstance(String content) {
        Bundle args = new Bundle();
        args.putString("content", content);
        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void init() {

        adapter = new MessageAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        getList();
    }

    @SuppressLint("CheckResult")
    private void getList(){

        RxRetrofitClient.builder()
                .header(new TokenInterceptor())
                .url(URL_CAR)
                .params("page",123)
                .build()
                .get()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Log.i(TAG, "getList: success " + s);
                    HttpResult<MessageResult<CarMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<CarMessage>>>(){}.getType());

                    if(result.isSuccess()){

                    }else {

                    }

                }, throwable -> {
                    Log.i(TAG, "getList: error = " + throwable);

                });

    }
}
