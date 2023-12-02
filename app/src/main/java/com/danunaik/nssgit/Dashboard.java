package com.danunaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.danunaik.Adapters.RecentActivitiesAdapter;
import com.danunaik.Tables.recent_activities;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    BottomNavigationView bottom_nav_dashboard;
    FirebaseRemoteConfig remoteConfig;
    ImageSlider imageSlider;
    CardView Our_website_cardview;
  WebView dashboard_our_website;
    ProgressBar progressBar_dashboard;
    TextView visit_here,group_belongs_dashboard;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ImageView edit_recent_activities_image;

    RecentActivitiesAdapter activitiesAdapter;
   ArrayList <recent_activities> recent_activitiesArrayList;
   RecyclerView recyclerView_recent;
   int id=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
       progressBar_dashboard=findViewById(R.id.progressbar_dashboard);
       auth=FirebaseAuth.getInstance();
       database=FirebaseDatabase.getInstance();

      imageSlider=findViewById(R.id.image_slider_dashboard);
      ArrayList<SlideModel> slideModels=new ArrayList<>();

        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/P6.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2F20230906_32019pmByGPSMapCamera.jpg?alt=media&token=3ee6cb64-efaf-417e-a19c-7db8f0d5236b&_gl=1*3fcdt7*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODIxNS4yNC4wLjA.", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2F20230906_31225pmByGPSMapCamera.jpg?alt=media&token=b58e539d-31ba-4227-8860-c6215442332a&_gl=1*xs30tv*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODM1OC4yOS4wLjA.", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2F20230906_30943pmByGPSMapCamera.jpg?alt=media&token=d33a5d31-67a6-4b65-a389-6c5f92841514&_gl=1*h2uik*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODQwNi40Ni4wLjA.", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2F20230906_32243pmByGPSMapCamera.jpg?alt=media&token=d17125b5-aa93-4d59-bd90-1912a31afda5&_gl=1*c2sfrb*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODQyNy4yNS4wLjA.",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2FSAVE_20230907_185058.jpg?alt=media&token=1a236144-ee94-4f49-8734-c9b120dd34cc&_gl=1*1svs6rl*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODQ1My42MC4wLjA.",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2FSAVE_20230907_185148.jpg?alt=media&token=a7a7553e-6fbc-47d9-a3f6-23c0b913d347&_gl=1*1eu0r81*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODQ3Ni4zNy4wLjA.",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2FSAVE_20230907_185233.jpg?alt=media&token=0f2d8dc7-724a-4908-b594-0c909abecfb9&_gl=1*16kyy3*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODUwMC4xMy4wLjA.",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/for-additional-purpose.appspot.com/o/nss%20git%2FSAVE_20230907_185534.jpg?alt=media&token=b8326004-8098-413a-8b4f-6fd6f3ceff83&_gl=1*11wzyys*_ga*MTQ2NjU5MDAwMC4xNjk3NTEwMjgz*_ga_CW55HF8NVT*MTY5Nzc1NjU0Ny41LjEuMTY5Nzc1ODUxOC42MC4wLjA.",ScaleTypes.FIT));

        imageSlider.setSlideAnimation(AnimationTypes.BACKGROUND_TO_FOREGROUND);
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);



      dashboard_our_website=findViewById(R.id.our_website_webview_dashboard);
        WebSettings settings=dashboard_our_website.getSettings();
        settings.setJavaScriptEnabled(true);
        dashboard_our_website.loadUrl("https://nss--nssklsgit20.repl.co/");


        Our_website_cardview=findViewById(R.id.our_website_cardview);
        Our_website_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://nss--nssklsgit20.repl.co/")));
            }
        });


 visit_here=findViewById(R.id.visit_here_dashboard);
 visit_here.setOnClickListener(new View.OnClickListener() {
     @Override
     public void onClick(View v) {
         startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://nss--nssklsgit20.repl.co/")));
     }
 });
group_belongs_dashboard=findViewById(R.id.group_belongs_dashboard);

database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        group_belongs_dashboard.setText(" Group : "+snapshot.child("group").getValue().toString());
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {
        Toast.makeText(Dashboard.this, error.getMessage(), Toast.LENGTH_SHORT).show();
    }
});



edit_recent_activities_image=findViewById(R.id.edit_recent_activities_image);
if(FirebaseAuth.getInstance().getCurrentUser().getEmail().matches("nssgitofficial@gmail.com")){
    edit_recent_activities_image.setVisibility(View.VISIBLE);
}else {
    edit_recent_activities_image.setVisibility(View.INVISIBLE);
}
edit_recent_activities_image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      startActivity(new Intent(getApplicationContext(), recent_activities_taker.class));
    }
});
recent_activitiesArrayList=new ArrayList<>();
activitiesAdapter=new RecentActivitiesAdapter(this,recent_activitiesArrayList);
recyclerView_recent=findViewById(R.id.recent_activities_recyclerview);

recyclerView_recent.setAdapter(activitiesAdapter);

        recyclerView_recent.smoothScrollToPosition(0);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView_recent.setLayoutManager(layoutManager);
       recyclerView_recent.smoothScrollBy(0,5);

database.getReference().child("recents").addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        recent_activitiesArrayList.clear();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            recent_activities tables = dataSnapshot.getValue(recent_activities.class);
            recent_activitiesArrayList.add(tables);
//
//            if(snapshot.exists()){
//                id=(int) snapshot.getChildrenCount();
//            }

        }
        activitiesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});






        remoteConfig=FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings=new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(5)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);

        remoteConfig.fetchAndActivate().addOnCompleteListener(new OnCompleteListener<Boolean>() {
            @Override
            public void onComplete(@NonNull Task<Boolean> task) {
                if (task.isSuccessful()){
                    String new_version_Code=remoteConfig.getString("new_version_code");

                    if(Integer.parseInt(new_version_Code) > getCurrentVersioncode()){
                        showUpdateDialog();
                    }

                }
            }
        });



        bottom_nav_dashboard=findViewById(R.id.bottom_nav_dashboard);
        bottom_nav_dashboard.setSelectedItemId(R.id.dashboard);
        bottom_nav_dashboard.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int nav_id=item.getItemId();
                if(nav_id==R.id.Chats){
                    startActivity(new Intent(getApplicationContext(), MainScreen.class));
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finish();
                    return true;
                }else if(nav_id==R.id.dashboard){

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
    private void showUpdateDialog() {
        final AlertDialog dialog=new AlertDialog.Builder(Dashboard.this).setTitle("Update App").setMessage("New App version Available size:1.7MB").setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try{
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.daneshnaik.nssgit")));
                }catch (Exception e){
                    Toast.makeText(Dashboard.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"ok new features discarded",Toast.LENGTH_LONG).show();
            }
        }).show();

    }

    private int getCurrentVersioncode(){
        PackageInfo packageInfo=null;
        try{
            packageInfo= getPackageManager().getPackageInfo(getPackageName(),0);

        }catch (Exception e){
            Toast.makeText(this, "Not working", Toast.LENGTH_SHORT).show();
        }
        return packageInfo.versionCode;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Dashboard.this).setIcon(R.drawable.baseline_logout_24).setTitle("Exit the app").setMessage("Are you sure! want to exit app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Dashboard.super.onBackPressed();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Dashboard.this, "Ok Fine️", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        alertDialog.show();

    }







}