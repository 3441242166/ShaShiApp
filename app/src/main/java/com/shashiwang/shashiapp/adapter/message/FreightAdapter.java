package com.shashiwang.shashiapp.adapter.message;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

public class FreightAdapter extends BaseQuickAdapter<FreightMessage,BaseViewHolder> {


    public FreightAdapter(@Nullable List<FreightMessage> data) {
        super(R.layout.item_msg_freight, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FreightMessage item) {
        helper.addOnClickListener(R.id.iv_phone);


    }

}
