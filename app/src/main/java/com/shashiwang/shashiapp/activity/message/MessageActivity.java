package com.shashiwang.shashiapp.activity.message;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.base.BasePresenter;
import com.shashiwang.shashiapp.base.BaseTopBarActivity;

import butterknife.BindView;

public class MessageActivity extends BaseTopBarActivity {

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_title2)
    TextView tvTitle2;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.bt_conect)
    Button btConect;

    @Override
    protected BasePresenter setPresenter() {
        return null;
    }

    @Override
    protected int getFrameContentView() {
        return R.layout.activity_message;
    }

    @Override
    protected void initFrame(Bundle savedInstanceState) {
        initData(savedInstanceState);
    }

    private void initData(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if(intent == null){
            return;
        }

        String[] titleAr = getResources().getStringArray(intent.getIntExtra("",-1));
        String[] keyAr = getResources().getStringArray(intent.getIntExtra("",-1));



    }
}
