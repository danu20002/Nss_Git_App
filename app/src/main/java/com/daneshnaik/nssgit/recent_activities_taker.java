package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.daneshnaik.Tables.recent_activities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class recent_activities_taker extends AppCompatActivity {
TextInputEditText activity_recent;
AppCompatButton btn_add_activity;
FirebaseAuth auth;
FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent_activities_taker);
        activity_recent=findViewById(R.id.activity_text);
        btn_add_activity=findViewById(R.id.btn_add_activity);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        btn_add_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(recent_activities_taker.this);
                progressDialog.setTitle("Uploading");
                progressDialog.setMessage("Data being Uploaded");
                progressDialog.show();
                String activity_text_data=activity_recent.getEditableText().toString();
                if(!activity_text_data.isEmpty()){
                    String activity_rrecnts_ka_data=activity_recent.getEditableText().toString();
                    recent_activities recentActivities=new recent_activities(activity_rrecnts_ka_data);
                    database.getReference().child("recents").push().setValue(recentActivities).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()){

                                progressDialog.setMessage("Just a Sec.....  uploaded");
                                activity_recent.setText("");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.setMessage("Uploaded");
                                        Toast.makeText(recent_activities_taker.this, "Recent activity is added", Toast.LENGTH_SHORT).show();
                                        progressDialog.dismiss();;
                                    }
                                },2000);

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(recent_activities_taker.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    Toast.makeText(recent_activities_taker.this, "Add Some text", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
}