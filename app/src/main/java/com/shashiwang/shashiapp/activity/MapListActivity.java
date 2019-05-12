package com.shashiwang.shashiapp.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.MapAdapter;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.Count;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.util.DataUtil;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.baidu.mapapi.BMapManager.getContext;
import static com.shashiwang.shashiapp.util.DataUtil.nowCount;

public class MapListActivity extends BaseTopBarActivity {

    RecyclerView recyclerView;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_map_list;
    }

    @SuppressLint("CheckResult")
    @Override
    protected void initFrame(Bundle savedInstanceState) {
        recyclerView = findViewById(R.id.list);
        MapAdapter adapter = new MapAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if(DataUtil.type == 0){
            setTitle("我的监护人");
            RxRetrofitClient.builder()
                    .url("myList")
                    .params("count",DataUtil.nowCount.count)
                    .build()
                    .get()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                                HttpResult<List<Count>> result =
                                        new Gson().fromJson(s,new TypeToken<HttpResult<List<Count>>>(){}.getType());
                                if(result.isSuccess()){
                                    List<Count> data = result.getData();
                                    adapter.setNewData(data);
                                }

                            });
        }else {
            setTitle("我监护的人");
            RxRetrofitClient.builder()
                    .url("getPosition")
                    .params("count",nowCount.count)
                    .build()
                    .get()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                                HttpResult<List<Count>> result =
                                        new Gson().fromJson(s,new TypeToken<HttpResult<List<Count>>>(){}.getType());
                                if(result.isSuccess()){
                                    List<Count> data = result.getData();
                                    adapter.setNewData(data);
                                }

                            });
        }



    }
}
