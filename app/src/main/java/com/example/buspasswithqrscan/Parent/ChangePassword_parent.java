package com.example.buspasswithqrscan.Parent;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword_parent extends Fragment {

    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change_password_parent, container, false);

        oldPasswordEditText = view.findViewById(R.id.oldpass);
        newPasswordEditText = view.findViewById(R.id.newpass);
        confirmPasswordEditText = view.findViewById(R.id.conformpass);

        ImageButton backButton = view.findViewById(R.id.ivbackBtnn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Profile_parentFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_parent, fragment).commit();
            }
        });

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) AppCompatButton confirmButton = view.findViewById(R.id.confirmbtnparent);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPassword = oldPasswordEditText.getText().toString().trim();
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (!newPassword.equals(confirmPassword)) {
                    // Show error message that new password and confirm password do not match
                    Toast.makeText(getContext(), "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Make API call to change password
                changePasswordParent(oldPassword, newPassword);
            }
        });

        return view;
    }

    private void changePasswordParent(String oldPassword, String newPassword) {
        JSONObject passwordChangeObject = new JSONObject();
        try {
            SharedPreferenceManager preferenceManager = SharedPreferenceManager.getInstance();
            int userId = preferenceManager.getUserId();
            if (userId == -1) {
                Toast.makeText(getContext(), "User ID not found", Toast.LENGTH_SHORT).show();
                return;
            }

            passwordChangeObject.put("id", userId);
            passwordChangeObject.put("oldPassword", oldPassword);
            passwordChangeObject.put("newPassword", newPassword);
        } catch (JSONException e) {
            e.printStackTrace();
            return;
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), passwordChangeObject.toString());
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        Call<ResponseBody> call = apiService.changePasswordParent(requestBody);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        String responseString = response.body().string();
                        Toast.makeText(getContext(), responseString, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getContext(), "Password change failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
