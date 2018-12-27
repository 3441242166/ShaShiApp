package com.shashiwang.shashiapp.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.net.interceptors.TokenInterceptor;
import com.example.net.rx.RxRetrofitClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.message.CarListActivity;
import com.shashiwang.shashiapp.activity.message.CarMessageActivity;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.bean.BaseMessage;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.HttpResult;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.bean.MessageResult;
import com.shashiwang.shashiapp.bean.StationMessage;
import com.shashiwang.shashiapp.constant.MessageType;
import com.shashiwang.shashiapp.presenter.PostListPresenter;
import com.shashiwang.shashiapp.view.PostListView;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.shashiwang.shashiapp.constant.ApiConstant.URL_CAR;
import static com.shashiwang.shashiapp.constant.ApiConstant.URL_PUBLISH;
import static com.shashiwang.shashiapp.constant.Constant.ID;

public class MessageListFragment extends LazyLoadFragment<PostListPresenter> implements PostListView {
    private static final String TAG = "MessageListFragment";

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MessageAdapter adapter;
    private List<BaseMessage> list;

    private int type;

    public static MessageListFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MessageListFragment fragment = new MessageListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected PostListPresenter setPresenter() {
        return new PostListPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        type = getArguments().getInt("type");

        initView();
        initEvent();

        presenter.getList(type);
    }

    private void initView(){
        adapter = new MessageAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        swipeRefreshLayout.setRefreshing(true);
    }

    private void initEvent(){
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.setEnableLoadMore(false);
            presenter.getList(type);
        });

        adapter.setOnItemClickListener((adapter, view, position) -> {
            //Intent intent = new Intent(CarListActivity.this,CarMessageActivity.class);
            //intent.putExtra(ID,list.get(position).getId());
            //startActivity(intent);
        });

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId() == R.id.iv_phone){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + list.get(position).getPhone());
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(List data) {
        list = data;
        adapter.setNewData(data);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void errorMessage(String throwable) {
        Toasty.normal(getContext(),throwable).show();
        swipeRefreshLayout.setRefreshing(false);
    }

}
