package edu.mareeaaup.s.shubaklubak.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Device implements Serializable {

    String name;
    Boolean state;

    public Device() {
    }

    public Device(String name, Boolean state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
}