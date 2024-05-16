package com.example.buspasswithqrscan.Parent.model;

public class Childsparent_Model {
    String name,pickcheckinTime,pickcheckoutTime,dropcheckinTime,dropcheckouttime;

    public Childsparent_Model(String name, String pickcheckinTime, String pickcheckoutTime, String dropcheckinTime, String dropcheckouttime) {
        this.name = name;
        this.pickcheckinTime = pickcheckinTime;
        this.pickcheckoutTime = pickcheckoutTime;
        this.dropcheckinTime = dropcheckinTime;
        this.dropcheckouttime = dropcheckouttime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPickcheckinTime() {
        return pickcheckinTime;
    }

    public void setPickcheckinTime(String pickcheckinTime) {
        this.pickcheckinTime = pickcheckinTime;
    }

    public String getPickcheckoutTime() {
        return pickcheckoutTime;
    }

    public void setPickcheckoutTime(String pickcheckoutTime) {
        this.pickcheckoutTime = pickcheckoutTime;
    }

    public String getDropcheckinTime() {
        return dropcheckinTime;
    }

    public void setDropcheckinTime(String dropcheckinTime) {
        this.dropcheckinTime = dropcheckinTime;
    }

    public String getDropcheckouttime() {
        return dropcheckouttime;
    }

    public void setDropcheckouttime(String dropcheckouttime) {
        this.dropcheckouttime = dropcheckouttime;
    }
}
