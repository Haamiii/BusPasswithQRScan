
package com.example.buspasswithqrscan.SuperAdmin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.R;
import com.example.buspasswithqrscan.SuperAdmin.Model.OrganizationsDetails;

import java.util.List;

public class OrganizationsAdapter extends RecyclerView.Adapter<OrganizationsAdapter.ViewHolder> {
    private List<OrganizationsDetails> organizationsList;

    public OrganizationsAdapter(List<OrganizationsDetails> organizationsList) {
        this.organizationsList = organizationsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_organization, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrganizationsDetails organization = organizationsList.get(position);
        holder.nameTextView.setText(organization.getName());
        holder.totalUsersTextView.setText(String.valueOf(organization.getTotalUsers()));
        holder.totalAdminsTextView.setText(String.valueOf(organization.getTotalAdmins()));
        holder.totalConductorsTextView.setText(String.valueOf(organization.getTotalConductors()));
        holder.totalParentsTextView.setText(String.valueOf(organization.getTotalParents()));
        holder.totalStudentsTextView.setText(String.valueOf(organization.getTotalStudents()));
        int user=organization.getTotalUsers();
        int total=14;
        holder.prgrassSA.setProgress(user);
        holder.prgrassSA.setMax(total);
    }

    @Override
    public int getItemCount() {
        return organizationsList != null ? organizationsList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, totalUsersTextView, totalAdminsTextView, totalConductorsTextView, totalParentsTextView, totalStudentsTextView;

        ProgressBar prgrassSA;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            totalUsersTextView = itemView.findViewById(R.id.totalUsersTextView);
            totalAdminsTextView = itemView.findViewById(R.id.totalAdminsTextView);
            totalConductorsTextView = itemView.findViewById(R.id.totalConductorsTextView);
            totalParentsTextView = itemView.findViewById(R.id.totalParentsTextView);
            totalStudentsTextView = itemView.findViewById(R.id.totalStudentsTextView);
            prgrassSA=itemView.findViewById(R.id.prgrassSA);
        }
    }
}
