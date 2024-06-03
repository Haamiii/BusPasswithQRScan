package com.example.buspasswithqrscan.Student.model;

public class BusInfo {

    private String routeNo;
    private String pickuptime;

    public String getRouteNo() {
        return routeNo;
    }

    public void setRouteNo(String routeNo) {
        this.routeNo = routeNo;
    }

    public String getPickuptime() {
        return pickuptime;
    }

    public void setPickuptime(String pickuptime) {
        this.pickuptime = pickuptime;
    }

    public BusInfo() {
        this.routeNo = routeNo;
        this.pickuptime = pickuptime;
    }


}
