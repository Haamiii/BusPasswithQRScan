package com.example.buspasswithqrscan.Admin.Model;

public class BusModel {
    String Busno;
    int StudentsChecked;
    int remainingSeats;

    public String getBusno() {
        return Busno;
    }

    public void setBusno(String busno) {
        Busno = busno;
    }

    public int getStudentsChecked() {
        return StudentsChecked;
    }

    public void setStudentsChecked(int studentsChecked) {
        StudentsChecked = studentsChecked;
    }

    public int getRemainingSeats() {
        return remainingSeats;
    }

    public void setRemainingSeats(int remainingSeats) {
        this.remainingSeats = remainingSeats;
    }

    public BusModel(String busno, int studentsChecked, int remainingSeats) {
        Busno = busno;
        StudentsChecked = studentsChecked;
        this.remainingSeats = remainingSeats;
    }
}
