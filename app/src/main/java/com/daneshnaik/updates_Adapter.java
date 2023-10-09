package com.daneshnaik;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.daneshnaik.Tables.updates_table;
import com.daneshnaik.nssgit.R;

import java.util.ArrayList;

public class updates_Adapter extends RecyclerView.Adapter<updates_Adapter.ViewHolder> {
    Context context;
    ArrayList<updates_table> tablesArrayList;
    public updates_Adapter(Context context,ArrayList<updates_table> tablesArrayList){
        this.context=context;
        this.tablesArrayList=tablesArrayList;
    }
    public  void setfilterdupdatelist(ArrayList<updates_table> filterlist) {
        this.tablesArrayList = filterlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public updates_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_updater,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull updates_Adapter.ViewHolder holder, int position) {
         updates_table UpdatesTable=tablesArrayList.get(position);
         holder.updates_title.setText(UpdatesTable.getTitle());
         holder.updates_descript.setText(UpdatesTable.getDescription());
         holder.updates_gmail.setText(UpdatesTable.getUpdates_Email());
    }

    @Override
    public int getItemCount() {
        return tablesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView updates_gmail,updates_title,updates_descript;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            updates_gmail=itemView.findViewById(R.id.text_updates_gmail);
            updates_title=itemView.findViewById(R.id.text_updates_title);
            updates_descript=itemView.findViewById(R.id.text_updates_description);
        }
    }
}
