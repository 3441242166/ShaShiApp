package com.shashiwang.shashiapp.activity.message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.message.CarAdapter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.presenter.msg.CarListPresenter;
import com.shashiwang.shashiapp.view.ICarView;

import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

import static com.shashiwang.shashiapp.constant.Constant.ID;

public class CarListActivity extends BaseTopBarActivity<CarListPresenter> implements ICarView {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.sw_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    private CarAdapter adapter;
    private List<CarMessage> list;

    private boolean isLoadMore = false;

    @Override
    protected CarListPresenter setPresenter() {
        return new CarListPresenter(this,this);
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
        setTitle("车辆信息");

        adapter = new CarAdapter(null);
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
            Intent intent = new Intent(CarListActivity.this,CarMessageActivity.class);
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
    public void loadDataSuccess(List<CarMessage> data) {
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