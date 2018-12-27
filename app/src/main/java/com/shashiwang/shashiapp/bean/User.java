package com.shashiwang.shashiapp.bean;

public class User {

    int id;
    String jpush_reg_id;
    String phone;
    String role;
    String push_category;
    int is_voice;
    String created_at;
    String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJpush_reg_id() {
        return jpush_reg_id;
    }

    public void setJpush_reg_id(String jpush_reg_id) {
        this.jpush_reg_id = jpush_reg_id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPush_category() {
        return push_category;
    }

    public void setPush_category(String push_category) {
        this.push_category = push_category;
    }

    public int getIs_voice() {
        return is_voice;
    }

    public void setIs_voice(int is_voice) {
        this.is_voice = is_voice;
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
}
