package com.daneshnaik.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daneshnaik.Tables.recent_activities;
import com.daneshnaik.nssgit.R;

import java.util.ArrayList;

public class RecentActivitiesAdapter extends RecyclerView.Adapter<RecentActivitiesAdapter.Viewholder> {
    Context context;
    ArrayList<recent_activities> recent_activitiesArrayList;
    public RecentActivitiesAdapter(Context context,ArrayList<recent_activities> recent_activitiesArrayList){
        this.context=context;
        this.recent_activitiesArrayList=recent_activitiesArrayList;
    }
    @NonNull
    @Override
    public RecentActivitiesAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view= LayoutInflater.from(context).inflate(R.layout.single_recent_activities,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentActivitiesAdapter.Viewholder holder, int position) {
    recent_activities recent_activites_present=recent_activitiesArrayList.get(position);
    holder.single_recent_activities_textview.setText(recent_activites_present.getText()+"---->Added By NSS GIT");
    }

    @Override
    public int getItemCount() {
        return recent_activitiesArrayList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView single_recent_activities_textview;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            single_recent_activities_textview=itemView.findViewById(R.id.single_recent_activities_textview);
        }
    }
}
