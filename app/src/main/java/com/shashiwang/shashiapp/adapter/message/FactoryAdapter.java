package com.shashiwang.shashiapp.adapter.message;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

public class FactoryAdapter extends BaseQuickAdapter<FactoryMessage,BaseViewHolder> {


    public FactoryAdapter(@Nullable List<FactoryMessage> data) {
        super(R.layout.item_msg_factory, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FactoryMessage item) {
        helper.addOnClickListener(R.id.iv_phone);

    }

}