package com.example.buspasswithqrscan.Admin;

import android.content.Context;

import com.example.buspasswithqrscan.network.ApiService;

public class custominfopopupAdapter {
    private final Context mContext;
    private final ApiService apiService;

    public custominfopopupAdapter(Context mContext, ApiService apiService) {
        this.mContext = mContext;
        this.apiService = apiService;
    }
}
