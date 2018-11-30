package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;

import java.util.List;

public class TextAdapter extends BaseQuickAdapter<TextAdapter.TextBean,BaseViewHolder> {

    private Context context;

    public TextAdapter(@Nullable List<TextBean> data, Context context) {
        super(R.layout.item_text, data);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, TextBean item) {
        helper.setText(R.id.tx_item_contant,item.name);
        //Glide.with(context).load(item.imgID).into((ImageView) helper.getView(R.id.item_grid_image));
    }


    public static class TextBean {
        int imgID;
        String name;

        public TextBean(int imgID,String name){
            this.imgID = imgID;
            this.name = name;
        }

    }

}
