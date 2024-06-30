package com.example.buspasswithqrscan.SuperAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.SuperAdmin.Model.Organization;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_superadmin_map extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superadmin_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapSA);
        if (mapFragment == null) {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction().replace(R.id.mapSA, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        fetchOrganizationsAndAddMarkers();
    }

    private void fetchOrganizationsAndAddMarkers() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Organization>> call = apiService.getAllOrganization();
        call.enqueue(new Callback<List<Organization>>() {
            @Override
            public void onResponse(Call<List<Organization>> call, Response<List<Organization>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Organization> organizations = response.body();
                    for (Organization organization : organizations) {
                        LatLng location = new LatLng(organization.getCords().getLatitude(), organization.getCords().getLongitude());
                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(location)
                                .title(organization.getName())
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.university)); // Example: Blue marker

                        mMap.addMarker(markerOptions);
                    }
                    if (!organizations.isEmpty()) {
                        LatLng firstOrgLocation = new LatLng(organizations.get(0).getCords().getLatitude(), organizations.get(0).getCords().getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstOrgLocation, 10));
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Organization>> call, Throwable t) {
                // Handle failure
                t.printStackTrace();
            }
        });
    }
}
