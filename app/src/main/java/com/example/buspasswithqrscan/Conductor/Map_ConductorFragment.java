package com.example.buspasswithqrscan.Conductor;

import android.Manifest;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.Conductor.model.RouteModel;
import com.example.buspasswithqrscan.Conductor.model.StopModel;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map_ConductorFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private List<RouteModel> routeList;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map__conductor, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapconductor);
        if (mapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mapconductor, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        Button chooseJourneyButton = v.findViewById(R.id.choose_journey_button);
        chooseJourneyButton.setOnClickListener(view -> showSpinnerDialog());
        return v;
    }

    private void showSpinnerDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.diologl_spinner, null);

        spinner = dialogView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setTitle("Select Journey")
                .setCancelable(false)
                .create();

        Button startButton = dialogView.findViewById(R.id.start_button);
        Button closeButton = dialogView.findViewById(R.id.close_button);

        startButton.setOnClickListener(v -> {
            String selectedRouteTitle = (String) spinner.getSelectedItem();
            if (selectedRouteTitle != null) {
                for (RouteModel route : routeList) {
                    if (route.getRouteTitle().equals(selectedRouteTitle)) {
                        displayRouteOnMap(route);
                        break;
                    }
                }
            }
            dialog.dismiss();
        });

        closeButton.setOnClickListener(v -> dialog.dismiss());

        fetchRoutesAndPopulateSpinner(adapter);
        dialog.show();
    }

    private void fetchRoutesAndPopulateSpinner(ArrayAdapter<String> adapter) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        int conductorId = SharedPreferenceManager.getInstance().readInt("conductorId", -1);
        Call<List<RouteModel>> call = apiService.getAssignedRoutes(conductorId);

        call.enqueue(new Callback<List<RouteModel>>() {
            @Override
            public void onResponse(Call<List<RouteModel>> call, Response<List<RouteModel>> response) {
                if (response.isSuccessful()) {
                    routeList = response.body();
                    if (routeList != null && !routeList.isEmpty()) {
                        for (RouteModel route : routeList) {
                            String routeTitle = route.getRouteTitle();
                            if (routeTitle != null) {
                                adapter.add(routeTitle);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No routes found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch routes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<RouteModel>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRouteOnMap(RouteModel route) {
        if (mMap == null) return;

        mMap.clear();
        List<LatLng> locations = new ArrayList<>();
        for (StopModel stop : route.getStops()) {
            LatLng location = new LatLng(stop.getLatitude(), stop.getLongitude());
            locations.add(location);
            mMap.addMarker(new MarkerOptions().position(location).title(stop.getName()));
        }

        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(locations)
                .color(Color.RED)
                .width(10);

        mMap.addPolyline(polylineOptions);

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng location : locations) {
            builder.include(location);
        }
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {
        // Handle map click if necessary
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
    }
}
