package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.AnimationTypes;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {
    BottomNavigationView bottom_nav_dashboard;
    FirebaseRemoteConfig remoteConfig;
    ImageSlider imageSlider;

    ProgressBar progressBar_dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
       progressBar_dashboard=findViewById(R.id.progressbar_dashboard);

      imageSlider=findViewById(R.id.image_slider_dashboard);
      ArrayList<SlideModel> slideModels=new ArrayList<>();
      slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/P7.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/P6.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/P1.JPG", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/pic3.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/P1.JPG", ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/pic4.jpg",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/P6.jpg",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/P5.jpg",ScaleTypes.FIT));
        slideModels.add(new SlideModel("https://nss--nssklsgit20.repl.co/static/IMG/plant.jpg",ScaleTypes.FIT));
        imageSlider.setSlideAnimation(AnimationTypes.BACKGROUND_TO_FOREGROUND);
        imageSlider.setImageList(slideModels,ScaleTypes.FIT);











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