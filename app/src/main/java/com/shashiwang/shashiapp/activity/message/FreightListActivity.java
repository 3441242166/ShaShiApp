package com.shashiwang.shashiapp.activity.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.message.FreightAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.presenter.msg.FreightListPresenter;
import com.shashiwang.shashiapp.view.IFreightListView;

import java.util.List;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.ID;

public class FreightListActivity extends BaseTopBarActivity<FreightListPresenter> implements IFreightListView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private FreightAdapter adapter;
    private List<FreightMessage> list;

    @Override
    protected FreightListPresenter setPresenter() {
        return new FreightListPresenter(this,this);
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
        setTitle("运费信息");

        adapter = new FreightAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void initEvent() {
        adapter.setOnLoadMoreListener(() -> {
            recyclerView.postDelayed(() -> presenter.getList(), 500);
        },recyclerView);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(FreightListActivity.this,FreightMessageActivity.class);
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
    public void loadDataSuccess(List<FreightMessage> data) {
        list = data;
        adapter.setNewData(data);
        adapter.loadMoreEnd();
    }

    @Override
    public void errorMessage(String throwable) {

    }
}
