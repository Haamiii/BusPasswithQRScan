package com.example.buspasswithqrscan.Admin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapAdminFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_admin, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapADMIN);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.mapADMIN, mapFragment).commit();
        }

        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        showPopupMenu(latLng);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapClickListener(this);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        LatLng uni = new LatLng(33.64098, 73.07782);
        mMap.addMarker(new MarkerOptions().position(uni).title("Barani Institute of Information Technology"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(uni, 15f));
    }

    private void showPopupMenu(final LatLng latLng) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_menu, null);
        Projection projection = mMap.getProjection();
        Point screenPoint = projection.toScreenLocation(latLng);

        final PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(getView(), Gravity.NO_GRAVITY, screenPoint.x, screenPoint.y);

        Button btnAddStop = popupView.findViewById(R.id.btnAddStop);
        Button btnAddRoute = popupView.findViewById(R.id.btnAddRoute);

        btnAddStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showStopInformationPopup(latLng);
            }
        });

        btnAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showRouteInformationPopup(latLng);
            }
        });
    }

    private void showStopInformationPopup(final LatLng latLng) {
        View popupView = getLayoutInflater().inflate(R.layout.popup_stop_information, null);

        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = popupView.getMeasuredWidth();
        int height = popupView.getMeasuredHeight();

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);

        EditText editTextStopName = popupView.findViewById(R.id.editTextStopName);
        Button buttonAddStop = popupView.findViewById(R.id.buttonAddStop);
        Button buttonCloseStop = popupView.findViewById(R.id.buttonCloseStop);

        buttonAddStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String stopName = editTextStopName.getText().toString();
                if (!stopName.isEmpty()) {
                    addNewStop(latLng, stopName);
                    popupWindow.dismiss();
                }
            }
        });

        buttonCloseStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void showRouteInformationPopup(final LatLng latLng) {
        View popupView = getLayoutInflater().inflate(R.layout.pop_route_information, null);

        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = popupView.getMeasuredWidth();
        int height = popupView.getMeasuredHeight();

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setElevation(20);
        popupWindow.showAtLocation(getView(), Gravity.CENTER, 0, 0);

        EditText editTextRouteName = popupView.findViewById(R.id.editTextRouteName);
        Spinner spcategoryAdmin = popupView.findViewById(R.id.spcategoryAdmin);
        Button buttonAddRoute = popupView.findViewById(R.id.buttonAddRoute);
        Button buttonCloseRoute = popupView.findViewById(R.id.buttonCloseRoute);

        // Populate the spinner with data
        List<String> spinnerdata = getSpinnerdata();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, spinnerdata);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spcategoryAdmin.setAdapter(adapter);

        buttonAddRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String routeName = editTextRouteName.getText().toString();
                String selectedCategory = (String) spcategoryAdmin.getSelectedItem();
                if (!routeName.isEmpty() && selectedCategory != null) {
                    addNewRoute(latLng, routeName, selectedCategory);
                    popupWindow.dismiss();
                }
            }
        });

        buttonCloseRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private List<String> getSpinnerdata() {
        // Replace with actual data retrieval logic
        List<String> categories = new ArrayList<>();
        categories.add("Chandni Chowk");
        categories.add("6th Road");
        categories.add("ShamsAbad");
        return categories;
    }

    private void addNewStop(LatLng latLng, String stopName) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(stopName));
    }

    private void addNewRoute(LatLng latLng, String routeName, String category) {
        // Implementation for adding a new route with a category
    }
}
