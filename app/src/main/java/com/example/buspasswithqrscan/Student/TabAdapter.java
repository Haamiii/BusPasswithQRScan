package com.example.buspasswithqrscan.Student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.StopModel;

import java.util.List;

public class TabAdapter extends RecyclerView.Adapter<TabAdapter.ViewHolder> {

    List<StopModel> stopModelList;

    public TabAdapter(List<StopModel> list) {
        this.stopModelList = list;
    }

    @NonNull
    @Override
    public TabAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter.ViewHolder holder, int position) {
        StopModel model = stopModelList.get(position);
        holder.tvStopName.setText(model.getStopName());
        holder.tvRouteNo.setText(String.valueOf(model.getRouteNo()));
        holder.tvStopTime.setText(model.getStopTime());
    }

    @Override
    public int getItemCount() {
        return stopModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvStopName, tvRouteNo, tvStopTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStopName = itemView.findViewById(R.id.tv_stop_name);
            tvRouteNo = itemView.findViewById(R.id.tv_route_no);
            tvStopTime = itemView.findViewById(R.id.tv_stop_timing);
        }
    }
}
