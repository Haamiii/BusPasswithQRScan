package com.example.buspasswithqrscan.Admin.Model;

public class BusLocationss {

    int BusId;
    int TotalSeats;
    int Passengers;
    int RouteId;
    int RouteTitle;

    public int getBusId() {
        return BusId;
    }

    public void setBusId(int busId) {
        BusId = busId;
    }

    public int getTotalSeats() {
        return TotalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        TotalSeats = totalSeats;
    }

    public int getPassengers() {
        return Passengers;
    }

    public void setPassengers(int passengers) {
        Passengers = passengers;
    }

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public int getRouteTitle() {
        return RouteTitle;
    }

    public void setRouteTitle(int routeTitle) {
        RouteTitle = routeTitle;
    }

    public Coordinates getCords() {
        return Cords;
    }

    public void setCords(Coordinates cords) {
        Cords = cords;
    }

    public BusLocationss(int busId, int totalSeats, int passengers, int routeId, int routeTitle, Coordinates cords) {
        BusId = busId;
        TotalSeats = totalSeats;
        Passengers = passengers;
        RouteId = routeId;
        RouteTitle = routeTitle;
        Cords = cords;
    }

    Coordinates Cords;

}
