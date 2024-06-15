package com.example.buspasswithqrscan.Student;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager2.widget.ViewPager2;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.BusInfo;
import com.example.buspasswithqrscan.Student.model.StopModel;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final Context mContext;
    private final ApiService apiService;
    private List<List<StopModel>> allStopsData;

    public CustomInfoWindowAdapter(Context context) {
        mContext = context;
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        fetchStopsAndRoutes();
    }

    private void fetchStopsAndRoutes() {
        int OrganizationId=SharedPreferenceManager.getInstance().readInt("OrganizationId",1);
        Call<List<List<StopModel>>> call = apiService.getAllRoutes(OrganizationId);
        call.enqueue(new Callback<List<List<StopModel>>>() {
            @Override
            public void onResponse(Call<List<List<StopModel>>> call, Response<List<List<StopModel>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    allStopsData = response.body();
                } else {
                    allStopsData = new ArrayList<>();
                    Log.e("CustomInfoWindow", "Failed to fetch stops and routes: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<List<StopModel>>> call, Throwable t) {
                allStopsData = new ArrayList<>();
                Log.e("CustomInfoWindow", "Error fetching stops and routes: " + t.getMessage());
            }
        });
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null; // Return null so default info window is not displayed
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null; // Return null so default info window is not displayed
    }

    void showBusInfoDialog(Marker marker) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View dialogView = inflater.inflate(R.layout.custom_info_window, null);
        builder.setView(dialogView);

        TextView titleTextView = dialogView.findViewById(R.id.title);
        ViewPager2 viewPager = dialogView.findViewById(R.id.viewPager);
        TabLayout tabLayout = dialogView.findViewById(R.id.tab_layoutstd);
        Button btnClose = dialogView.findViewById(R.id.btnClose);
        Button btnAddToFavourites = dialogView.findViewById(R.id.btnAddToFavourites);

        String stopName = marker.getTitle();
        titleTextView.setText(stopName);

        AlertDialog dialog = builder.create();
        btnClose.setOnClickListener(v -> dialog.dismiss());

        // Find the bus info from the allStopsData
        List<BusInfo> busInfoList = getBusInfoByStopName(stopName);

        if (!busInfoList.isEmpty()) {
            BusInfoPagerAdapter adapter = new BusInfoPagerAdapter(busInfoList);
            viewPager.setAdapter(adapter);
            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();
        }

        // Adding stop to favorite stops when button clicked
        btnAddToFavourites.setOnClickListener(v -> {
            // Assuming stopId and studentId are obtained from somewhere
            int stopId = getStopIdByName(stopName);
            int studentId = getStudentId(); // Replace with your actual logic to get student ID

            apiService.AddFavStop(studentId, stopId).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        String message = response.body();
                        if (message.equals("Fav Stop Added")) {
                            Toast.makeText(mContext, "Stop added to favorites", Toast.LENGTH_SHORT).show();
                        } else if (message.equals("Fav Stop Already Exist")) {
                            Toast.makeText(mContext, "Stop is already a favorite", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        try {
                            // Log detailed error message
                            String errorBody = response.errorBody().string();
                            Log.e("AddFavStopError", "Error response: " + errorBody);
                            Toast.makeText(mContext, "Failed to add stop to favorites: " + errorBody, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(mContext, "Failed to add stop to favorites", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(mContext, "Failed to add stop to favorites", Toast.LENGTH_SHORT).show();
                }
            });
        });

        dialog.show();
    }

    private List<BusInfo> getBusInfoByStopName(String stopName) {
        List<BusInfo> busInfoList = new ArrayList<>();
        if (allStopsData != null) {
            for (List<StopModel> routeList : allStopsData) {
                for (StopModel stop : routeList) {
                    if (stop.getName().equals(stopName)) {
                        BusInfo busInfo = new BusInfo();
                        busInfo.setRouteNo(String.valueOf(stop.getRoute()));
                        busInfo.setPickuptime(stop.getTiming());
                        busInfoList.add(busInfo);
                    }
                }
            }
        }
        return busInfoList;
    }

    private int getStopIdByName(String stopName) {
        if (allStopsData != null) {
            for (List<StopModel> routeList : allStopsData) {
                for (StopModel stop : routeList) {
                    if (stop.getName().equals(stopName)) {
                        return stop.getId();
                    }
                }
            }
        }
        // Return a default value or handle the case when the stop name is not found
        return -1; // For example, returning -1 if stop name is not found
    }

    private int getStudentId() {

        return SharedPreferenceManager.getInstance().getStudentId();
    }
}
