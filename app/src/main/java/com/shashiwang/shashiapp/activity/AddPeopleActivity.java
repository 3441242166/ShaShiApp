package com.shashiwang.shashiapp.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.net.rx.RxRetrofitClient;
import com.example.util.SharedPreferencesHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.constant.Constant;
import com.shashiwang.shashiapp.util.DataUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class AddPeopleActivity extends BaseTopBarActivity {



    @Override
    protected int getFrameContentView() {
        return R.layout.activity_add_people;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        Button add = findViewById(R.id.ac_choose_add);
        EditText editText = findViewById(R.id.ac_choose_ed);
        setTitle("添加");
        add.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals(DataUtil.nowCount.count)){
                    Toast.makeText(AddPeopleActivity.this,"不能添加自己",Toast.LENGTH_SHORT).show();
                    return;
                }
                RxRetrofitClient.builder()
                        .url("addParent")
                        .params("count",DataUtil.nowCount.count)
                        .params("parent",editText.getText().toString())
                        .build()
                        .get()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(s -> {
                                    HttpResult<Object> result = new Gson().fromJson(s,new TypeToken<HttpResult<Object>>(){}.getType());

                                    if(result.isSuccess()){
                                        Toast.makeText(AddPeopleActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                                        finish();
                                    }else {
                                        Toast.makeText(AddPeopleActivity.this,result.getMessage(),Toast.LENGTH_SHORT).show();
                                    }

                                }
                                , throwable -> {
                                    Toast.makeText(AddPeopleActivity.this,throwable.toString(),Toast.LENGTH_SHORT).show();
                                });
            }
        });
    }

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }
}
