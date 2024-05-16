package com.example.buspasswithqrscan.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.Student.model.Favourite_stopModel;

import java.util.List;

public class Favourite_StopsAdapter extends RecyclerView.Adapter<Favourite_StopsAdapter.ViewHolder> {

    List<Favourite_stopModel> favourite_stopModelList;
    Context context;

    public Favourite_StopsAdapter(List<Favourite_stopModel> favourite_stopModelList, Context context) {
        this.context = context;
        this.favourite_stopModelList = favourite_stopModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_fouritestop_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Favourite_stopModel model = favourite_stopModelList.get(position);

        holder.checkBox.setChecked(model.isChecked());
        holder.checkBox.setText(model.getText());

        // Set a tag to identify the position of the item clicked
        holder.checkBox.setTag(position);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int adapterPosition = holder.getAdapterPosition();
                favourite_stopModelList.get(adapterPosition).setChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favourite_stopModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cbfrvtStop);
        }
    }
}
