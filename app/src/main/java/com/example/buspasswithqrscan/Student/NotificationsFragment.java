package com.example.buspasswithqrscan.Student;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.example.buspasswithqrscan.Student.model.NotificationModel;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;

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

public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    NotificationAdapter notificationAdapter;
    private static final String TAG = "NotificationsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.rcv_notification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationAdapter = new NotificationAdapter(new ArrayList<>(), getContext());
        recyclerView.setAdapter(notificationAdapter);
        fetchStudentNotifications();

        return view;
    }

    private void fetchStudentNotifications() {
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        try {
            String userJson = SharedPreferenceManager.getInstance().read("user", null);
            if (userJson != null) {
                JSONObject object = new JSONObject(userJson);
                int userId = object.getInt("UserId");

                Call<ResponseBody> call = apiService.GetUserNotification(userId);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            try {
                                String responseBody = response.body().string();
                                if (isJSONValid(responseBody)) {
                                    JSONArray jsonArray = new JSONArray(responseBody);
                                    List<NotificationModel> studentNotification = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        NotificationModel notification = new NotificationModel(
                                                jsonObject.getInt("Id"),
                                                jsonObject.getString("Date"),
                                                jsonObject.getString("Time"),
                                                jsonObject.getString("Type"),
                                                jsonObject.getString("Description"),
                                                jsonObject.getInt("UserID"),
                                                jsonObject.getInt("NotificationRead")
                                        );
                                        studentNotification.add(notification);
                                    }
                                    notificationAdapter.updateData(studentNotification);
                                } else {
                                    Toast.makeText(getContext(), responseBody, Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException | JSONException e) {
                                Log.e(TAG, "Error parsing response: " + e.getMessage());
                                Toast.makeText(getContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "No Notification Found!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
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

    private boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
