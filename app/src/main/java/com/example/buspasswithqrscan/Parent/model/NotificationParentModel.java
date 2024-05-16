package com.example.buspasswithqrscan.Parent.model;

import java.io.Serializable;

public class NotificationParentModel implements Serializable {


    String title;
    String msg;
    String date;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public NotificationParentModel(String title, String msg, String date, String time) {
        this.title = title;
        this.msg = msg;
        this.date = date;
        Time = time;
    }

    String Time;



}
