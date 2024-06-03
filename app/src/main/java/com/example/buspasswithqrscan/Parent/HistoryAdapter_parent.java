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
import com.example.buspasswithqrscan.Parent.model.HistoryModelParent;

import java.util.List;

public class HistoryAdapter_parent extends RecyclerView.Adapter<HistoryAdapter_parent.ViewHolder> {

    List<HistoryModelParent> historyModelsListParent;
    Context context;
    public HistoryAdapter_parent(List<HistoryModelParent> historyModelsListParent, Context context) {
        this.historyModelsListParent = historyModelsListParent;
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
        HistoryModelParent model= historyModelsListParent.get(position);
        holder.tvName.setText(model.getStudentName());
        holder.tvtitle.setText(model.getTitle());
        holder.tvpassid.setText(model.getPassId());
        holder.tvstopname.setText(model.getStopName());
        holder.tvroute.setText(model.getRoute());
        holder.tvbus.setText(model.getBus());

    }

    @Override
    public int getItemCount() {
        return historyModelsListParent.size();
    }

    public void updateData(List<HistoryModelParent> historyList) {
        this.historyModelsListParent= historyList;
        notifyDataSetChanged();
    }

    public void clearData() {
        this.historyModelsListParent.clear();
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
