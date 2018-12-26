package com.shashiwang.shashiapp.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shashiwang.shashiapp.R;
import com.shashiwang.shashiapp.bean.CarMessage;
import com.shashiwang.shashiapp.bean.DriverMessage;
import com.shashiwang.shashiapp.bean.FactoryMessage;
import com.shashiwang.shashiapp.bean.FreightMessage;
import com.shashiwang.shashiapp.bean.MessageBean;
import com.shashiwang.shashiapp.bean.StationMessage;

import java.util.List;

import static com.shashiwang.shashiapp.constant.MessageType.*;

public class MessageAdapter extends BaseMultiItemQuickAdapter<MessageBean,BaseViewHolder> {
    private static final String TAG = "MessageAdapter";

    public MessageAdapter(@Nullable List<MessageBean> data) {
        super(data);

        addItemType(CAR, R.layout.item_msg_car);
        addItemType(FACTORY, R.layout.item_msg_factory);
        addItemType(FREIGHT, R.layout.item_msg_freight);
        addItemType(DRIVER, R.layout.item_msg_driver);
        addItemType(STATION, R.layout.item_msg_station);

    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {

        switch (item.getItemType()){
            case CAR:
                CarMessage carMessage = (CarMessage) item.getBean();

                break;
            case FREIGHT:
                FreightMessage freightMessage = (FreightMessage) item.getBean();

                break;
            case DRIVER:
                DriverMessage driverMessage = (DriverMessage) item.getBean();

                break;
            case FACTORY:
                FactoryMessage factoryMessage = (FactoryMessage) item.getBean();

                break;
            case STATION:
                StationMessage stationMessage = (StationMessage) item.getBean();

                break;
        }
    }



}

