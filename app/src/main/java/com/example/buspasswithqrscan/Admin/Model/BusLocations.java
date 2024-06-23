package com.example.buspasswithqrscan.Admin.Model;

import com.example.buspasswithqrscan.Parent.model.ChildrenLocation;

public class BusLocations {
    private int busId;
    private int routeId;
    private String routeTitle;
    private ChildrenLocation.Location cords;

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public String getRouteTitle() {
        return routeTitle;
    }

    public void setRouteTitle(String routeTitle) {
        this.routeTitle = routeTitle;
    }

    public ChildrenLocation.Location getCords() {
        return cords;
    }

    public void setCords(ChildrenLocation.Location cords) {
        this.cords = cords;
    }

    public BusLocations(int busId, int routeId, String routeTitle, ChildrenLocation.Location cords) {
        this.busId = busId;
        this.routeId = routeId;
        this.routeTitle = routeTitle;
        this.cords = cords;
    }
}
