package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.message.CarMessageActivity;
import com.shashiwang.shashiapp.activity.message.DriverMessageActivity;
import com.shashiwang.shashiapp.activity.message.FactoryMessageActivity;
import com.shashiwang.shashiapp.activity.message.FreightMessageActivity;
import com.shashiwang.shashiapp.activity.message.StationMessageActivity;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.constant.MessageType;
import com.shashiwang.shashiapp.presenter.MessageListPresenter;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.IMessageListView;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static com.shashiwang.shashiapp.constant.Constant.ID;
import static com.shashiwang.shashiapp.constant.Constant.TITLE;
import static com.shashiwang.shashiapp.constant.Constant.TYPE;

public class MessageListActivity extends BaseTopBarActivity<MessageListPresenter> implements IMessageListView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MessageAdapter adapter;
    private List<MessageBean> list;

    private boolean isLoadMore = false;
    private int type;
    private String title;

    @Override
    protected MessageListPresenter setPresenter() {
        return new MessageListPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initData();
        initView();
        initEvent();
        presenter.getList(type,true);
    }

    private void initData() {
        type = getIntent().getIntExtra(TYPE,-1);
        title = getIntent().getStringExtra(TITLE);
    }

    private void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.setEnableLoadMore(false);
            presenter.getList(type,true);
        });

        adapter.setOnLoadMoreListener(() -> {
            isLoadMore = true;
            recyclerView.postDelayed(() -> presenter.getList(type,false), 500);
        },recyclerView);

        adapter.setOnItemClickListener((adapter, view, position) -> {

            startActivity(position);
        });

        adapter.setOnItemChildClickListener((adapter, view, position) -> {
            if(view.getId() == R.id.iv_phone){
                Intent intent = new Intent(Intent.ACTION_DIAL);
                Uri data = Uri.parse("tel:" + list.get(position).getBean().getPhone());
                intent.setData(data);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        setTitle(title);

        adapter = new MessageAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration());
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        recyclerView.setAdapter(adapter);
    }

    private void startActivity(int position){
        Intent intent = new Intent();

        switch (type){
            case MessageType.FACTORY:
                intent = new Intent(this,FactoryMessageActivity.class);
                break;
            case MessageType.STATION:
                intent = new Intent(this,StationMessageActivity.class);
                break;
            case MessageType.CAR:
                intent = new Intent(this,CarMessageActivity.class);
                break;
            case MessageType.FREIGHT:
                intent = new Intent(this,FreightMessageActivity.class);
                break;
            case MessageType.DRIVER:
                intent = new Intent(this,DriverMessageActivity.class);
                break;
        }

        intent.putExtra(ID,list.get(position).getBean().getId());
        startActivity(intent);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(List<MessageBean> data) {
        if(isLoadMore){
            if(data.size() == 0){
                Toasty.normal(this,"没有更多数据了").show();
                adapter.loadMoreEnd(true);
            }
            adapter.addData(data);
            adapter.loadMoreComplete();
            isLoadMore = false;
        }else {
            list = data;
            adapter.setNewData(data);
            swipeRefreshLayout.setRefreshing(false);
            adapter.setEnableLoadMore(true);
        }
    }

    @Override
    public void errorMessage(String throwable) {

    }

}
