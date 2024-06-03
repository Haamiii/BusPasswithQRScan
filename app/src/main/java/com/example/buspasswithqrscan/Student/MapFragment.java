package com.example.buspasswithqrscan.Student;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.BusLocation;
import com.example.buspasswithqrscan.Student.model.StopModel;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private ApiService apiService;
    private static final String TAG = "MapFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null) {
            FragmentManager fm = getChildFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.map, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        return v;
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        }

        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(requireContext()));
        fetchStopsAndRoutes();
        fetchBusesLocations();
    }



    private void fetchStopsAndRoutes() {
        Call<List<List<StopModel>>> call = apiService.getAllStops();
        call.enqueue(new Callback<List<List<StopModel>>>() {
            @Override
            public void onResponse(Call<List<List<StopModel>>> call, Response<List<List<StopModel>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API Response: " + response.body().toString());
                    displayStopsAndRoutes(response.body());
                } else {
                    Log.e(TAG, "Failed to fetch data: " + response.message());
                    Toast.makeText(requireContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<List<StopModel>>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayStopsAndRoutes(List<List<StopModel>> apiRoutes) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        boolean hasLocations = false;

        for (List<StopModel> route : apiRoutes) {
            List<LatLng> routePoints = new ArrayList<>();
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.busstop);
            for (StopModel stop : route) {
                double lat = Double.parseDouble(stop.getLatitude());
                double lng = Double.parseDouble(stop.getLongitude());
                Log.d(TAG, "Stop: " + stop.getName() + ", Lat: " + lat + ", Lng: " + lng);
                if (lat == 0.0 && lng == 0.0) {
                    Log.e(TAG, "Invalid coordinates for stop: " + stop.getName());
                    continue;
                }
                LatLng location = new LatLng(lat, lng);
                Marker marker=  mMap.addMarker(new MarkerOptions().position(location).icon(icon).title(stop.getName()));
                marker.setTag(stop.getId());// Set the stop ID as the marker's tag
                routePoints.add(location);
                builder.include(location);
                hasLocations = true;
            }
            if (!routePoints.isEmpty()) {
                PolylineOptions polylineOptions = new PolylineOptions()
                        .addAll(routePoints)
                        .color(Color.RED)
                        .width(10);
                mMap.addPolyline(polylineOptions);
            }
        }

        if (hasLocations) {
            LatLngBounds bounds = builder.build();
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
        } else {
            Log.e(TAG, "No valid locations found to display on the map");
            Toast.makeText(requireContext(), "No valid locations found", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchBusesLocations() {
        Call<List<BusLocation>> call = apiService.getBusesLocations();
        call.enqueue(new Callback<List<BusLocation>>() {
            @Override
            public void onResponse(Call<List<BusLocation>> call, Response<List<BusLocation>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d(TAG, "API Response: " + response.body().toString());
                    displayBusesOnMap(response.body());
                } else {
                    Log.e(TAG, "Failed to fetch bus locations: " + response.message());
                    Toast.makeText(requireContext(), "Failed to fetch bus locations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BusLocation>> call, Throwable t) {
                Log.e(TAG, "Error: " + t.getMessage());
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayBusesOnMap(List<BusLocation> busLocations) {
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.busmapmarker);
        for (BusLocation busLocation : busLocations) {
            LatLng busLatLng = new LatLng(busLocation.getCords().getLatitude(), busLocation.getCords().getLongitude());
            mMap.addMarker(new MarkerOptions().position(busLatLng).icon(icon).title("Bus ID: " + busLocation.getBusId()));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
