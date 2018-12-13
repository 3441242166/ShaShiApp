package com.shashiwang.shashiapp.activity.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.MessageListActivity;
import com.shashiwang.shashiapp.adapter.MessageAdapter;
import com.shashiwang.shashiapp.adapter.message.FreightAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.presenter.FreightListPresenter;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.IFreightListView;

import java.util.List;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.ID;
import static com.shashiwang.shashiapp.constant.Constant.TYPE;
import static com.shashiwang.shashiapp.constant.Constant.URL;

public class FreightListActivity extends BaseTopBarActivity<FreightListPresenter> implements IFreightListView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    FreightAdapter adapter;
    List<FreightMessage> list;

    @Override
    protected FreightListPresenter setPresenter() {
        return new FreightListPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_freight_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        adapter = new FreightAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration());
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener((adapter, view, position) -> {

            Intent intent = new Intent(FreightListActivity.this,FreightMessageActivity.class);
            intent.putExtra(ID,list.get(position).getId());
            startActivity(intent);

        });

        presenter.getList();
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
    }

    @Override
    public void errorMessage(String throwable) {

    }
}
