package com.example.buspasswithqrscan.Admin.Model;

import java.util.List;

public class ApiRoute {
    private List<List<ApiStops>> apiRoute;

    public List<List<ApiStops>> getApiRoute() {
        return apiRoute;
    }

    public void setApiRoute(List<List<ApiStops>> apiRoute) {
        this.apiRoute = apiRoute;
    }
}
