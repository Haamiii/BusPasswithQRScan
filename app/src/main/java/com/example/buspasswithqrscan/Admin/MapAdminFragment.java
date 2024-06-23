package com.example.buspasswithqrscan.Admin;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.buspasswithqrscan.Admin.Model.ApiStops;
import com.example.buspasswithqrscan.Admin.Model.BusLocations;
import com.example.buspasswithqrscan.Admin.Model.Route;
import com.example.buspasswithqrscan.Parent.model.ChildrenLocation;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapAdminFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private static final String TAG = "MapAdminFragment";

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private List<LatLng> routeLocations = new ArrayList<>();

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

        // Fetch routes and stops once the map is ready
        fetchStops();
        // Fetch routes and stops once the map is ready
        fetchRoutesAndStops();
        // Fetch and display bus locations
        int organizationId = SharedPreferenceManager.getInstance().readInt("OrganizationId", 0);
        fetchBusesLocations(organizationId);
    }

    private void fetchStops() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<ApiStops>> call = apiService.getAllStops();
        call.enqueue(new Callback<List<ApiStops>>() {
            @Override
            public void onResponse(Call<List<ApiStops>> call, Response<List<ApiStops>> response) {
                if (response.isSuccessful()) {
                    List<ApiStops> stops = response.body();
                    if (stops != null) {
                        Log.d(TAG, "Stops fetched: " + stops.size());
                        displayStopsOnMap(stops);
                    } else {
                        Log.d(TAG, "Stops response is null");
                    }
                } else {
                    Log.d(TAG, "Failed to fetch stops: " + response.message());
                    Toast.makeText(getContext(), "Failed to fetch stops", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ApiStops>> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getContext(), "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayStopsOnMap(List<ApiStops> stops) {
        if (mMap == null) {
            Log.d(TAG, "Map is not ready");
            return;
        }
        for (ApiStops stop : stops) {
            LatLng latLng = new LatLng(stop.getLatitude(), stop.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(stop.getName()));
        }
    }

    private void fetchRoutesAndStops() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        int organizationid = SharedPreferenceManager.getInstance().readInt("OrganizationId", 0);
        Log.d(TAG, "Fetching routes for organization ID: " + organizationid);

        Call<List<List<ApiStops>>> call = apiService.getAllRoute(organizationid);
        call.enqueue(new Callback<List<List<ApiStops>>>() {
            @Override
            public void onResponse(Call<List<List<ApiStops>>> call, Response<List<List<ApiStops>>> response) {
                if (response.isSuccessful()) {
                    List<List<ApiStops>> routes = response.body();
                    if (routes != null) {
                        Log.d(TAG, "Routes fetched: " + routes.size());
                        displayRoutesOnMap(routes);
                        displayStopsOnMap(routes); // New method to display stops
                    } else {
                        Log.d(TAG, "Routes response is null");
                    }
                } else {
                    Log.d(TAG, "Failed to fetch routes: " + response.message());
                    Toast.makeText(getContext(), "Failed to fetch routes", Toast.LENGTH_SHORT).show();
                }
            }

            private void displayStopsOnMap(List<List<ApiStops>> routes) {
                if (mMap == null) {
                    Log.d(TAG, "Map is not ready");
                    return;
                }
                for (List<ApiStops> route : routes) {
                    for (ApiStops stop : route) {
                        LatLng latLng = new LatLng(stop.getLatitude(), stop.getLongitude());
                        mMap.addMarker(new MarkerOptions().position(latLng).title(stop.getName()));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<List<ApiStops>>> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getContext(), "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRoutesOnMap(List<List<ApiStops>> routes) {
        if (mMap == null) {
            Log.d(TAG, "Map is not ready");
            return;
        }
        for (List<ApiStops> route : routes) {
            List<LatLng> latLngs = new ArrayList<>();
            for (ApiStops stop : route) {
                LatLng latLng = new LatLng(stop.getLatitude(), stop.getLongitude());
                mMap.addMarker(new MarkerOptions().position(latLng).title(stop.getName()));
                latLngs.add(latLng);
            }

            // Draw polyline connecting stops in the route
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(latLngs)
                    .color(Color.RED)
                    .width(10);
            mMap.addPolyline(polylineOptions);
        }

        // Move the camera to show all markers
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (List<ApiStops> route : routes) {
            for (ApiStops stop : route) {
                LatLng latLng = new LatLng(stop.getLatitude(), stop.getLongitude());
                builder.include(latLng);
            }
        }
        LatLngBounds bounds = builder.build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
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
                String selectedStop = (String) spcategoryAdmin.getSelectedItem();
                if (!routeName.isEmpty() && selectedStop != null) {
                    addNewRoute(latLng, routeName, selectedStop);
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
        categories.add("Saddar");
        categories.add("Marir Chowk");
        categories.add("Double Road");
        categories.add("IJP Road");
        categories.add("Faizabad");
        categories.add("Old Airport Road");
        categories.add("Kurri Road");
        return categories;
    }

    private void addNewStop(final LatLng latLng, final String stopName) {
        mMap.addMarker(new MarkerOptions().position(latLng).title(stopName));

        // Get the organization ID from shared preferences
        int organizationId = SharedPreferenceManager.getInstance().readInt("OrganizationId", 0);

        // Create a new Stop object
        ApiStops stop = new ApiStops(0, stopName,"" ,latLng.latitude, latLng.longitude, 0, organizationId);

        // Make the API call to insert the stop
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<String> call = apiService.insertStop(stop);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Stop added successfully: " + response.body());
                    Toast.makeText(getContext(), "Stop added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Failed to add stop: " + response.message());
                    Toast.makeText(getContext(), "Failed to add stop", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getContext(), "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addNewRoute(LatLng latLng, String routeName, String selectedStops) {
        // Prepare the route object
        Route route = new Route(routeName, SharedPreferenceManager.getInstance().readInt("OrganizationId", 0), selectedStops);

        // Call the API to insert the route
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<String> call = apiService.insertRoute(route);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "Route added successfully: " + response.body());
                    Toast.makeText(getContext(), "Route added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "Failed to add route: " + response.message());
                    Toast.makeText(getContext(), "Failed to add route", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getContext(), "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void fetchBusesLocations(int organizationId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<BusLocations>> call = apiService.getBusesLocation(organizationId);
        call.enqueue(new Callback<List<BusLocations>>() {
            @Override
            public void onResponse(Call<List<BusLocations>> call, Response<List<BusLocations>> response) {
                if (response.isSuccessful()) {
                    List<BusLocations> busLocations = response.body();
                    if (busLocations != null) {
                        displayBusLocations(busLocations);
                    } else {
                        Log.d(TAG, "Bus locations response is null");
                    }
                } else {
                    Log.d(TAG, "Failed to fetch bus locations: " + response.message());
                    Toast.makeText(getContext(), "Failed to fetch bus locations", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<BusLocations>> call, Throwable t) {
                Log.d(TAG, "API call failed: " + t.getMessage());
                Toast.makeText(getContext(), "API call failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayBusLocations(List<BusLocations> busLocations) {
        if (mMap == null) {
            Log.d(TAG, "Map is not ready");
            return;
        }

        for (BusLocations busLocation : busLocations) {
            // Extract information from BusLocation object
            int busId = busLocation.getBusId();
            int routeId = busLocation.getRouteId();
            String routeTitle = busLocation.getRouteTitle();
            ChildrenLocation.Location location = busLocation.getCords();
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // Create LatLng object for bus location
            LatLng latLng = new LatLng(latitude, longitude);

            // Add marker for bus location
            mMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Bus ID: " + busId)
                    .snippet("Route ID: " + routeId + "\nRoute Title: " + routeTitle)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.busmapmarker)));
        }
    }

}