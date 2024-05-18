package com.example.buspasswithqrscan.Admin.Model;

import java.io.Serializable;

public class NotificationAdminModel implements Serializable {
    String msg;
    String date;
    String Time;

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

    public NotificationAdminModel(String msg, String date, String time) {
        this.msg = msg;
        this.date = date;
        Time = time;
    }
}
