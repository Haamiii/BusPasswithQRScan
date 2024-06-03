package com.example.buspasswithqrscan.Parent;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buspasswithqrscan.Parent.model.NotificationParentModel;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Notification_parentFragment extends Fragment {

    RecyclerView recyclerView;
    NotificationAdapter_parent adapter;
    private static final String TAG = "NotificationsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification_parent, container, false);

        recyclerView = view.findViewById(R.id.rcv_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter
        adapter = new NotificationAdapter_parent(new ArrayList<>(), getContext());
        recyclerView.setAdapter(adapter);

        // Call the API to fetch notifications
        getUserNotifications();

        return view;
    }

    private void getUserNotifications() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        try {
            String userJson = SharedPreferenceManager.getInstance().read("user", null);
            if (userJson != null) {
                JSONObject object = new JSONObject(userJson);
                int userId = object.getInt("UserId");

                Call<List<NotificationParentModel>> call = apiService.getUserNotifications(userId);
                call.enqueue(new Callback<List<NotificationParentModel>>() {
                    @Override
                    public void onResponse(Call<List<NotificationParentModel>> call, Response<List<NotificationParentModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            // Update data on the adapter instance
                            adapter.updateData(response.body());
                        } else {
                            Toast.makeText(getContext(), "No Notification Found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<NotificationParentModel>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Error fetching notifications: " + t.getMessage());
                    }
                });
            } else {
                Toast.makeText(getContext(), "User data not found", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException ex) {
            Log.e(TAG, "JSON Exception: " + ex.getMessage());
            Toast.makeText(getContext(), "Error parsing user data", Toast.LENGTH_SHORT).show();
        }
    }
}
