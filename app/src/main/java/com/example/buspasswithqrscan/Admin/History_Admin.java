package com.example.buspasswithqrscan.Admin;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.buspasswithqrscan.Admin.Model.HistoryModelAdmin;
import com.example.buspasswithqrscan.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class History_Admin extends Fragment {
    RecyclerView recyclerView;
    EditText fromDatePicker;
    EditText toDatePicker;
    int year;
    int month;
    int day;
    Spinner spinner;
    String[] category={"Bus Arrival","Bus Departure"};
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history__admin, container, false);
        recyclerView = view.findViewById(R.id.rcv_historyadmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HistoryAdapter_Admin(setDummyData(),getContext()));

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
        fromDatePicker=view.findViewById(R.id.fromdatepicker);
        toDatePicker = view.findViewById(R.id.todatepicker);

        fromDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(fromDatePicker);
            }
        });
        toDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(toDatePicker);
            }
        });
        spinner=view.findViewById(R.id.spcategoryAdmin);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,category);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        return view;
    }

    private void showDatePickerDialog(final EditText datePickerEditText) {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Set the selected date to EditText
                    datePickerEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                }, year, month, day);
        datePickerDialog.show();

    }

    private List<HistoryModelAdmin> setDummyData() {
        List<HistoryModelAdmin> historyModelAdminList= new ArrayList<>();
        for (int i = 0; i < 10; i++){
            HistoryModelAdmin historyModelAdmin=new HistoryModelAdmin("Bus Arrival","6th Road","8:00AM","4/march/2024", "1","30");
            historyModelAdminList.add(historyModelAdmin);
        }

        return historyModelAdminList;
    }
}