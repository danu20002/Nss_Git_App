package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainScreen extends AppCompatActivity {
BottomNavigationView bottom_nav_chats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);












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
}