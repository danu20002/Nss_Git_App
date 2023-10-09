package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class updates extends AppCompatActivity {
    BottomNavigationView bottom_nav_updates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates);







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
}