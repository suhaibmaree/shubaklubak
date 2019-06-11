package edu.mareeaaup.s.shubaklubak.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Moode implements Serializable {

    private Boolean state;
    private String name;
    private String key;
    private  boolean expanded;
    private List<Device> devices = new ArrayList<>();
    //private Map<String, Device> devices;

    public Moode() {
    }

    public Moode( List<Device> devices, String key, Boolean state) {
        this.devices = devices;
        this.state = state;
        this.key = key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

