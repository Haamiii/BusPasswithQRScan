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
import com.example.buspasswithqrscan.Conductor.model.BusLocation;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Map_ConductorFragment extends Fragment implements OnMapReadyCallback {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private List<RouteModel> routeList;
    private Spinner spinner;
    private boolean isJourneyStarted = false;
    private int busId;
    private Marker busMarker;

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
                        busId = SharedPreferenceManager.getInstance().readInt("BusId", -1);
                        if (busId != -1) {
                            startJourney(busId, route.getRouteId());
                            displayRouteOnMap(route);
                        } else {
                            Toast.makeText(getContext(), "Bus ID not found", Toast.LENGTH_SHORT).show();
                        }
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

    private void startJourney(int busId, int routeId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.startJourney(busId, routeId);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    isJourneyStarted = true;
                    Toast.makeText(getContext(), "Journey Started Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Failed to Start Journey", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateBusLocation(int busId, double latitude, double longitude) {
        BusLocation busLoc = new BusLocation(busId, latitude, longitude);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.updateBusLocation(busLoc);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Bus location updated", Toast.LENGTH_SHORT).show();
                    updateMapWithBusLocation(new LatLng(latitude, longitude));
                } else {
                    Toast.makeText(getContext(), "Failed to update bus location", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void displayRouteOnMap(RouteModel route) {
        if (mMap != null) {
            mMap.clear();
            List<StopModel> stops = route.getStops();
            PolylineOptions polylineOptions = new PolylineOptions().color(Color.BLUE).width(5);

            for (StopModel stop : stops) {
                LatLng stopLatLng = new LatLng(stop.getLatitude(), stop.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions().position(stopLatLng).title(stop.getName()));
                marker.setTag(stop); // Store stop information in the marker
                polylineOptions.add(stopLatLng);
            }

            Polyline polyline = mMap.addPolyline(polylineOptions);
            if (!stops.isEmpty()) {
                LatLng firstStopLatLng = new LatLng(stops.get(0).getLatitude(), stops.get(0).getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstStopLatLng, 15));
            }

            polyline.setClickable(true);
            mMap.setOnPolylineClickListener(polyline1 -> {
                // Get the clicked point on the polyline
                LatLng clickedPoint = polyline1.getPoints().get(0); // Assuming it's the first point for simplicity

                // Update the bus location to the clicked point
                updateBusLocation(busId, clickedPoint.latitude, clickedPoint.longitude);
            });

            mMap.setOnMarkerClickListener(marker -> {
                StopModel stop = (StopModel) marker.getTag();
                if (stop != null) {
                    // Show information about the stop, but don't update the bus location
                    Toast.makeText(getContext(), "Stop: " + stop.getName(), Toast.LENGTH_SHORT).show();
                }
                return true; // Return true to indicate that we have handled the event
            });
        }
    }

    private void updateMapWithBusLocation(LatLng busLocation) {
        if (mMap != null) {
            if (busMarker != null) {
                busMarker.setPosition(busLocation);
            } else {
                busMarker = mMap.addMarker(new MarkerOptions()
                        .position(busLocation)
                        .title("Bus Location")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.busmapmarker))); // Use custom icon
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(busLocation));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                Toast.makeText(getContext(), "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}