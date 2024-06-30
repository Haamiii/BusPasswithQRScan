package com.example.buspasswithqrscan.SuperAdmin.Model;

import java.util.List;

public class SuperAdminDashboardData {
    private int TotalUsers;
    private List<OrganizationsDetails> Organizations;

    public int getTotalUsers() {
        return TotalUsers;
    }

    public void setTotalUsers(int totalUsers) {
        TotalUsers = totalUsers;
    }

    public List<OrganizationsDetails> getOrganizations() {
        return Organizations;
    }

    public void setOrganizations(List<OrganizationsDetails> organizations) {
        this.Organizations = organizations;
    }

    public SuperAdminDashboardData(int totalUsers, List<OrganizationsDetails> organizations) {
        TotalUsers = totalUsers;
        this.Organizations = organizations;
    }
}
