package com.example.buspasswithqrscan.Parent.model;

import java.io.Serializable;

public class NotificationParentModel implements Serializable {


    int Id;
    String Date;
    String Time;
    String Type;
    String Description;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getNotificationRead() {
        return NotificationRead;
    }

    public void setNotificationRead(int notificationRead) {
        NotificationRead = notificationRead;
    }

    public NotificationParentModel(int id, String date, String time, String type, String description, int userID, int notificationRead) {
        Id = id;
        Date = date;
        Time = time;
        Type = type;
        Description = description;
        UserID = userID;
        NotificationRead = notificationRead;
    }

    int UserID;
    int NotificationRead;
}