package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.post.PostFreightActivity;
import com.shashiwang.shashiapp.adapter.GridAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.constant.ApiConstant;
import com.shashiwang.shashiapp.customizeview.LoginEditText;

import java.io.Serializable;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedbackActivity extends BaseTopBarActivity {
    private static final String TAG = "FeedbackActivity";

    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.ed_input)
    EditText edInput;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_feedback;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        setTitle("意见反馈");


        btSend.setOnClickListener(view -> {
            RxRetrofitClient.builder()
                    .header(new TokenInterceptor())
                    .url(ApiConstant.URL_FEEDBACK)
                    .params("content",edInput.getText().toString())
                    .build()
                    .post()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        Log.i(TAG, "getList: success " + s);
                        HttpResult<MessageResult<FreightMessage>> result = new Gson().fromJson(s,new TypeToken<HttpResult<MessageResult<FreightMessage>>>(){}.getType());

                        if(result.isSuccess()){
                            Toast.makeText(FeedbackActivity.this,"反馈成功",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            Toast.makeText(FeedbackActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                        }

                    }, throwable -> {
                        Log.i(TAG, "getList: error = " + throwable);
                        Toast.makeText(FeedbackActivity.this,throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
