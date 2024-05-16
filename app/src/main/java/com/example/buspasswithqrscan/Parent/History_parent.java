package com.example.buspasswithqrscan.Parent;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Parent.HistoryAdapter_parent;
import com.example.buspasswithqrscan.Parent.Profile_parentFragment;
import com.example.buspasswithqrscan.Parent.model.HistoryModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class History_parent extends Fragment {

    CardView cardView;
    RecyclerView recyclerView;
    EditText fromDatePicker;
    EditText toDatePicker;
    int year;
    int month;
    int day;
    Spinner spinner;
    String[] child={"Select Child","Hamid","Abdullah","Adeel","Piyar"};

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_history_parent, container, false);

        recyclerView = rootView.findViewById(R.id.rcv_history);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new HistoryAdapter_parent(setDummyData(), getContext()));

        ImageButton backbutton;
        backbutton = rootView.findViewById(R.id.icbackButn);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new Profile_parentFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout_parent, fragment).commit();
            }
        });
        fromDatePicker=rootView.findViewById(R.id.fromdatepicker);
        toDatePicker = rootView.findViewById(R.id.todatepicker);

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
        spinner=rootView.findViewById(R.id.spSelectchild);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, child);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        return rootView;
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
        List<HistoryModel> historyModelList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            HistoryModel historyModel = new HistoryModel(i + "00", "Journey taken", "Chandni Chowk", i + "1", i + "01", "Hamid");
            historyModelList.add(historyModel);
        }

        return historyModelList;
    }
}