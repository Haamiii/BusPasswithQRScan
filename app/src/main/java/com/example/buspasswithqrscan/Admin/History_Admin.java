package com.example.buspasswithqrscan.Admin;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Admin.Model.HistoryModelAdmin;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History_Admin extends Fragment {
    RecyclerView recyclerView;
    private HistoryAdapter_Admin adapterAdmin;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history__admin, container, false);
        recyclerView = view.findViewById(R.id.rcv_historyadmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterAdmin = new HistoryAdapter_Admin();
        recyclerView.setAdapter(adapterAdmin);
        //recyclerView.setAdapter(new HistoryAdapter_Admin(setDummyData(),getContext()));

        ImageButton backbutton;
        backbutton = view.findViewById(R.id.icbackButnAdmin);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ProfileAdminFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layoutAdmin, fragment).commit();
            }
        });

        fetchgetrouteranking();

        return view;
    }

    private void fetchgetrouteranking() {
        int OrganizationId = SharedPreferenceManager.getInstance().readInt("OrganizationId", 1);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<List<HistoryModelAdmin>> call = apiService.getRouteRanking(OrganizationId);
        Log.d("TestDATA", String.valueOf(OrganizationId));
        call.enqueue(new Callback<List<HistoryModelAdmin>>() {
            @Override
            public void onResponse(Call<List<HistoryModelAdmin>> call, Response<List<HistoryModelAdmin>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<HistoryModelAdmin> travels = response.body();
                    if (travels.isEmpty()) {
                        Toast.makeText(getContext(), "There is no Data.", Toast.LENGTH_SHORT).show();
                    } else {
                        adapterAdmin.setData(travels);
                    }
                } else {
                    Toast.makeText(getContext(), "Failed to fetch user history", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HistoryModelAdmin>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
