package com.example.buspasswithqrscan.Parent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.buspasswithqrscan.Parent.model.Childsparent_Model;
import com.example.buspasswithqrscan.R;
import java.util.ArrayList;
import java.util.List;

public class TabAdapter_parent extends RecyclerView.Adapter<TabAdapter_parent.ViewHolder> {

    private List<Childsparent_Model> childsparentModelList;

    public TabAdapter_parent() {
        this.childsparentModelList = new ArrayList<>();
    }

    public void setData(List<Childsparent_Model> list) {
        this.childsparentModelList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TabAdapter_parent.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imageparemt_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TabAdapter_parent.ViewHolder holder, int position) {
        Childsparent_Model model = childsparentModelList.get(position);
        Log.d("Adapter Data", "Name: " + model.getChildDetails().getName());
        holder.tvname.setText(model.getChildDetails().getName());
        holder.tvpickcheckintime.setText(model.getChildTimings().getPickup_Checkin());
        holder.tvpickcheckouttime.setText(model.getChildTimings().getPickup_Checkout());
        holder.tvdropcheckintime.setText(model.getChildTimings().getDropoff_Checkin());
        holder.tvdropcheckouttime.setText(model.getChildTimings().getDropoff_Checkout());

        int totalJourneys = model.getChildDetails().getTotalJourneys();
        int remainingJourneys = model.getChildDetails().getRemainingJourneys();

        holder.prgrassstudent.setMax(totalJourneys);
        holder.prgrassstudent.setProgress(remainingJourneys);
        holder.tvtotaljourneyparentstd .setText("/ " + totalJourneys);
        holder.tvjourneyparentstd.setText(String.valueOf(remainingJourneys));
    }

    @Override
    public int getItemCount() {
        return childsparentModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvname, tvpickcheckintime, tvpickcheckouttime, tvdropcheckintime, tvdropcheckouttime,tvjourneyparentstd,tvtotaljourneyparentstd;
        ProgressBar prgrassstudent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvname = itemView.findViewById(R.id.tv_name);
            tvpickcheckintime = itemView.findViewById(R.id.tv_pickcheckin_time);
            tvpickcheckouttime = itemView.findViewById(R.id.tv_pickcheckout_time);
            tvdropcheckintime = itemView.findViewById(R.id.tv_dropcheckin_time);
            tvdropcheckouttime = itemView.findViewById(R.id.tv_dropcheckout_time);
            prgrassstudent=itemView.findViewById(R.id.prgrassstudent);
            tvjourneyparentstd = itemView.findViewById(R.id.tvjourneyparentstd);
            tvtotaljourneyparentstd = itemView.findViewById(R.id.tvtotaljourneyparentstd);
        }
    }
}
