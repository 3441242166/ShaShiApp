package com.shashiwang.shashiapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.util.SharedPreferencesHelper;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.activity.FeedbackActivity;
import com.shashiwang.shashiapp.activity.MainActivity;
import com.shashiwang.shashiapp.activity.MapListActivity;
import com.shashiwang.shashiapp.activity.SettingActivity;
import com.shashiwang.shashiapp.activity.SettingBroadcastActivity;
import com.shashiwang.shashiapp.activity.PostListActivity;
import com.shashiwang.shashiapp.adapter.TextAdapter;
import com.shashiwang.shashiapp.activity.LoginActivity;
import com.shashiwang.shashiapp.base.BaseFragment;
import com.shashiwang.shashiapp.presenter.MyFragmentPresenter;
import com.shashiwang.shashiapp.util.DataUtil;
import com.shashiwang.shashiapp.util.DividerItemDecoration;
import com.shashiwang.shashiapp.view.IMyFragmentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static com.shashiwang.shashiapp.constant.Constant.REQUEST_LOGIN;
import static com.shashiwang.shashiapp.constant.Constant.REQUEST_SETTING;
import static com.shashiwang.shashiapp.constant.Constant.TOKEN;

public class MyFragment extends BaseFragment<MyFragmentPresenter> implements IMyFragmentView{
    private static final String TAG = "MyFragment";


    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.iv_setting)
    ImageView ivSetting;

    private TextAdapter adapter;

    private static final String[] TITLE = {"我的监护人", "我监护的人"};
    private static final int[] IMG = {R.drawable.ic_my_1,R.drawable.ic_my_2,
            R.drawable.ic_my_4, R.drawable.ic_my_6};
    private static final Class[] CLASSES = {PostListActivity.class,
            SettingBroadcastActivity.class};

    @Override
    protected MyFragmentPresenter setPresenter() {
        return new MyFragmentPresenter(this,getContext());
    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_my;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initView();
        initEvent();
    }

    private void initEvent() {
        adapter.setOnItemClickListener((adapter, view, position) -> {
            DataUtil.type = position;
            startActivity(new Intent(getContext(), MapListActivity.class));
        });
        ivSetting.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), SettingActivity.class));
        });
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
    public void unLogin(boolean is, String user) {
        if(is){
            btLogin.setVisibility(View.VISIBLE);
            tvName.setVisibility(View.GONE);
            btLogin.setOnClickListener(view -> startActivityForResult(new Intent(getContext(), LoginActivity.class),REQUEST_LOGIN));
        }else {
            btLogin.setVisibility(View.GONE);
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(user);
            presenter.getUserMessage();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i(TAG, "onActivityResult: requestCode = "+ requestCode + "  resultCode = "+ resultCode);
        presenter.checkLogin();
    }
}
