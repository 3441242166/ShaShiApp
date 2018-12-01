package com.shashiwang.shashiapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.TextAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ChooseBottomDialog extends Dialog {

    @BindView(R.id.im_dialog_back)
    ImageView ivBack;
    @BindView(R.id.tx_dialog_title)
    TextView tvTitle;
    @BindView(R.id.rv_dialog_post)
    RecyclerView rvView;

    private Context context;
    private TextAdapter adapter;
    private String title;
    private List<TextAdapter.TextBean> list;

    private OnChooseListener onChooseListener;

    public ChooseBottomDialog(Context context,String title) {
        super(context, R.style.style_select_dialog);
        this.context = context;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_choose);//这行一定要写在前面
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    private void initData() {
        String[] data = context.getResources().getStringArray(R.array.work_year);
        list = new ArrayList<>();

        for(String val:data){
            list.add(new TextAdapter.TextBean(val));
        }

        adapter.setNewData(list);

    }

    private void initEvent() {

        adapter.setOnItemClickListener((adapter, view, position) -> {
            if(onChooseListener != null){
                onChooseListener.onChoose(list.get(position).name);
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

        rvView.setLayoutManager(new LinearLayoutManager(context));

        adapter = new TextAdapter(null,context);
        rvView.setAdapter(adapter);

    }

    public void setOnChooseListener(OnChooseListener onChooseListener){
        this.onChooseListener = onChooseListener;
    }

    interface OnChooseListener{
        void onChoose(String str);
    }

}
