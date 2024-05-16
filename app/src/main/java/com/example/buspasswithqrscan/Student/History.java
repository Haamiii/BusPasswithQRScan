package com.example.buspasswithqrscan.Student;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.HistoryModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class History extends Fragment {

    CardView cardView;
    RecyclerView recyclerView;
    EditText fromDatePicker;
    EditText toDatePicker;
    int year;
    int month;
    int day;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        recyclerView=rootView.findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HistoryAdapter(setDummyData(),getContext()));

        ImageButton backbutton;
        backbutton = rootView.findViewById(R.id.icbackBtn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Fragment fragment= new ProfileFragment();
                FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout,fragment).commit();
            }
        });

        fromDatePicker = rootView.findViewById(R.id.fromdatepicker);
        toDatePicker = rootView.findViewById(R.id.todatepicker);

        // Set OnClickListener for fromDatePicker
        fromDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(fromDatePicker);
            }
        });

        // Set OnClickListener for toDatePicker
        toDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(toDatePicker);
            }
        });

        return rootView;
    }

    private List<HistoryModel> setDummyData(){
        List<HistoryModel> historyModelList=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HistoryModel historyModel= new HistoryModel(i+"00","Jouney Taken","Saddar",i+"/1/2024",i+":05",i+"1",i+"1");
            historyModelList.add(historyModel);
        }

        return historyModelList;
    }

    // Method to show DatePickerDialog
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
}
