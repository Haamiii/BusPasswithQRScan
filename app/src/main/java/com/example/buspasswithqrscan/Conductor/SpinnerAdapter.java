package com.example.buspasswithqrscan.Conductor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Conductor.model.SpinnerModel;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<SpinnerModel> {
    public SpinnerAdapter(Context context, int resource, List<SpinnerModel> journeys) {
        super(context, resource, journeys);

    }
}