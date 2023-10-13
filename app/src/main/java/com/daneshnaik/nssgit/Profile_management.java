package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_management extends AppCompatActivity {
    BottomNavigationView bottom_nav_profile_management;
    CircleImageView profile_photo_settings;
    TextView profile_settings_changer,group_belongs_to,program_officer,about_redcross_settings,help_section_settings,raise_query_settings,bug_reports_settings,privacy_policy_settings,patch_notes_settings,contact_developer_settings,contact_us_settings,logout_settings;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_management);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        profile_photo_settings=findViewById(R.id.profile_toolbar_settings);

        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String profile_image_settings=snapshot.child("imageurl").getValue().toString();
                Glide.with(getApplicationContext()).load(profile_image_settings).placeholder(R.drawable.baseline_person_24).into(profile_photo_settings);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

 profile_settings_changer=findViewById(R.id.profile_settings_changer);
 profile_settings_changer.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         startActivity(new Intent(getApplicationContext(), Profile_settings.class));
         overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
     }
 });

 group_belongs_to=findViewById(R.id.group_belongs_to);
 group_belongs_to.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), Group_belong_to.class));
     }
 });


program_officer=findViewById(R.id.program_officer_settings);
   program_officer.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           startActivity(new Intent(getApplicationContext(), Program_officer.class));
       }
   });

about_redcross_settings=findViewById(R.id.about_red_cross_settings);
about_redcross_settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), About_redcross.class));
    }
});

 help_section_settings=findViewById(R.id.help_section_settings);
 help_section_settings.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         startActivity(new Intent(getApplicationContext(), Help_section.class));
     }
 });
 raise_query_settings=findViewById(R.id.raise_query_settings);
 raise_query_settings.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         startActivity(new Intent(getApplicationContext(), Raise_query.class));
     }
 });
 bug_reports_settings=findViewById(R.id.bug_reports_settings);
 bug_reports_settings.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         startActivity(new Intent(getApplicationContext(), Bug_reports.class));
     }
 });

privacy_policy_settings=findViewById(R.id.privacy_policy_settings);
privacy_policy_settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), Privacy_policy.class));
    }
});
patch_notes_settings=findViewById(R.id.patch_notes_settings);
patch_notes_settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), Patch_notes.class));
    }
});


contact_developer_settings=findViewById(R.id.contact_developer_settings);
contact_developer_settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(), Contact_developer.class));
    }
});

contact_us_settings=findViewById(R.id.contact_us_settings);
contact_us_settings.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),Contact_us.class));
    }
});













 logout_settings=findViewById(R.id.logout_settings);
 logout_settings.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View view) {
         AlertDialog.Builder dialog=new AlertDialog.Builder(Profile_management.this);
         dialog.setTitle("Log Out").setIcon(R.drawable.baseline_logout_24).setMessage("Are you sure! Want to Log Out?").setPositiveButton("YES", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 auth.signOut();
                 startActivity(new Intent(getApplicationContext(),User_login_page.class));
                 overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                 finish();
             }
         }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 Toast.makeText(Profile_management.this, "Sure Sure", Toast.LENGTH_SHORT).show();
             }
         });
         dialog.show();
     }
 });


























































        bottom_nav_profile_management=findViewById(R.id.bottom_nav_profile_management);
        bottom_nav_profile_management.setSelectedItemId(R.id.profile);
        bottom_nav_profile_management.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
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
                    startActivity(new Intent(getApplicationContext(), updates.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    return true;

                } else if (nav_id==R.id.profile) {

                    return true;

                }
                return true;
            }
        });
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Profile_management.this).setIcon(R.drawable.baseline_logout_24).setTitle("Exit the app").setMessage("Are you sure! want to exit app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Profile_management.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Profile_management.this, "Ok Fine️", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        alertDialog.show();

    }
}