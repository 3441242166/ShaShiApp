package com.shashiwang.shashiapp.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.bean.BaseMessage;
import com.shashiwang.shashiapp.presenter.PostListPresenter;
import com.shashiwang.shashiapp.view.IPostListView;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MessageListFragmentI extends LazyLoadFragment<PostListPresenter> implements IPostListView {
    private static final String TAG = "MessageListFragmentI";

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MessageAdapter adapter;
    private List<BaseMessage> list;

    private int type;

    public static MessageListFragmentI newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        MessageListFragmentI fragment = new MessageListFragmentI();
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
