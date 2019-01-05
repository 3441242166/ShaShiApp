package com.shashiwang.shashiapp.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseMessage implements Parcelable {
    private int id;
    private String phone;

    public BaseMessage(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.phone);
    }


    protected BaseMessage(Parcel in) {
        id = in.readInt();
        phone = in.readString();
    }

    public static final Creator<BaseMessage> CREATOR = new Creator<BaseMessage>() {
        @Override
        public BaseMessage createFromParcel(Parcel in) {
            return new BaseMessage(in);
        }

        @Override
        public BaseMessage[] newArray(int size) {
            return new BaseMessage[size];
        }
    };
}
