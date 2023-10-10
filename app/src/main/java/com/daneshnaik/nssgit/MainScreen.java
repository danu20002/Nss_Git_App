package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.daneshnaik.Adapters.UserAdapter;
import com.daneshnaik.Tables.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainScreen extends AppCompatActivity {
BottomNavigationView bottom_nav_chats;
    RecyclerView recyclerView;

    FirebaseAuth auth;
    FirebaseDatabase database;


    UserAdapter adapter;
    ArrayList<Users> users;
    ProgressBar progress_mainscreen;

    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        recyclerView=findViewById(R.id.recyclerview_main);
        progress_mainscreen=findViewById(R.id.progress_mainscreen);


        searchView=findViewById(R.id.serach_bar_user);
        searchView.clearFocus();

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        users=new ArrayList<>();
        adapter=new UserAdapter(this,users);

        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rcy_animation);
        recyclerView.startAnimation(animation);
        recyclerView.setAdapter(adapter);
//         recyclerView.setVerticalScrollBarEnabled(true);
        adapter.notifyDataSetChanged();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        progress_mainscreen.setVisibility(View.VISIBLE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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



        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Users user=snapshot1.getValue(Users.class);

                    if (!user.getId().equals(FirebaseAuth.getInstance().getUid())) {
                        users.add(user);
                        progress_mainscreen.setVisibility(View.INVISIBLE);
                    }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });














        bottom_nav_chats=findViewById(R.id.bottom_nav_chats);
        bottom_nav_chats.setSelectedItemId(R.id.Chats);
        bottom_nav_chats.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int nav_id=item.getItemId();
                if(nav_id==R.id.Chats){
                    return  true;
                }else if(nav_id==R.id.dashboard){
                    startActivity(new Intent(getApplicationContext(), Dashboard.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    return true;

                } else if (nav_id==R.id.updates) {
                    startActivity(new Intent(getApplicationContext(), updates.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
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
        ArrayList<Users> filterlist=new ArrayList<>();
        for(Users users1: users){
            if(users1.getFullName().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(users1);
            } else if (users1.getEmail().toLowerCase().contains(text.toLowerCase())) {
                filterlist.add(users1);
            }
        }
        if(filterlist.isEmpty()){
            Toast.makeText(getApplicationContext(),"NO USER FOUND",Toast.LENGTH_SHORT).show();
        }else{
            adapter.setfilterdlist(filterlist);
        }

    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(MainScreen.this).setIcon(R.drawable.baseline_logout_24).setTitle("Exit the app").setMessage("Are you sure! want to exit app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainScreen.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainScreen.this, "Ok Fine️", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        alertDialog.show();

    }
}
