package com.shashiwang.shashiapp.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.MessageBean;

import java.util.List;

import static com.shashiwang.shashiapp.constant.MessageType.*;

public class MessageAdapter extends BaseMultiItemQuickAdapter<MessageBean,BaseViewHolder> {


    public MessageAdapter(@Nullable List<MessageBean> data) {
        super(data);

        addItemType(POST, R.layout.item_msg_post);
        addItemType(CAR, R.layout.item_msg_car);
        addItemType(FACTORY, R.layout.item_msg_factory);
        addItemType(FREIGHT, R.layout.item_msg_freight);
        addItemType(DRIVER, R.layout.item_msg_driver);
        addItemType(STATION, R.layout.item_msg_station);

    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {

    }



}

