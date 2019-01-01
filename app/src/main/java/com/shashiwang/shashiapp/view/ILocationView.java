package com.shashiwang.shashiapp.view;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.shashiwang.shashiapp.base.IBaseView;

import java.util.List;

public interface ILocationView extends IBaseView<List<PoiInfo>> {
    void setMapLocation(BDLocation location);

    void getSuggestionCity(List<SuggestionResult.SuggestionInfo> data);
}
