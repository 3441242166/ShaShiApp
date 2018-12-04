package com.shashiwang.shashiapp.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.FeedbackActivity;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.activity.PostListActivity;
import com.shashiwang.shashiapp.activity.SettingActivity;
import com.shashiwang.shashiapp.activity.login.LoginActivity;
import com.shashiwang.shashiapp.adapter.TextAdapter;
import com.shashiwang.shashiapp.base.LazyLoadFragment;
import com.shashiwang.shashiapp.presenter.MyFragmentPresenter;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.IMyFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.shashiwang.shashiapp.constant.Constant.TOKEN;

public class MyFragment extends LazyLoadFragment<MyFragmentPresenter> implements IMyFragmentView{

    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;

    private TextAdapter adapter;

    private static final String[] TITLE = {"我的发布", "推荐有奖", "意见反馈", "发布信息"};
    private static final int[] IMG = {R.mipmap.gv_animation, R.mipmap.gv_multipleltem, R.mipmap.gv_header_and_footer, R.mipmap.gv_pulltorefresh};
    private static final Class[] CLASSES = {PostListActivity.class,MainActivity.class,FeedbackActivity.class, MainActivity.class};

    @Override
    protected MyFragmentPresenter setPresenter() {
        return new MyFragmentPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init() {
        initView();
        initEvent();
    }

    private void initEvent() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            final Intent intent = new Intent(getContext(),CLASSES[position]);
            startActivity(intent);
        });

        ivSetting.setOnClickListener(view -> startActivity(new Intent(getContext(), SettingActivity.class)));
    }

    private void initView() {
        List<TextAdapter.TextBean> list = new ArrayList<>(TITLE.length);
        for(int x=0;x<TITLE.length;x++){
            list.add(new TextAdapter.TextBean(IMG[x],TITLE[x],false));
        }
        adapter = new TextAdapter(list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

    @Override
    public void unLogin() {
        btLogin.setVisibility(View.VISIBLE);
        tvName.setVisibility(View.GONE);

        btLogin.setOnClickListener(view -> startActivity(new Intent(getContext(), LoginActivity.class)));
    }

}
