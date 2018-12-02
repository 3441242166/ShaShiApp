package com.shashiwang.shashiapp.dialog;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
    RecyclerView rvView;

    private TextAdapter adapter;
    private String title;
    private List<TextAdapter.TextBean> list;

    private OnChooseListener onChooseListener;

    public ChooseBottomDialog(Context context,String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected void init() {
        ButterKnife.bind(this);
        initView();
        initData();
        initEvent();
    }

    @Override
    protected int getContentView() {
        return R.layout.dialog_bottom_choose;
    }

    private void initData() {
        String[] data = getContext().getResources().getStringArray(R.array.work_year);
        Log.i(TAG, "initData: data.size = "+data.length);
        list = new ArrayList<>();

        for(String val : data){
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

        rvView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TextAdapter(null,getContext());
        rvView.setAdapter(adapter);
    }

    public void setOnChooseListener(OnChooseListener onChooseListener){
        this.onChooseListener = onChooseListener;
    }

    interface OnChooseListener{
        void onChoose(String str);
    }

}
