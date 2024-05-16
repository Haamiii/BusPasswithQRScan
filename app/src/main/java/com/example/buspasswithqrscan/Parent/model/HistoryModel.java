package com.example.buspasswithqrscan.Parent.model;

import java.io.Serializable;

public class HistoryModel implements Serializable {
    public String getPassid() {
        return passid;
    }

    public void setPassid(String passid) {
        this.passid = passid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStopname() {
        return stopname;
    }

    public void setStopname(String stopname) {
        this.stopname = stopname;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public HistoryModel(String passid, String title, String stopname, String route, String bus, String name) {
        this.passid = passid;
        this.title = title;
        this.stopname = stopname;
        this.route = route;
        this.bus = bus;
        Name = name;
    }

    String passid;
    String title;
    String stopname;
    String route;
    String bus;
    String Name;
}
