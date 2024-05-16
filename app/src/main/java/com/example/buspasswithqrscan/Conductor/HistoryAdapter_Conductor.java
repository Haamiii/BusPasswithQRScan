package com.example.buspasswithqrscan.Conductor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Conductor.model.HistoryModel;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class HistoryAdapter_Conductor extends RecyclerView.Adapter<HistoryAdapter_Conductor.ViewHolder> {
    List<HistoryModel> historyModelsList;
    Context context;

    public HistoryAdapter_Conductor(List<HistoryModel> historyModelsList, Context context) {
        this.historyModelsList = historyModelsList;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter_Conductor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_conductor_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter_Conductor.ViewHolder holder, int position) {
        HistoryModel model=historyModelsList.get(position);
        holder.tvstudentscan.setText(model.getStudentscan());
        holder.tvroute.setText(model.getRoute());
        holder.tvdate.setText(model.getDate());
        holder.tvtime.setText(model.getTime());
        holder.tvtitle.setText(model.getTitle());
        holder.tvStopName.setText(model.getStopname());

    }

    @Override
    public int getItemCount() {
        return historyModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tvtitle, tvStopName, tvtime, tvdate, tvroute, tvstudentscan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cvhistory_pwrapperconductor);
            tvtitle=itemView.findViewById(R.id.tvtitlec);
            tvStopName=itemView.findViewById(R.id.tvstopNamec);
            tvtime=itemView.findViewById(R.id.tvtimec);
            tvdate=itemView.findViewById(R.id.tvdatec);
            tvroute=itemView.findViewById(R.id.tvroutec);
            tvstudentscan=itemView.findViewById(R.id.tvstudentscanc);

        }
    }
}
