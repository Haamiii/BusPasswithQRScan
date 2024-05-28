package com.example.buspasswithqrscan.Student;


import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buspasswithqrscan.MainActivity;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.example.buspasswithqrscan.Student.model.Student;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.ApiResponse;
import com.example.buspasswithqrscan.network.ApiService;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    private TextView nameTextView;
    private TextView contactTextView;
    private TextView passIdTextView;
    private TextView genderTextView;
    private TextView regNoTextView;
    private TextView passExpiryTextView;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = rootView.findViewById(R.id.namestudent);
        contactTextView = rootView.findViewById(R.id.contactStudent);
        passIdTextView = rootView.findViewById(R.id.passidStudent);
        genderTextView = rootView.findViewById(R.id.genderStudent);
        regNoTextView = rootView.findViewById(R.id.regnoStudent);
        passExpiryTextView = rootView.findViewById(R.id.passexpiryStudent);
        try {
            JSONObject object=new JSONObject(SharedPreferenceManager.getInstance().read("user",null));
            nameTextView.setText(object.getString("Name"));
            contactTextView.setText(object.getString("Contact"));
            passIdTextView.setText(object.getInt("PassId")+"");
            genderTextView.setText(object.getString("Gender"));
            regNoTextView.setText(object.getString("RegNo"));
            String[] exp=object.getString("PassExpiry").split(" ");
            passExpiryTextView.setText(exp[0]);
        } catch (JSONException e) {
            Log.d("ero==>>",e.getMessage());
        }
        //fetchStudentData();
        rootView.findViewById(R.id.historybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment historyFragment = new History();
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frame_layout, historyFragment)
                        .addToBackStack(null) // This line allows the user to navigate back to the previous fragment
                        .commit();
            }
        });
        rootView.findViewById(R.id.changepssbtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment changepassFragment = new ChangePassword();
                getParentFragmentManager().beginTransaction().replace(R.id.frame_layout, changepassFragment).commit();
            }
        });
        rootView.findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        return rootView;
    }


//    private void fetchStudentData() {
//        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
//        Call<ApiResponse<Student>> call = apiService.getStudentDetails(SharedPreferenceManager.getInstance().read("user_id",0));
//        try {
//            call.enqueue(new Callback<ApiResponse<Student>>() {
//                @Override
//                public void onResponse(Call<ApiResponse<Student>> call, Response<ApiResponse<Student>> response) {
//                    if (response.isSuccessful() && response.body() != null) {
//                        Student student = response.body().getData();
//                        updateUI(student);
//                    } else {
//                        Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ApiResponse<Student>> call, Throwable t) {
//                    Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        } catch (Exception e) {
//            Log.e(TAG, "Exception during network call: " + e.getMessage(), e);
//            Toast.makeText(getContext(), "Exception: " + e.getMessage(), Toast.LENGTH_SHORT).show(); }
//    }
//
//    private void updateUI(Student student) {
//        nameTextView.setText(student.getName());
//        contactTextView.setText(student.getContact());
//        passIdTextView.setText(String.valueOf(student.getPassId()));
//        genderTextView.setText(student.getGender());
//        regNoTextView.setText(student.getRegNo());
//        passExpiryTextView.setText(student.getPassExpiry());
//    }


}
