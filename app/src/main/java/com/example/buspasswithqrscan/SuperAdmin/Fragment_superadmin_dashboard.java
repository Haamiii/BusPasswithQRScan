package com.example.buspasswithqrscan.SuperAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.SuperAdmin.Model.OrganizationsDetails;
import com.example.buspasswithqrscan.SuperAdmin.Model.SuperAdminDashboardData;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_superadmin_dashboard extends Fragment {
    private ViewPager2 viewPager2;
    private OrganizationsAdapter adapter;
    private TabLayout tabLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_superadmin_dashboard, container, false);
        viewPager2 = view.findViewById(R.id.view_pager_parent);
        tabLayout = view.findViewById(R.id.tab_layout_superadmin);
        fetchOrganizationDetails();
        return view;
    }

    private void fetchOrganizationDetails() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        apiService.getAllOrganizationDetails().enqueue(new Callback<SuperAdminDashboardData>() {
            @Override
            public void onResponse(Call<SuperAdminDashboardData> call, Response<SuperAdminDashboardData> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<OrganizationsDetails> organizationsList = response.body().getOrganizations();
                    if (organizationsList != null && !organizationsList.isEmpty()) {
                        adapter = new OrganizationsAdapter(organizationsList);
                        viewPager2.setAdapter(adapter);

                        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> {})).attach();
                    } else {
                        Toast.makeText(getContext(), "No organizations found", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SuperAdminDashboardData> call, Throwable t) {
                Toast.makeText(getContext(), "Network Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
