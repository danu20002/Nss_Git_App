package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daneshnaik.Tables.updates_table;
import com.daneshnaik.Adapters.updates_Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class updates extends AppCompatActivity {
    BottomNavigationView bottom_nav_updates;
    FloatingActionButton floatingActionButton_updates;

    androidx.appcompat.widget.SearchView searchView_updates;
   updates_Adapter adapter;
   RecyclerView recyclerview_updates;

    int id =0;
    ArrayList<updates_table> UpdatesArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);

        floatingActionButton_updates=findViewById(R.id.floating_btn_update);

        if(FirebaseAuth.getInstance().getCurrentUser().getEmail() != "nssgitofficial@gmail.com"){
              floatingActionButton_updates.setVisibility(View.INVISIBLE);
        }else{
            floatingActionButton_updates.setVisibility(View.VISIBLE);
        }


       floatingActionButton_updates.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(), updates_taker.class));

           }
       });


        final String senderId = FirebaseAuth.getInstance().getUid();
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        UpdatesArrayList = new ArrayList<>();
        adapter = new updates_Adapter(this, UpdatesArrayList);

        recyclerview_updates = findViewById(R.id.recyclerview_updates);

        recyclerview_updates.setAdapter(adapter);
        recyclerview_updates.smoothScrollToPosition(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerview_updates.setLayoutManager(layoutManager);


        database.getReference().child("Updates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UpdatesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    updates_table tables = dataSnapshot.getValue(updates_table.class);
                    UpdatesArrayList.add(tables);

                    if(snapshot.exists()){
                        id=(int) snapshot.getChildrenCount();
                    }

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        searchView_updates=findViewById(R.id.searchview_updates);
        searchView_updates.clearFocus();

    searchView_updates.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            filterList(newText);
            return true;
        }
    });



















        bottom_nav_updates=findViewById(R.id.bottom_nav_updates);
        bottom_nav_updates.setSelectedItemId(R.id.updates);
        bottom_nav_updates.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int nav_id=item.getItemId();
                if(nav_id==R.id.Chats){
                    startActivity(new Intent(getApplicationContext(), MainScreen.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    return  true;
                }else if(nav_id==R.id.dashboard){
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    return true;

                } else if (nav_id==R.id.updates) {

                    return true;

                } else if (nav_id==R.id.profile) {
                    startActivity(new Intent(getApplicationContext(), Profile_management.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    return true;

                }
                return true;
            }
        });
    }
    private  void filterList(String text){
        ArrayList<updates_table> filterlist=new ArrayList<>();
        for(updates_table users1: UpdatesArrayList){
            if(users1.getTitle().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(users1);
            } else if (users1.getDescription().toLowerCase().contains(text.toLowerCase())) {
                filterlist.add(users1);
            }
        }
        if(filterlist.isEmpty()){
            Toast.makeText(getApplicationContext(),"NO Update FOUND",Toast.LENGTH_SHORT).show();
        }else{
            adapter.setfilterdupdatelist(filterlist);
        }

    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(updates.this).setIcon(R.drawable.baseline_logout_24).setTitle("Exit the app").setMessage("Are you sure! want to exit app?")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updates.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(updates.this, "Ok Fine️", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        alertDialog.show();

    }
}