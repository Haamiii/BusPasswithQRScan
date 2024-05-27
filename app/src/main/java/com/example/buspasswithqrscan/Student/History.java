package com.example.buspasswithqrscan.Student;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;
import com.example.buspasswithqrscan.Student.model.HistoryModel;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History extends Fragment {

    private HistoryAdapter adapter;
    private RecyclerView recyclerView;
    private EditText fromDatePicker;
    private EditText toDatePicker;
    private int year;
    private int month;
    private int day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView = rootView.findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new HistoryAdapter();
        recyclerView.setAdapter(adapter);

        fromDatePicker = rootView.findViewById(R.id.fromdatepicker);
        toDatePicker = rootView.findViewById(R.id.todatepicker);

        fromDatePicker.setOnClickListener(v -> showDatePickerDialog(fromDatePicker));
        toDatePicker.setOnClickListener(v -> showDatePickerDialog(toDatePicker));

        return rootView;
    }

    private void showDatePickerDialog(final EditText datePickerEditText) {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year, monthOfYear, dayOfMonth) -> {
            datePickerEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            String fromDate = fromDatePicker.getText().toString();
            String toDate = toDatePicker.getText().toString();
            if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                fetchUserHistory(fromDate, toDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    private void fetchUserHistory(String fromDate, String toDate) {
        Log.d("TestDATA", fromDate + toDate);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        try {
            String userJson = SharedPreferenceManager.getInstance().read("user", null);
            if (userJson != null) {
                JSONObject object = new JSONObject(userJson);
                int userId = object.getInt("UserId");

                Call<List<HistoryModel>> call = apiService.getUserHistory(userId, fromDate, toDate);
                call.enqueue(new Callback<List<HistoryModel>>() {
                    @Override
                    public void onResponse(Call<List<HistoryModel>> call, Response<List<HistoryModel>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<HistoryModel> apiTravelList = response.body();
                            if (apiTravelList.isEmpty()) {
                                Toast.makeText(getContext(), "There is no history for the selected dates", Toast.LENGTH_SHORT).show();
                            } else {
                                adapter.setData(apiTravelList);
                            }
                        } else {
                            Toast.makeText(getContext(), "Failed to fetch user history", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<HistoryModel>> call, Throwable t) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }
}
