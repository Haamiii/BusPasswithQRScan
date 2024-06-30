package com.example.buspasswithqrscan.SuperAdmin.Model;

public class Organization {
    private int Id;
    private String Name;
    private Cords Cords;

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

    public Cords getCords() {
        return Cords;
    }

    public void setCords(Cords cords) {
        Cords = cords;
    }

    public static class Cords {
        private double latitude;
        private double longitude;

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
