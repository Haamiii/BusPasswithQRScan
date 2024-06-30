package com.example.buspasswithqrscan.Admin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buspasswithqrscan.Admin.Model.Routes;
import com.example.buspasswithqrscan.R;

import java.util.List;

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.RouteViewHolder> {

    private List<Routes> routes;
    private Context context;

    public RoutesAdapter(Context context,List<Routes> routes) {
        this.context = context;
        this.routes = routes;
    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemroute, parent, false);
        return new RouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        Routes route = routes.get(position);
        holder.routeName.setText(route.getRouteTitle());
    }

    @Override
    public int getItemCount() {
        return routes != null ? routes.size() : 0;
    }

    public void updateRoutes(List<Routes> routes) {
        this.routes = routes;
        notifyDataSetChanged();
    }

    static class RouteViewHolder extends RecyclerView.ViewHolder {
        TextView routeName;

        RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeName = itemView.findViewById(R.id.route_name);
        }
    }
}