package com.shashiwang.shashiapp.dialog;


import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.TextAdapter;
import com.shashiwang.shashiapp.base.BaseScreenDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ChooseBottomDialog extends BaseScreenDialog {
    private static final String TAG = "ChooseBottomDialog";

    @BindView(R.id.im_dialog_back)
    ImageView ivBack;
    @BindView(R.id.tx_dialog_title)
    TextView tvTitle;
    @BindView(R.id.rv_dialog_post)
    ListView rvView;

    private String title;
    private int dataID;

    private OnChooseListener onChooseListener;

    public ChooseBottomDialog(Context context,String title,int dataID) {
        super(context);
        this.title = title;
        this.dataID  =dataID;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_bottom_choose;
    }

    private void initEvent() {

        rvView.setOnItemClickListener((adapterView, view, i, l) -> {
            if(onChooseListener != null){
                onChooseListener.onChoose("");
            }
        });

    }

    private void initView() {
        tvTitle.setText(title);

        setCancelable(true);//点击外部不可dismiss
        setCanceledOnTouchOutside(true);
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),dataID);
        rvView.setAdapter(adapter);


    }

    public void setOnChooseListener(OnChooseListener onChooseListener){
        this.onChooseListener = onChooseListener;
    }

    interface OnChooseListener{
        void onChoose(String str);
    }

}
