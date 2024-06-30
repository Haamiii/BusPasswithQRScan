package com.example.buspasswithqrscan.SuperAdmin.Model;

public class OrganizationsDetails {
    private int Id;
    private String Name;
    private int TotalUsers;
    private int TotalAdmins;
    private int TotalConductors;
    private int TotalParents;
    private int TotalStudents;

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

    public int getTotalUsers() {
        return TotalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        TotalUsers = totalUsers;
    }

    public int getTotalAdmins() {
        return TotalAdmins;
    }

    public void setTotalAdmins(int totalAdmins) {
        TotalAdmins = totalAdmins;
    }

    public int getTotalConductors() {
        return TotalConductors;
    }

    public void setTotalConductors(int totalConductors) {
        TotalConductors = totalConductors;
    }

    public int getTotalParents() {
        return TotalParents;
    }

    public void setTotalParents(int totalParents) {
        TotalParents = totalParents;
    }

    public int getTotalStudents() {
        return TotalStudents;
    }

    public void setTotalStudents(int totalStudents) {
        this.TotalStudents = totalStudents;
    }

    public OrganizationsDetails(int id, String name, int totalUsers, int totalAdmins, int totalConductors, int totalParents, int totalStudents) {
        Id = id;
        Name = name;
        TotalUsers = totalUsers;
        TotalAdmins = totalAdmins;
        TotalConductors = totalConductors;
        TotalParents = totalParents;
        TotalStudents = totalStudents;
    }
}
