package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.shashiwang.shashiapp.R;

import java.util.ArrayList;
import java.util.List;

import static com.shashiwang.shashiapp.adapter.PhotoAdapter.PhotoBean.ADD_PHOTO;
import static com.shashiwang.shashiapp.adapter.PhotoAdapter.PhotoBean.PHOTO;
import static com.shashiwang.shashiapp.constant.MessageType.CAR;
import static com.shashiwang.shashiapp.constant.MessageType.FACTORY;

public class PhotoAdapter extends BaseMultiItemQuickAdapter<PhotoAdapter.PhotoBean,BaseViewHolder> {

    private boolean isShow = true;

    public PhotoAdapter(@Nullable List<PhotoBean> data,boolean isShow) {
        super(data);
        this.isShow = isShow;
        addItemType(PHOTO, R.layout.item_photo);
        addItemType(ADD_PHOTO, R.layout.layout_add);
    }

    @Override
    protected void convert(BaseViewHolder helper, PhotoBean item) {
        if(!isShow){
            helper.getView(R.id.iv_delete).setVisibility(View.GONE);
        }

        switch (item.getItemType()){
            case PHOTO:
                helper.addOnClickListener(R.id.iv_delete);
                Glide.with(mContext).load(item.url).into((ImageView) helper.getView(R.id.iv));
                break;
            case ADD_PHOTO:
                helper.addOnClickListener(R.id.iv_add);
                break;
        }

    }

    public static class PhotoBean implements MultiItemEntity {
        public static final int PHOTO = 0;
        public static final int ADD_PHOTO = 1;

        public String url;
        public int type;

        public PhotoBean(String url){
            this.url = url;
            this.type = PHOTO;
        }

        public PhotoBean(String url, int type){
            this.url = url;
            this.type = type;
        }

        @Override
        public int getItemType() {
            return type;
        }
    }

}
