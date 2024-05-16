package com.example.buspasswithqrscan.Conductor.model;

import java.io.Serializable;

public class HistoryModel implements Serializable {
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getStudentscan()
    {
        return studentscan;
    }

    public void setStudentscan(String studentscan)
    {
        this.studentscan = studentscan;
    }

    public HistoryModel(String title, String stopname, String time, String date, String route, String studentscan) {
        this.title = title;
        this.stopname = stopname;
        this.time = time;
        this.date = date;
        this.route = route;
        this.studentscan = studentscan;
    }

    String title;
    String stopname;
    String time;
    String date;
    String route;
    String studentscan;



}
