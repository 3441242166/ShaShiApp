package com.shashiwang.shashiapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.presenter.PostListPresenter;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.PostListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.CLASS;
import static com.shashiwang.shashiapp.constant.Constant.TITLE;
import static com.shashiwang.shashiapp.constant.Constant.TYPE;
import static com.shashiwang.shashiapp.constant.Constant.URL;
import static com.shashiwang.shashiapp.constant.MessageType.FACTORY;
import static com.shashiwang.shashiapp.constant.MessageType.*;

public class MessageListActivity extends BaseTopBarActivity<PostListPresenter> implements PostListView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private MessageAdapter adapter;
    private Class aClass;

    private int type;
    private String url;

    @Override
    protected PostListPresenter setPresenter() {
        return new PostListPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_message_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initData();
        initView();
        initEvent();
        presenter.getList(url,type);
    }

    private void initData() {
        type = getIntent().getIntExtra(TYPE,-1);
        url = getIntent().getStringExtra(URL);
        aClass = (Class) getIntent().getSerializableExtra(CLASS);
        setTitle(getIntent().getStringExtra(TITLE));

    }

    private void initEvent() {
        adapter.setOnItemClickListener((adapter, view, position) -> {

            Intent intent = new Intent(MessageListActivity.this,aClass);
            intent.putExtra(TYPE,type);
            intent.putExtra(URL,url+"1/");

            startActivity(intent);

        });
    }

    private void initView() {
        adapter = new MessageAdapter(null);
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
    public void loadDataSuccess(List data) {
        adapter.setNewData(getAdapterData(data));
    }

    @Override
    public void errorMessage(String throwable) {

    }

    private List<MessageBean> getAdapterData(List data){
        List<MessageBean> list = new ArrayList<>(data.size());

        for(int x=0;x<data.size();x++){
            switch (type){
                case POST:
                    list.add(new MessageBean<>(type,data.get(x)));
                    break;
                case CAR:
                    list.add(new MessageBean<>(type,data.get(x)));
                    break;
                case FREIGHT:
                    list.add(new MessageBean<>(type, (FreightMessage) data.get(x)));
                    break;
                case DRIVER:
                    list.add(new MessageBean<>(type,data.get(x)));
                    break;
                case FACTORY:
                    list.add(new MessageBean<>(type,data.get(x)));
                    break;
                case STATION:
                    list.add(new MessageBean<>(type,data.get(x)));
                    break;
            }
            Log.i("666666", "getAdapterData: " +((FreightMessage) list.get(x).getBean()).getCargo_name());
        }

        return list;
    }
}
