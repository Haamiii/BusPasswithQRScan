package com.example.buspasswithqrscan.Admin.Model;

public class ApiStops {
    private int Id;
    private String Name;
    private String Timing;
    private double Latitude;
    private double Longitude;
    private int Route;
    private int OrganizationId;

    public int getOrganizationId() {
        return OrganizationId;
    }

    public void setOrganizationId(int organizationId) {
        OrganizationId = organizationId;
    }
    public ApiStops(int id, String name, String timing, double latitude, double longitude, int route, int organizationId) {
        Id = id;
        Name = name;
        Timing = timing;
        Latitude = latitude;
        Longitude = longitude;
        Route = route;
        OrganizationId= organizationId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTiming() {
        return Timing;
    }

    public void setTiming(String timing) {
        Timing = timing;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public int getRoute() {
        return Route;
    }

    public void setRoute(int route) {
        Route = route;
    }
    @Override
    public String toString() {
        return Name; // Return the route title for display in the Spinner
    }
}
