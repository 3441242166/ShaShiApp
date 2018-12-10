package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;

import java.util.List;

public class PostAdapter extends BaseQuickAdapter<PostAdapter.GridBean,BaseViewHolder> {

    private Context context;

    public PostAdapter(@Nullable List<GridBean> data, Context context) {
        super(R.layout.item_grid, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, GridBean item) {
        
    }

}
