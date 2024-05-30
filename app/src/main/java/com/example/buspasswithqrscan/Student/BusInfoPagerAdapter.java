package com.example.buspasswithqrscan.Student;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.BusInfo;

import java.util.List;

public class BusInfoPagerAdapter extends RecyclerView.Adapter<BusInfoPagerAdapter.BusInfoViewHolder> {

    private List<BusInfo> busInfoList;

    public BusInfoPagerAdapter(List<BusInfo> busInfoList) {
        this.busInfoList = busInfoList;
    }

    @NonNull
    @Override
    public BusInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);
        return new BusInfoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BusInfoViewHolder holder, int position) {
        BusInfo busInfo = busInfoList.get(position);
        holder.textViewRouteNo.setText(busInfo.getRouteNo());
        holder.tvRouteNo.setText(busInfo.getPickuptime());
    }

    @Override
    public int getItemCount() {
        return busInfoList.size();
    }

    static class BusInfoViewHolder extends RecyclerView.ViewHolder {

        TextView textViewRouteNo;
        TextView tvRouteNo;

        BusInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewRouteNo = itemView.findViewById(R.id.tv_route_no);
            tvRouteNo = itemView.findViewById(R.id.tv_pickup_time);
        }
    }
}
