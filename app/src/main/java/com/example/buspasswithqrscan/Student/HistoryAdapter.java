package com.example.buspasswithqrscan.Student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.HistoryModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<HistoryModel> historyModelsList;

    public HistoryAdapter() {
        this.historyModelsList = new ArrayList<>();
    }

    public HistoryAdapter(List<HistoryModel> historyModelsList) {
        this.historyModelsList = historyModelsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HistoryModel model = historyModelsList.get(position);
        holder.tvdate.setText(formatDate(model.getDate()));
        holder.tvtime.setText(formatTime(model.getTime()));
        holder.tvtitle.setText(model.getType());
        holder.tvpassid.setText(String.valueOf(model.getPassId()));
        holder.tvstopname.setText(String.valueOf(model.getStopId()));
        holder.tvroute.setText(String.valueOf(model.getRouteId()));
        holder.tvbus.setText(String.valueOf(model.getBusId()));
    }

    @Override
    public int getItemCount() {
        return historyModelsList != null ? historyModelsList.size() : 0;
    }

    public void setData(List<HistoryModel> historyList) {
        this.historyModelsList.clear();
        this.historyModelsList.addAll(historyList);
        notifyDataSetChanged();
    }

    private String formatDate(String originalDate) {
        // Assuming the date is in the format "yyyy-MM-dd"
        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        try {
            Date date = originalFormat.parse(originalDate);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return originalDate; // in case of error, return the original date string
        }
    }

    private String formatTime(String originalTime) {
        // Assuming the time is in the format "HH:mm:ss.SSSSSSS"
        SimpleDateFormat originalFormat = new SimpleDateFormat("HH:mm:ss.SSSSSSS", Locale.getDefault());
        SimpleDateFormat targetFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        try {
            Date date = originalFormat.parse(originalTime);
            return targetFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return originalTime; // in case of error, return the original time string
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvdate, tvtime, tvtitle, tvpassid, tvstopname, tvroute, tvbus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvdate = itemView.findViewById(R.id.tvdate);
            tvtime = itemView.findViewById(R.id.tvtime);
            tvtitle = itemView.findViewById(R.id.tvtitle);
            tvpassid = itemView.findViewById(R.id.tvpassid);
            tvstopname = itemView.findViewById(R.id.tvstopname);
            tvroute = itemView.findViewById(R.id.tvroute);
            tvbus = itemView.findViewById(R.id.tvbus);
        }
    }
}
