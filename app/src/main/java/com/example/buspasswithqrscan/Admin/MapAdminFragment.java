package com.example.buspasswithqrscan.Admin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.buspasswithqrscan.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapAdminFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener{
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_map_admin, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mapADMIN, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        showPopupMenu(latLng);
    }

    private void showPopupMenu(LatLng latLng) {
        // Inflate the popup menu layout
        View popupView = getLayoutInflater().inflate(R.layout.popup_menu, null);

        // Convert LatLng to screen coordinates
        Projection projection = mMap.getProjection();
        Point screenPoint = projection.toScreenLocation(latLng);

        // Create the popup window
        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(20);

        // Show the popup window at the screen coordinates
        popupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, screenPoint.x, screenPoint.y);

        // Get buttons from the popup view
        Button btnAddStop = popupView.findViewById(R.id.btnAddStop);
        Button btnAddRoute = popupView.findViewById(R.id.btnAddRoute);

        // Set button click listeners
        btnAddStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add stop
                addNewStop(latLng);
                popupWindow.dismiss();
            }
        });

        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle add route
                addNewRoute(latLng);
                popupWindow.dismiss();
            }
        });
    }

    private void addNewStop(LatLng latLng) {
        // Implementation for adding a new stop
    }

    private void addNewRoute(LatLng latLng) {
        // Implementation for adding a new route
    }



    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);
        // Enable the My Location layer if the permission is granted
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        // Add a marker in Biit and move the camera
        LatLng uni = new LatLng(33.64098, 73.07782);
        mMap.addMarker(new MarkerOptions().position(uni).title("Barani Institute of Information Technology"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uni, 15f));
    }
}