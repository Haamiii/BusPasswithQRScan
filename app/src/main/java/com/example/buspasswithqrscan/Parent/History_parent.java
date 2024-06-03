package com.example.buspasswithqrscan.Parent;


//not fetching data got errors

import android.os.Bundle;
import android.app.DatePickerDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Parent.model.HistoryModelParent;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.Student;
import com.example.buspasswithqrscan.network.ApiService;
import com.example.buspasswithqrscan.network.RetrofitClient;
import com.example.buspasswithqrscan.network.SharedPreferenceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History_parent extends Fragment {

    RecyclerView recyclerView;
    EditText fromDatePicker;
    EditText toDatePicker;
    Spinner spinner;
    List<Student> children;
    HistoryAdapter_parent historyAdapter;
    ApiService apiService;
    int parentId; // Example parent ID, replace with actual logged-in parent's ID
    HashMap<String, Integer> childIdMap = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_parent, container, false);

        apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        // Initialize UI components
        recyclerView = rootView.findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        historyAdapter = new HistoryAdapter_parent(new ArrayList<>(), getContext());
        recyclerView.setAdapter(historyAdapter);

        fromDatePicker = rootView.findViewById(R.id.fromdatepicker);
        toDatePicker = rootView.findViewById(R.id.todatepicker);
        spinner = rootView.findViewById(R.id.spSelectchild);

        parentId = SharedPreferenceManager.getInstance().read("parentId", -1);
        Log.d("History_parent", "Parent ID: " + parentId);

        fromDatePicker.setOnClickListener(v -> showDatePickerDialog(fromDatePicker));
        toDatePicker.setOnClickListener(v -> showDatePickerDialog(toDatePicker));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("History_parent", "Spinner item selected: " + position);
                if (position > 0) {
                    // Fetch history for selected student and dates
                    String selectedChildName = parent.getItemAtPosition(position).toString();
                    int selectedChildId = childIdMap.get(selectedChildName);
                    String fromDate = fromDatePicker.getText().toString();
                    String toDate = toDatePicker.getText().toString();
                    if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                        fetchHistory(selectedChildId, fromDate, toDate);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        fromDatePicker.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && spinner.getSelectedItemPosition() > 0) {
                String selectedChildName = spinner.getSelectedItem().toString();
                int selectedChildId = childIdMap.get(selectedChildName);
                String fromDate = fromDatePicker.getText().toString();
                String toDate = toDatePicker.getText().toString();
                if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                    fetchHistory(selectedChildId, fromDate, toDate);
                }
            }
        });

        toDatePicker.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus && spinner.getSelectedItemPosition() > 0) {
                String selectedChildName = spinner.getSelectedItem().toString();
                int selectedChildId = childIdMap.get(selectedChildName);
                String fromDate = fromDatePicker.getText().toString();
                String toDate = toDatePicker.getText().toString();
                if (!fromDate.isEmpty() && !toDate.isEmpty()) {
                    fetchHistory(selectedChildId, fromDate, toDate);
                }
            }
        });

        // Manually populate children list (replace with actual data)
        fetchChildrenManually();

        return rootView;
    }

    private void fetchChildrenManually() {
        // Manually populate children list (replace with actual data)
        List<Student> children = new ArrayList<>();
        children.add(new Student(2, "Hamid Basar Wahab"));
        children.add(new Student(5, "Hammad Wahab"));

        // Populate spinner with children names
        List<String> childNames = new ArrayList<>();
        childNames.add("Select Child");
        for (Student child : children) {
            childNames.add(child.getName()); // Add child name to list
            childIdMap.put(child.getName(), child.getId()); // Map child name to ID
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, childNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void showDatePickerDialog(final EditText datePickerEditText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), (view, year1, monthOfYear, dayOfMonth) -> {
            datePickerEditText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void fetchHistory(int studentId, String fromDate, String toDate) {
        // Check if the provided studentId is valid
        if (studentId == 2 || studentId == 5) {
            // Fetch history for the selected student and dates
            apiService.getUserHistoryParent(studentId, fromDate, toDate).enqueue(new Callback<List<List<HistoryModelParent>>>() {
                @Override
                public void onResponse(Call<List<List<HistoryModelParent>>> call, Response<List<List<HistoryModelParent>>> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<HistoryModelParent> historyList = new ArrayList<>();
                        for (List<HistoryModelParent> travelList : response.body()) {
                            historyList.addAll(travelList);
                        }
                        if (!historyList.isEmpty()) {
                            historyAdapter.updateData(historyList);
                        } else {
                            Toast.makeText(getContext(), "No history found for the selected period", Toast.LENGTH_SHORT).show();
                            historyAdapter.clearData(); // Clear previous history if any
                        }
                    } else {
                        String errorMessage = "Failed to fetch history: ";
                        if (response.errorBody() != null) {
                            try {
                                errorMessage += response.errorBody().string();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            errorMessage += "Unknown error";
                        }
                        Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        Log.d("History_parent", errorMessage);
                    }
                }

                @Override
                public void onFailure(Call<List<List<HistoryModelParent>>> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to fetch history", Toast.LENGTH_SHORT).show();
                    Log.e("History_parent", "Failed to fetch history", t);
                }
            });
        } else {
            Toast.makeText(getContext(), "Invalid student ID", Toast.LENGTH_SHORT).show();
            Log.e("History_parent", "Invalid student ID: " + studentId);
        }
    }



}
