package com.example.buspasswithqrscan.Student.model;

public class StopModel {
    String stopName,stopTime;
    int routeNo;

    public StopModel(String stopName, String stopTime, int routeNo) {
        this.stopName = stopName;
        this.stopTime = stopTime;
        this.routeNo = routeNo;
    }

    public StopModel() {
    }

    public String getStopName() {
        return stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    public String getStopTime() {
        return stopTime;
    }

    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }

    public int getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(int routeNo) {
        this.routeNo = routeNo;
    }
}
