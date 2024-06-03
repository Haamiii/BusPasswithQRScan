package com.example.buspasswithqrscan.Parent.model;

import com.google.gson.annotations.SerializedName;

public class Childsparent_Model {

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @SerializedName("childDetails")
    private ChildDetails childDetails;

    @SerializedName("childTimings")
    private ChildTimings childTimings;

    public ChildDetails getChildDetails() {
        return childDetails;
    }

    public void setChildDetails(ChildDetails childDetails) {
        this.childDetails = childDetails;
    }

    public ChildTimings getChildTimings() {
        return childTimings;
    }

    public void setChildTimings(ChildTimings childTimings) {
        this.childTimings = childTimings;
    }

    // Getters and setters

    public static class ChildDetails {
        private int Id;
        private String Name;
        private String Gender;
        private String RegNo;
        private String Contact;
        private String UserName;
        private String Password;
        private String PassStatus;
        private String PassExpiry;
        private int TotalJourneys;
        private int RemainingJourneys;
        private int ParentId;
        private int UserId;
        private int PassId;

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

        public String getGender() {
            return Gender;
        }

        public void setGender(String gender) {
            Gender = gender;
        }

        public String getRegNo() {
            return RegNo;
        }

        public void setRegNo(String regNo) {
            RegNo = regNo;
        }

        public String getContact() {
            return Contact;
        }

        public void setContact(String contact) {
            Contact = contact;
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

        public String getPassStatus() {
            return PassStatus;
        }

        public void setPassStatus(String passStatus) {
            PassStatus = passStatus;
        }

        public String getPassExpiry() {
            return PassExpiry;
        }

        public void setPassExpiry(String passExpiry) {
            PassExpiry = passExpiry;
        }

        public int getTotalJourneys() {
            return TotalJourneys;
        }

        public void setTotalJourneys(int totalJourneys) {
            TotalJourneys = totalJourneys;
        }

        public int getRemainingJourneys() {
            return RemainingJourneys;
        }

        public void setRemainingJourneys(int remainingJourneys) {
            RemainingJourneys = remainingJourneys;
        }

        public int getParentId() {
            return ParentId;
        }

        public void setParentId(int parentId) {
            ParentId = parentId;
        }

        public int getUserId() {
            return UserId;
        }

        public void setUserId(int userId) {
            UserId = userId;
        }

        public int getPassId() {
            return PassId;
        }

        public void setPassId(int passId) {
            PassId = passId;
        }

        // Getters and setters
    }

    public static class ChildTimings {
        public String getPickup_Checkin() {
            return Pickup_Checkin;
        }

        public void setPickup_Checkin(String pickup_Checkin) {
            Pickup_Checkin = pickup_Checkin;
        }

        public String getPickup_Checkout() {
            return Pickup_Checkout;
        }

        public void setPickup_Checkout(String pickup_Checkout) {
            Pickup_Checkout = pickup_Checkout;
        }

        public String getDropoff_Checkin() {
            return Dropoff_Checkin;
        }

        public void setDropoff_Checkin(String dropoff_Checkin) {
            Dropoff_Checkin = dropoff_Checkin;
        }

        public String getDropoff_Checkout() {
            return Dropoff_Checkout;
        }

        public void setDropoff_Checkout(String dropoff_Checkout) {
            Dropoff_Checkout = dropoff_Checkout;
        }

        private String Pickup_Checkin;
        private String Pickup_Checkout;
        private String Dropoff_Checkin;
        private String Dropoff_Checkout;

        // Getters and setters
    }
}
