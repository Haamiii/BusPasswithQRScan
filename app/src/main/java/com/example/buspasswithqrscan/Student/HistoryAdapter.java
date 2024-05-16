package com.example.buspasswithqrscan.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.HistoryModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    List<HistoryModel> historyModelsList;
    Context context;

    public HistoryAdapter(List<HistoryModel> historyModelsList, Context context) {
        this.historyModelsList = historyModelsList;
        this.context=context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        HistoryModel model=historyModelsList.get(position);
        holder.tvdate.setText(model.getDate());
        holder.tvtime.setText(model.getTime());
        holder.tvtitle.setText(model.getTitle());
        holder.tvpassid.setText(model.getPassid());
        holder.tvstopname.setText(model.getStopname());
        holder.tvroute.setText(model.getRoute());
        holder.tvbus.setText(model.getBus());
    }

    @Override
    public int getItemCount() {

        return historyModelsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView tvdate,tvtime,tvtitle,tvpassid,tvstopname,tvroute,tvbus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cvhistorywrapper);
            tvdate=itemView.findViewById(R.id.tvdate);
            tvtime=itemView.findViewById(R.id.tvtime);
            tvtitle=itemView.findViewById(R.id.tvtitle);
            tvpassid=itemView.findViewById(R.id.tvpassid);
            tvstopname=itemView.findViewById(R.id.tvstopname);
            tvroute=itemView.findViewById(R.id.tvroute);
            tvbus=itemView.findViewById(R.id.tvbus);
        }
    }
}
