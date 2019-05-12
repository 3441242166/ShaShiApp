package com.shashiwang.shashiapp.bean;

import com.baidu.mapapi.model.LatLng;

import java.util.List;

public class Count {
     public String count;
     public String password;
     public List<String> data;
     public int distance;
     public double lat;
     public double lng;

     public LatLng getLocation(){
          return new LatLng(lat,lng);
     }
     public void saveLoaction(double t,double g){
          lat = t;
          lng = g;
     }
}
