package com.example.buspasswithqrscan.Parent;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buspasswithqrscan.Parent.model.ChildrenLocation;
import com.example.buspasswithqrscan.Parent.model.Childsparent_Model;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home_parentFragment extends Fragment implements OnMapReadyCallback {

    ViewPager2 viewPager2;
    TabLayout tabLayout;
    TabAdapter_parent adapter;
    AppCompatButton trackMyChildButton;
    private GoogleMap mMap;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_parent, container, false);

        viewPager2 = view.findViewById(R.id.view_pager_parent);
        tabLayout = view.findViewById(R.id.tab_layout_parent);

        adapter = new TabAdapter_parent();
        viewPager2.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> {})).attach();
        int parentid = SharedPreferenceManager.getInstance().read("parentId", -1);
        trackMyChildButton = view.findViewById(R.id.trackmychild);
        trackMyChildButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment Map = new Map_parentFragment();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout_parent, Map)
                        .addToBackStack(null) // This line allows the user to navigate back to the previous fragment
                        .commit();
            }
        });

        // Load the map fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapparent);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        fetchChildrenData(parentid);

        return view;
    }

    private void fetchChildrenData(int parentId) {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<Childsparent_Model>> call = apiService.getChildrenByParentId(parentId);
        call.enqueue(new Callback<List<Childsparent_Model>>() {
            @Override
            public void onResponse(Call<List<Childsparent_Model>> call, Response<List<Childsparent_Model>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Childsparent_Model> childrenList = response.body();
                    adapter.setData(childrenList);

                    if (!childrenList.isEmpty() && mMap != null) {
                        Childsparent_Model firstChild = childrenList.get(0);
                        double latitude = firstChild.getLatitude();
                        double longitude = firstChild.getLongitude();
                        LatLng childLocation = new LatLng(latitude, longitude);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(childLocation, 15)); // Adjust the zoom level as needed
                    }
                } else {
                    Toast.makeText(getActivity(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Childsparent_Model>> call, Throwable t) {
                Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // You can customize the map settings here
    }
}
