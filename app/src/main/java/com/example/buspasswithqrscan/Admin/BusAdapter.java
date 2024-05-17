package com.example.buspasswithqrscan.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Admin.Model.BusModel;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class BusAdapter extends RecyclerView.Adapter<BusAdapter.ViewHolder> {

    List<BusModel> busModelList;
    public BusAdapter(List<BusModel> busModelList) {
        this.busModelList = busModelList;
    }


    @NonNull
    @Override
    public BusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.buss_layout, parent, false);
        return new BusAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusAdapter.ViewHolder holder, int position) {
        BusModel model=busModelList.get(position);
        holder.tv_bus_no.setText(model.getBusno());
        holder.tvstudentcheckedinno.setText(String.valueOf(model.getStudentsChecked()));
        holder.tv_remainingseatsno.setText(String.valueOf(model.getRemainingSeats()));

    }

    @Override
    public int getItemCount() {
        return busModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvstudentcheckedinno,tv_remainingseatsno,tv_bus_no;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_bus_no = itemView.findViewById(R.id.tv_bus_no);
            tv_remainingseatsno = itemView.findViewById(R.id.tv_remainingseatsno);
            tvstudentcheckedinno = itemView.findViewById(R.id.tvstudentcheckedinno);
        }
    }
}
