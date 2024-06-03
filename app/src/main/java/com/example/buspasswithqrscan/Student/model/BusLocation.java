package com.example.buspasswithqrscan.Student.model;

import com.example.buspasswithqrscan.Student.model.BusLocationCoordinates;

public class BusLocation {
    private int BusId;
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

