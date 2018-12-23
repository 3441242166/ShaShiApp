package com.shashiwang.shashiapp.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;

import java.util.List;

public class TextAdapter extends BaseQuickAdapter<TextAdapter.TextBean,BaseViewHolder> {


    public TextAdapter(@Nullable List<TextBean> data) {
        super(R.layout.item_image_text, data);

    }

    @Override
    protected void convert(BaseViewHolder helper, TextBean item) {
        helper.setText(R.id.tx_item_contant,item.name);

        if(item.imgID != -1){
            final ImageView imageView = helper.getView(R.id.im_left);
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(item.imgID).into(imageView);
        }

        if(item.isRightShow){
            final ImageView imageView = helper.getView(R.id.im_right);
            imageView.setVisibility(View.VISIBLE);
        }
    }


    public static class TextBean {
        int imgID;
        public String name;
        boolean isRightShow;

        public TextBean(String name){
            this.imgID = -1;
            this.name = name;
            this.isRightShow = false;
        }

        public TextBean(int imgID,String name,boolean isRightShow){
            this.imgID = imgID;
            this.name = name;
            this.isRightShow = isRightShow;
        }

    }

}
