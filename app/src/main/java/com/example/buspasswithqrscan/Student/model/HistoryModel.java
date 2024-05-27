package com.example.buspasswithqrscan.Student.model;

import java.io.Serializable;

public class HistoryModel implements Serializable {
    int Id,PassId,StudentId,BusId,RouteId,StopId;
    String Date,Time,Type;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getPassId() {
        return PassId;
    }

    public void setPassId(int passId) {
        PassId = passId;
    }

    public int getStudentId() {
        return StudentId;
    }

    public void setStudentId(int studentId) {
        StudentId = studentId;
    }

    public int getBusId() {
        return BusId;
    }

    public void setBusId(int busId) {
        BusId = busId;
    }

    public int getRouteId() {
        return RouteId;
    }

    public void setRouteId(int routeId) {
        RouteId = routeId;
    }

    public int getStopId() {
        return StopId;
    }

    public void setStopId(int stopId) {
        StopId = stopId;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public HistoryModel(int id, int passId, int studentId, int busId, int routeId, int stopId, String date, String time, String type) {
        Id = id;
        PassId = passId;
        StudentId = studentId;
        BusId = busId;
        RouteId = routeId;
        StopId = stopId;
        Date = date;
        Time = time;
        Type = type;
    }
}
