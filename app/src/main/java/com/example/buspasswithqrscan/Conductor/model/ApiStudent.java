package com.example.buspasswithqrscan.Conductor.model;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiStudent {
    private String name;
    private String regNo;
    private String remainingJourneys;
    private String gender;
    private String passId;
    private String passExpiry;
    private String passStatus;
    private String imageUrl;

    public ApiStudent(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString("Name");
        this.regNo = jsonObject.getString("RegNo");
        this.remainingJourneys = jsonObject.getString("RemainingJourneys");
        this.gender = jsonObject.getString("Gender");
        this.passId = jsonObject.getString("PassId");
        this.passExpiry = jsonObject.getString("PassExpiry");
        this.passStatus = jsonObject.getString("PassStatus");
        this.imageUrl = jsonObject.getString("D:\\pic_20201009_112406");
    }

    // Getters
    public String getName() { return name; }
    public String getRegNo() { return regNo; }
    public String getRemainingJourneys() { return remainingJourneys; }
    public String getGender() { return gender; }
    public String getPassId() { return passId; }
    public String getPassExpiry() { return passExpiry; }
    public String getPassStatus() { return passStatus; }
    public String getImageUrl() { return imageUrl; }
}