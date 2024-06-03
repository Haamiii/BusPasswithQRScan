package com.example.buspasswithqrscan.Student.model;

import com.google.gson.annotations.SerializedName;

public class StopModel {
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Timing")
    private String timing;
    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longitude;
    @SerializedName("Route")
    private int route;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public String getLatitude() {
        return String.valueOf(latitude);
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return String.valueOf(longitude);
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

    public StopModel(int id, String name, String timing, double latitude, double longitude, int route) {
        this.id = id;
        this.name = name;
        this.timing = timing;
        this.latitude = latitude;
        this.longitude = longitude;
        this.route = route;
    }
}