package com.example.buspasswithqrscan.Admin.Model;


import java.util.List;

public class Routes {
    public int RouteId;
    public String RouteTitle;
    public List<Stop> Stops;

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

    public List<Stop> getStops() {
        return Stops;
    }

    public void setStops(List<Stop> stops) {
        Stops = stops;
    }
}
