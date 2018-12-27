package com.shashiwang.shashiapp.adapter.message;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

public class CarAdapter extends BaseQuickAdapter<CarMessage,BaseViewHolder> {


    public CarAdapter(@Nullable List<CarMessage> data) {
        super(R.layout.item_msg_car, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CarMessage item) {


    }

}
