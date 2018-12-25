package com.shashiwang.shashiapp.activity.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.message.CarAdapter;
import com.shashiwang.shashiapp.adapter.message.FactoryAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.presenter.msg.CarListPresenter;
import com.shashiwang.shashiapp.presenter.msg.FactoryListPresenter;
import com.shashiwang.shashiapp.view.ICarView;
import com.shashiwang.shashiapp.view.IFactoryView;

import java.util.List;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.ID;

public class FactoryListActivity extends BaseTopBarActivity<FactoryListPresenter> implements IFactoryView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private FactoryAdapter adapter;
    private List<FactoryMessage> list;

    @Override
    protected FactoryListPresenter setPresenter() {
        return new FactoryListPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initView();
        initEvent();
        presenter.getList();
    }

    private void initView() {
        setTitle("砂石料厂信息");

        adapter = new FactoryAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initEvent() {
        adapter.setOnLoadMoreListener(() -> {
            recyclerView.postDelayed(() -> presenter.getList(), 500);
        },recyclerView);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(FactoryListActivity.this,FactoryMessageActivity.class);
            intent.putExtra(ID,list.get(position).getId());
            startActivity(intent);

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
    public void loadDataSuccess(List<FactoryMessage> data) {
        list = data;
        adapter.setNewData(data);
        adapter.loadMoreEnd();
    }

    @Override
    public void errorMessage(String throwable) {

    }

}