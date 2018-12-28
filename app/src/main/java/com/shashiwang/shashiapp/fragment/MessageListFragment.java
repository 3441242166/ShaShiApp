package com.shashiwang.shashiapp.fragment;

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
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.constant.MessageType;
import com.shashiwang.shashiapp.presenter.PostListPresenter;
import com.shashiwang.shashiapp.view.IPostListView;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static com.shashiwang.shashiapp.constant.Constant.ID;

public class MessageListFragment extends BaseFragment<PostListPresenter> implements IPostListView {
    private static final String TAG = "MessageListFragment";

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MessageAdapter adapter;
    private List<MessageBean> list;

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

    private void startActivity(int position){
        Intent intent = new Intent();

        switch (type){
            case MessageType.FACTORY:
                intent = new Intent(getContext(),FactoryMessageActivity.class);
                break;
            case MessageType.STATION:
                intent = new Intent(getContext(),StationMessageActivity.class);
                break;
            case MessageType.CAR:
                intent = new Intent(getContext(),CarMessageActivity.class);
                break;
            case MessageType.FREIGHT:
                intent = new Intent(getContext(),FreightMessageActivity.class);
                break;
            case MessageType.DRIVER:
                intent = new Intent(getContext(),DriverMessageActivity.class);
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
