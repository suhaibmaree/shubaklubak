package edu.mareeaaup.s.shubaklubak.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Moode implements Serializable {

    private Boolean state;
    private String name;
    private String key;
    //private List devices = new ArrayList<Device>();

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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Moode() {
    }

//    public List getDevices() {
//        return devices;
//    }

//    public void setDevices(List devices) {
//        this.devices = devices;
//    }

    public Moode(String key, Boolean state) {
        this.state = state;
        this.key = key;
    }

}

