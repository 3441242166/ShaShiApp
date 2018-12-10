package com.shashiwang.shashiapp.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.PostAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.presenter.PostListPresenter;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.PostListView;

import butterknife.BindView;

public class PostListActivity extends BaseTopBarActivity<PostListPresenter> implements PostListView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private PostAdapter adapter;

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
        initView();
        initEvent();
    }

    private void initEvent() {

    }

    private void initView() {
        adapter = new PostAdapter(null,this);
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
