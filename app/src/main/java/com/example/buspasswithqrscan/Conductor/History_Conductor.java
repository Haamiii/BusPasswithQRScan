package com.example.buspasswithqrscan.Conductor;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
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

import com.example.buspasswithqrscan.Conductor.model.HistoryModel;
import com.example.buspasswithqrscan.Parent.HistoryAdapter_parent;
import com.example.buspasswithqrscan.Parent.Profile_parentFragment;
import com.example.buspasswithqrscan.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

//backbutton error image history conductor

public class History_Conductor extends Fragment {

    CardView cardView;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history__conductor, container, false);

        recyclerView = view.findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HistoryAdapter_Conductor(setDummyData(),getContext()));

        ImageButton backbutton;
        backbutton = view.findViewById(R.id.icbackButnc);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Profile_ConductorFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layoutconductor, fragment).commit();
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
        spinner=view.findViewById(R.id.spSelectcategory);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item,category);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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

    private List<HistoryModel> setDummyData() {
        List<HistoryModel> historyModelList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HistoryModel historyModel = new HistoryModel("Bus Arrival","6th Road","8:00AM","4/march/2024",01,30);
            historyModelList.add(historyModel);
        }
        return historyModelList;
    }

}