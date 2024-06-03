package com.example.buspasswithqrscan.Student.model;

public class Student {
    private int id;
    private String name;
    private String contact;
    private int passId;
    private int userId;
    private String gender;
    private int parentId;
    private String password;
    private String userName;
    private String regNo;
    private String passStatus;
    private String passExpiry;
    private int remainingJourneys;
    private int totalJourneys;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public int getPassId() {
        return passId;
    }

    public void setPassId(int passId) {
        this.passId = passId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getPassStatus() {
        return passStatus;
    }

    public void setPassStatus(String passStatus) {
        this.passStatus = passStatus;
    }

    public String getPassExpiry() {
        return passExpiry;
    }

    public void setPassExpiry(String passExpiry) {
        this.passExpiry = passExpiry;
    }

    public int getRemainingJourneys() {
        return remainingJourneys;
    }

    public void setRemainingJourneys(int remainingJourneys) {
        this.remainingJourneys = remainingJourneys;
    }

    public int getTotalJourneys() {
        return totalJourneys;
    }

    public void setTotalJourneys(int totalJourneys) {
        this.totalJourneys = totalJourneys;
    }
}