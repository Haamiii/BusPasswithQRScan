package com.example.buspasswithqrscan.Parent;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Parent.model.HistoryModel;

import java.util.List;

public class HistoryAdapter_parent extends RecyclerView.Adapter<HistoryAdapter_parent.ViewHolder> {

    List<HistoryModel> historyModelsList;
    Context context;
    public HistoryAdapter_parent(List<HistoryModel> historyModelsList, Context context) {
        this.historyModelsList = historyModelsList;
        this.context=context;
    }

    @NonNull
    @Override
    public HistoryAdapter_parent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history_parent_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter_parent.ViewHolder holder, int position) {
        HistoryModel model=historyModelsList.get(position);
        holder.tvName.setText(model.getName());
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
        TextView tvtitle,tvName,tvpassid,tvstopname,tvroute,tvbus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.cvhistory_pwrapper);
            tvName=itemView.findViewById(R.id.tvName);
            tvtitle=itemView.findViewById(R.id.tvtitle);
            tvpassid=itemView.findViewById(R.id.tvpassid);
            tvstopname=itemView.findViewById(R.id.tvstopname);
            tvroute=itemView.findViewById(R.id.tvroute);
            tvbus=itemView.findViewById(R.id.tvbus);
        }
    }
}
