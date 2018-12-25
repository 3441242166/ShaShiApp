package com.shashiwang.shashiapp.activity.message;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.message.DriverAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.presenter.msg.DriverListPresenter;
import com.shashiwang.shashiapp.view.IDriverView;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static com.shashiwang.shashiapp.constant.Constant.ID;

public class DriverListActivity extends BaseTopBarActivity<DriverListPresenter> implements IDriverView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private DriverAdapter adapter;
    private List<DriverMessage> list;

    private boolean isLoadMore = false;

    @Override
    protected DriverListPresenter setPresenter() {
        return new DriverListPresenter(this,this);
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_list;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initView();
        initEvent();
        presenter.getList(true);
    }

    private void initView() {
        setTitle("司机信息");

        adapter = new DriverAdapter(null);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        swipeRefreshLayout.setRefreshing(true);

    }

    private void initEvent() {
        swipeRefreshLayout.setOnRefreshListener(() -> {
            adapter.setEnableLoadMore(false);
            presenter.getList(true);
        });

        adapter.setOnLoadMoreListener(() -> {
            isLoadMore = true;
            recyclerView.postDelayed(() -> presenter.getList(false), 500);
        },recyclerView);

        adapter.setOnItemClickListener((adapter, view, position) -> {
            Intent intent = new Intent(DriverListActivity.this,DriverMessageActivity.class);
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
    public void loadDataSuccess(List<DriverMessage> data) {
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