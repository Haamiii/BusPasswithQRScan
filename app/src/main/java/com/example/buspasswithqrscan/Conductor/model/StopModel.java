package com.example.buspasswithqrscan.Conductor.model;

import com.google.gson.annotations.SerializedName;

public class StopModel {
    private int Id;
    private String Name;
    private String Timing;
    private double Latitude;
    private double Longitude;
    @SerializedName("Route")
    private int route;

    // Getters and setters
    public int getId() { return Id; }
    public void setId(int id) { Id = id; }

    public String getName() { return Name; }
    public void setName(String name) { Name = name; }

    public String getTiming() { return Timing; }
    public void setTiming(String timing) { Timing = timing; }

    public double getLatitude() { return Latitude; }
    public void setLatitude(double latitude) { Latitude = latitude; }

    public double getLongitude() { return Longitude; }
    public void setLongitude(double longitude) { Longitude = longitude; }
    public int getRoute() {
        return route;
    }

    public void setRoute(int route) {
        this.route = route;
    }

}
