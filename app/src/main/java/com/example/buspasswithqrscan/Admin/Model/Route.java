package com.example.buspasswithqrscan.Admin.Model;

public class Route {
    private String routeTitle;
    private int organizationId;

    public String getRouteTitle() {
        return routeTitle;
    }

    public void setRouteTitle(String routeTitle) {
        this.routeTitle = routeTitle;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    public String getStops() {
        return stops;
    }

    public void setStops(String stops) {
        this.stops = stops;
    }

    public Route(String routeTitle, int organizationId, String stops) {
        this.routeTitle = routeTitle;
        this.organizationId = organizationId;
        this.stops = stops;
    }

    private String stops;
}
