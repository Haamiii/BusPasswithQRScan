package com.example.buspasswithqrscan.Admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Admin.Model.HistoryModelAdmin;
import com.example.buspasswithqrscan.R;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter_Admin extends RecyclerView.Adapter<HistoryAdapter_Admin.ViewHolder> {
    List<HistoryModelAdmin> historyModelAdminList;

    public HistoryAdapter_Admin(){
        this.historyModelAdminList=new ArrayList<>();
    }

    public HistoryAdapter_Admin(List<HistoryModelAdmin> historyModelAdminList) {
        this.historyModelAdminList = historyModelAdminList;
    }

    @NonNull
    @Override
    public HistoryAdapter_Admin.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_admin_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter_Admin.ViewHolder holder, int position) {
        HistoryModelAdmin model= historyModelAdminList.get(position);
        holder.tvrouteno.setText(String.valueOf(model.getRouteId()));
        holder.tvbusno.setText(String.valueOf(model.getBusId()));
        holder.tvtraveltype.setText(model.getTravelType());
        holder.tvpassengers.setText(String.valueOf(model.getMaxPassengers()));
    }

    @Override
    public int getItemCount()
    {
        return historyModelAdminList.size();
    }
    public void setData(List<HistoryModelAdmin> historyList) {
        this.historyModelAdminList.clear();
        this.historyModelAdminList.addAll(historyList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvrouteno, tvbusno, tvtraveltype, tvpassengers;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cvhistory_pwrapperadmin);

            tvrouteno=itemView.findViewById(R.id.tvrouteno);
            tvbusno=itemView.findViewById(R.id.tvBusNo);
            tvtraveltype=itemView.findViewById(R.id.tvtravelType);
            tvpassengers=itemView.findViewById(R.id.tvpassengers);
        }
    }
}
