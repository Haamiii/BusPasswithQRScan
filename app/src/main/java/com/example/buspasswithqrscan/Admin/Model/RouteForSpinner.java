package com.example.buspasswithqrscan.Admin.Model;

public class RouteForSpinner {
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
    @Override
    public String toString() {
        return RouteTitle; // Return the route title for display in the Spinner
    }

}
