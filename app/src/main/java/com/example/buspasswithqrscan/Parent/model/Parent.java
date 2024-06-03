package com.example.buspasswithqrscan.Parent.model;

public class Parent {
    int Id;
    String Name;
    String Contact;
    int ChildrenEnroll;
    int UserId;
    String UserName;
    String Password;

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

    public int getChildrenEnroll() {
        return ChildrenEnroll;
    }

    public void setChildrenEnroll(int childrenEnroll) {
        ChildrenEnroll = childrenEnroll;
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

    public Parent(int id, String name, String contact, int childrenEnroll, int userId, String userName, String password) {
        Id = id;
        Name = name;
        Contact = contact;
        ChildrenEnroll = childrenEnroll;
        UserId = userId;
        UserName = userName;
        Password = password;
    }
}
