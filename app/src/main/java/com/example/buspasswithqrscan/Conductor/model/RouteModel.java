package com.example.buspasswithqrscan.Conductor.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RouteModel {

    @SerializedName("RouteId")
    private int routeId;
    @SerializedName("RouteTitle")
    private String routeTitle;
    @SerializedName("Stops")
    private List<StopModel> stops;

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

    public List<StopModel> getStops() {
        return stops;
    }

    public void setStops(List<StopModel> stops) {
        this.stops = stops;
    }
}
