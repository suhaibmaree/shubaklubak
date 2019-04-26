package edu.mareeaaup.s.shubaklubak.Model;


import java.io.Serializable;

public class Device implements Serializable {

    private String name;
    private Boolean state;


    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
