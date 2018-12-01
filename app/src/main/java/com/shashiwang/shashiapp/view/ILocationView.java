package com.shashiwang.shashiapp.view;

import com.baidu.location.BDLocation;
import com.shashiwang.shashiapp.base.IBaseView;

public interface ILocationView extends IBaseView {
    void setMapLocation(BDLocation location);
}
