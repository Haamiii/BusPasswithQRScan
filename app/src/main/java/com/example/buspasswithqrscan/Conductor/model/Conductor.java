package com.example.buspasswithqrscan.Conductor.model;

public class Conductor {
    int Id;
    String Name;
    String Contact;
    int UserId;
    String UserName;
    String Password;
    int BusId;
    String BusRegNo;
    int TotalSeats;
    public Conductor(int id, String name, String contact, int userId, String userName, String password, int busId, String busRegNo, int totalSeats) {
        Id = id;
        Name = name;
        Contact = contact;
        UserId = userId;
        UserName = userName;
        Password = password;
        BusId = busId;
        BusRegNo = busRegNo;
        TotalSeats = totalSeats;
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

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getBusId() {
        return BusId;
    }

    public void setBusId(int busId) {
        BusId = busId;
    }

    public String getBusRegNo() {
        return BusRegNo;
    }

    public void setBusRegNo(String busRegNo) {
        BusRegNo = busRegNo;
    }

    public int getTotalSeats() {
        return TotalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        TotalSeats = totalSeats;
    }
}
