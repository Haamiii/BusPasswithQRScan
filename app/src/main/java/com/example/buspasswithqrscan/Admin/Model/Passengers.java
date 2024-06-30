package com.example.buspasswithqrscan.Admin.Model;

public class Passengers {

    String Name;
    int PassId;
    int RegNo;
    String StopName;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getPassId() {
        return PassId;
    }

    public void setPassId(int passId) {
        PassId = passId;
    }

    public int getRegNo() {
        return RegNo;
    }

    public void setRegNo(int regNo) {
        RegNo = regNo;
    }

    public String getStopName() {
        return StopName;
    }

    public void setStopName(String stopName) {
        StopName = stopName;
    }

    public Passengers(String name, int passId, int regNo, String stopName) {
        Name = name;
        PassId = passId;
        RegNo = regNo;
        StopName = stopName;
    }
}
