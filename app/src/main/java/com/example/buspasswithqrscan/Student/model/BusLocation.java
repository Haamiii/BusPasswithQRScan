package com.example.buspasswithqrscan.Student.model;

public class BusLocation {
    private int BusId;
    private int RouteId;
    private String RouteTitle;

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public String getRouteTitle() {
        return RouteTitle;
    }

    public void setRouteTitle(String routeTitle) {
        RouteTitle = routeTitle;
    }

    private BusLocationCoordinates Cords;

    public int getBusId() {
        return BusId;
    }

    public void setBusId(int busId) {
        BusId = busId;
    }

    public BusLocationCoordinates getCords() {
        return Cords;
    }

    public void setCords(BusLocationCoordinates cords) {
        Cords = cords;
    }
}

