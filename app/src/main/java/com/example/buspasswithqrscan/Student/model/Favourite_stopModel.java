package com.example.buspasswithqrscan.Student.model;

import java.io.Serializable;

public class Favourite_stopModel implements Serializable {
    int id;
    String text;

    boolean isChecked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public Favourite_stopModel(int id, String text, boolean isChecked) {
        this.id = id;
        this.text = text;
        this.isChecked = isChecked;
    }
}
