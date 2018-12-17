package com.shashiwang.shashiapp.bean;

public class FreightMessage {

    private int id;
    private int user_id;
    private String start_location_lat;
    private String start_location_lng;
    private String end_location_lat;
    private String end_location_lng;
    private String start_location;
    private String end_location;
    private int distance;
    private String cargo_name;
    private int price;
    private String car_category;
    private String remark;
    private String created_at;
    private String updated_at;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getStart_location_lat() {
        return start_location_lat;
    }

    public void setStart_location_lat(String start_location_lat) {
        this.start_location_lat = start_location_lat;
    }

    public String getStart_location_lng() {
        return start_location_lng;
    }

    public void setStart_location_lng(String start_location_lng) {
        this.start_location_lng = start_location_lng;
    }

    public String getEnd_location_lat() {
        return end_location_lat;
    }

    public void setEnd_location_lat(String end_location_lat) {
        this.end_location_lat = end_location_lat;
    }

    public String getEnd_location_lng() {
        return end_location_lng;
    }

    public void setEnd_location_lng(String end_location_lng) {
        this.end_location_lng = end_location_lng;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getCargo_name() {
        return cargo_name;
    }

    public void setCargo_name(String cargo_name) {
        this.cargo_name = cargo_name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCar_category() {
        return car_category;
    }

    public void setCar_category(String car_category) {
        this.car_category = car_category;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStart_location() {
        return start_location;
    }

    public void setStart_location(String start_location) {
        this.start_location = start_location;
    }

    public String getEnd_location() {
        return end_location;
    }

    public void setEnd_location(String end_location) {
        this.end_location = end_location;
    }
}
