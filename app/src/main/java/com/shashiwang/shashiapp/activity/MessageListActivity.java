package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.message.CarMessageActivity;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.adapter.PostAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.presenter.PostListPresenter;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.PostListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.TITLE;
import static com.shashiwang.shashiapp.constant.Constant.TYPE;
import static com.shashiwang.shashiapp.constant.MessageType.FACTORY;

public class MessageListActivity extends BaseTopBarActivity<PostListPresenter> implements PostListView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private MessageAdapter adapter;
    private List<MessageBean> list;
    private Class aClass;

    private int type = -1;

    @Override
    protected PostListPresenter setPresenter() {
        return new PostListPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_post_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initData();
        initView();
        initEvent();
    }

    private void initData() {
        type = getIntent().getIntExtra(TYPE,-1);
        String title = getIntent().getStringExtra(TITLE);
        aClass = (Class) getIntent().getSerializableExtra("class");
        setTitle(title);

        if(type == -1){
            finish();
        }

        list = new ArrayList<>();

        for(int x=0;x<20;x++){
            list.add(new MessageBean(type));
        }

    }

    private void initEvent() {
        adapter.setOnItemClickListener((adapter, view, position) -> {

            Intent intent = new Intent(MessageListActivity.this,aClass);
            intent.putExtra(TYPE,type);
            startActivity(intent);

        });
    }

    private void initView() {
        adapter = new MessageAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismissProgress() {

    }

    @Override
    public void loadDataSuccess(Object data) {

    }

    @Override
    public void errorMessage(String throwable) {

    }
}
