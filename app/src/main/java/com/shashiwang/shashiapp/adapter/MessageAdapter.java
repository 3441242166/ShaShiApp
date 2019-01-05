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
import com.shashiwang.shashiapp.util.DateUtil;
import com.shashiwang.shashiapp.util.StringUtil;

import java.util.List;

import static com.shashiwang.shashiapp.constant.MessageType.*;
import static com.shashiwang.shashiapp.util.TypeUtil.getYearString;

public class MessageAdapter extends BaseMultiItemQuickAdapter<MessageBean,BaseViewHolder> {
    private static final String TAG = "MessageAdapter";

    public MessageAdapter(@Nullable List<MessageBean> data) {
        super(data);

        addItemType(CAR, R.layout.item_msg_car);
        addItemType(FACTORY, R.layout.item_msg_factory);
        addItemType(FREIGHT, R.layout.item_msg_freight);
        addItemType(DRIVER, R.layout.item_msg_driver);
        addItemType(STATION, R.layout.item_msg_station);

        Log.i(TAG, "MessageAdapter");
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageBean item) {
        helper.addOnClickListener(R.id.iv_phone);
        Log.i(TAG, "convert: type = " + item.getItemType());
        Log.i(TAG, "convert: item = "+item);

        if(item.getBean() instanceof  FactoryMessage){
            Log.i(TAG, "convert: FactoryMessage");
        }
        if(item.getBean() instanceof  StationMessage){
            Log.i(TAG, "convert: StationMessage");
        }
        if(item.getBean() instanceof  CarMessage){
            Log.i(TAG, "convert: CarMessage");
        }
        if(item.getBean() instanceof  FreightMessage){
            Log.i(TAG, "convert: FreightMessage");
        }
        if(item.getBean() instanceof  DriverMessage){
            Log.i(TAG, "convert: DriverMessage");
        }

        switch (item.getItemType()){
            case CAR:
                CarMessage carMessage = (CarMessage) item.getBean();
                helper.setText(R.id.tv_title,StringUtil.getFirstChinese(carMessage.getLinkman())+"先生");
                helper.setText(R.id.tv_label,DateUtil.getDifferentString(carMessage.getCreated_at()));

                helper.setText(R.id.tv_name,carMessage.getBrand());
                helper.setText(R.id.tv_price,""+carMessage.getPrice());

                helper.setText(R.id.tv_type,""+carMessage.getFactory_year());
                helper.setText(R.id.tv_kilometre,""+carMessage.getMileage()+"公里");
                helper.setText(R.id.tv_content,carMessage.getRemark());
                break;
            case FREIGHT:
                FreightMessage freightMessage = (FreightMessage) item.getBean();
                helper.setText(R.id.tv_title,StringUtil.getFirstChinese(freightMessage.getLinkman())+"先生");
                helper.setText(R.id.tv_label,DateUtil.getDifferentString(freightMessage.getCreated_at()));

                helper.setText(R.id.tv_name,freightMessage.getCargo_name());
                helper.setText(R.id.tv_price,""+freightMessage.getPrice());

                helper.setText(R.id.tv_start,freightMessage.getStart_location());
                helper.setText(R.id.tv_end,freightMessage.getEnd_location());

                helper.setText(R.id.tv_car,freightMessage.getCar_category());
                helper.setText(R.id.tv_distance,""+freightMessage.getDistance());

                helper.setText(R.id.tv_content,freightMessage.getRemark());
                break;
            case DRIVER:
                DriverMessage driverMessage = (DriverMessage) item.getBean();
                helper.setText(R.id.tv_title,StringUtil.getFirstChinese(driverMessage.getLinkman())+"先生");
                helper.setText(R.id.tv_label,DateUtil.getDifferentString(driverMessage.getCreated_at()));

                helper.setText(R.id.tv_years,getYearString(driverMessage.getWork_year()));
                helper.setText(R.id.tv_salary,""+driverMessage.getSalary());

                helper.setText(R.id.tv_address,driverMessage.getWork_address());

                helper.setText(R.id.tv_content,driverMessage.getJob_desc());
                break;
            case FACTORY:
                FactoryMessage factoryMessage = (FactoryMessage) item.getBean();

                helper.setText(R.id.tv_title,StringUtil.getFirstChinese(factoryMessage.getLinkman())+"先生");
                helper.setText(R.id.tv_label,DateUtil.getDifferentString(factoryMessage.getCreated_at()));

                helper.setText(R.id.tv_price,""+factoryMessage.getCategory_price());

                helper.setText(R.id.tv_address,factoryMessage.getLocation());

                helper.setText(R.id.tv_content,factoryMessage.getRemark());
                break;
            case STATION:
                StationMessage stationMessage = (StationMessage) item.getBean();
                helper.setText(R.id.tv_title,StringUtil.getFirstChinese(stationMessage.getLinkman())+"先生");
                helper.setText(R.id.tv_label,DateUtil.getDifferentString(stationMessage.getCreated_at()));

                helper.setText(R.id.tv_price,""+stationMessage.getCategory_price());

                helper.setText(R.id.tv_address,stationMessage.getLocation());

                helper.setText(R.id.tv_content,stationMessage.getRemark());
                break;
        }
    }



}

