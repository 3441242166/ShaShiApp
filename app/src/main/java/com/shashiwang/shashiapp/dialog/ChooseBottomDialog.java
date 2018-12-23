package com.shashiwang.shashiapp.dialog;


import android.content.Context;
import android.support.annotation.CheckResult;
import android.support.annotation.Nullable;
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

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.adapter.GridAdapter;
import com.shashiwang.shashiapp.adapter.TextAdapter;
import com.shashiwang.shashiapp.base.BaseScreenDialog;
import com.shashiwang.shashiapp.util.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private String title;
    List<String> data;

    private OnChooseListener onChooseListener;

    public ChooseBottomDialog(Context context,String title,List<String> data) {
        super(context);
        this.title = title;
        this.data = data;
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

        ivBack.setOnClickListener(view -> ChooseBottomDialog.this.cancel());
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

        TextAdapter adapter = new TextAdapter(data);
        rvView.setAdapter(adapter);
        rvView.addItemDecoration(new DividerItemDecoration());
        rvView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setOnItemClickListener((adapter1, view, position) -> {
            Log.i(TAG, "click: ");
            if(onChooseListener != null){
                onChooseListener.onChoose(data.get(position),position);
            }
            ChooseBottomDialog.this.cancel();
        });
    }

    public void setOnChooseListener(OnChooseListener onChooseListener){
        this.onChooseListener = onChooseListener;
    }

    public interface OnChooseListener{
        void onChoose(String str,int pos);
    }

     static class  TextAdapter extends BaseQuickAdapter<String,BaseViewHolder>{


        TextAdapter(@Nullable List<String> data) {
            super(R.layout.item_text, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            Log.i(TAG, "convert: item = "+item);
            helper.setText(R.id.tv,item);
        }
    }


}
