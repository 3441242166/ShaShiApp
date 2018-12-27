package com.shashiwang.shashiapp.adapter.message;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.StationMessage;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

public class StationAdapter extends BaseQuickAdapter<StationMessage,BaseViewHolder> {


    public StationAdapter(@Nullable List<StationMessage> data) {
        super(R.layout.item_msg_station, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StationMessage item) {
        helper.addOnClickListener(R.id.iv_phone);


    }

}