package com.example.buspasswithqrscan.Admin.Model;

import java.io.Serializable;

public class HistoryModelAdmin implements Serializable {

    int RouteId;
    int BusId;
    String TravelType;
    int MaxPassengers;

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public int getBusId() {
        return BusId;
    }

    public void setBusId(int busId) {
        BusId = busId;
    }

    public String getTravelType() {
        return TravelType;
    }

    public void setTravelType(String travelType) {
        TravelType = travelType;
    }

    public int getMaxPassengers() {
        return MaxPassengers;
    }

    public void setMaxPassengers(int maxPassengers) {
        MaxPassengers = maxPassengers;
    }

    public HistoryModelAdmin(int routeId, int busId, String travelType, int maxPassengers) {
        RouteId = routeId;
        BusId = busId;
        TravelType = travelType;
        MaxPassengers = maxPassengers;
    }
}
