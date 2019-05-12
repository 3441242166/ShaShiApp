package com.shashiwang.shashiapp.fragment;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.shashiwang.shashiapp.base.IBaseView;
import com.shashiwang.shashiapp.bean.Count;

import java.util.List;

public interface IMapView extends IBaseView<List<Count>> {
    void getSuggestionCity(List<SuggestionResult.SuggestionInfo> data);

    void setMapLocation(BDLocation location);
}
