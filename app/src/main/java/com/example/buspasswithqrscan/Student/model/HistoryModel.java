package com.example.buspasswithqrscan.Student.model;

import java.io.Serializable;

public class HistoryModel implements Serializable {
    String passid;
    String title;
    String stopname;
    String date;
    String time,route,bus;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = String.valueOf(route);
    }

    public String getBus() {
        return bus;
    }

    public void setBus(int bus) {
        this.bus = String.valueOf(bus);
    }

    public HistoryModel(String passid, String title, String stopname, String date, String time, String route, String bus) {
        this.passid = passid;
        this.title = title;
        this.stopname = stopname;
        this.date = date;
        this.time = time;
        this.route = route;
        this.bus = bus;
    }
}
