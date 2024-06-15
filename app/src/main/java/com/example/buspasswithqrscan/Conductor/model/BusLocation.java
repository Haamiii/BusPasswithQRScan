package com.example.buspasswithqrscan.Conductor.model;

import com.google.gson.annotations.SerializedName;

public class BusLocation {
    @SerializedName("BusId")
    private int busId;

    @SerializedName("Cords")
    private Coordinates coordinates;

    public BusLocation(int busId, double latitude, double longitude) {
        this.busId = busId;
        this.coordinates = new Coordinates(latitude, longitude);
    }

    public int getBusId() {
        return busId;
    }

    public void setBusId(int busId) {
        this.busId = busId;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public static class Coordinates {
        @SerializedName("latitude")
        private double latitude;

        @SerializedName("longitude")
        private double longitude;

        public Coordinates(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
