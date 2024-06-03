package com.example.buspasswithqrscan.Parent.model;

import java.io.Serializable;

public class HistoryModelParent implements Serializable {
    private String studentName;
    private String title;
    private String passId;
    private String stopName;
    private String route;
    private String bus;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
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

    public HistoryModelParent(String studentName, String title, String passId, String stopName, String route, String bus) {
        this.studentName = studentName;
        this.title = title;
        this.passId = passId;
        this.stopName = stopName;
        this.route = route;
        this.bus = bus;
    }
}
