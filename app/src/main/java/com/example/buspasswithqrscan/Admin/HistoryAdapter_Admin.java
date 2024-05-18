package com.example.buspasswithqrscan.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buspasswithqrscan.Admin.Model.HistoryModelAdmin;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class HistoryAdapter_Admin extends RecyclerView.Adapter<HistoryAdapter_Admin.ViewHolder> {
    List<HistoryModelAdmin> historyModelAdminList;
    Context context;

    public HistoryAdapter_Admin(List<HistoryModelAdmin> historyModelAdminList, Context context) {
        this.historyModelAdminList = historyModelAdminList;
        this.context = context;
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
        holder.tvstudentscan.setText(model.getStudentscan());
        holder.tvroute.setText(model.getRoute());
        holder.tvdate.setText(model.getDate());
        holder.tvtime.setText(model.getTime());
        holder.tvtitle.setText(model.getTitle());
        holder.tvStopName.setText(model.getStopname());

    }

    @Override
    public int getItemCount()
    {
        return historyModelAdminList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvtitle, tvStopName, tvtime, tvdate, tvroute, tvstudentscan;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cvhistory_pwrapperadmin);
            tvtitle=itemView.findViewById(R.id.tvtitleadmin);
            tvStopName=itemView.findViewById(R.id.tvstopNameAdmin);
            tvtime=itemView.findViewById(R.id.tvtimeadmin);
            tvdate=itemView.findViewById(R.id.tvdateAdmin);
            tvroute=itemView.findViewById(R.id.tvrouteAdmin);
            tvstudentscan=itemView.findViewById(R.id.tvstudentscanAdmin);
        }
    }
}
