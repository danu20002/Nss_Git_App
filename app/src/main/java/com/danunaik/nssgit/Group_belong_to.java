package com.danunaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Group_belong_to extends AppCompatActivity {
RadioButton annapurana_group,brahmagiri_group,vindhya_group,aravali_group,palani_group,himalaya_group,nilagiri_group,satpura_group,sahyadri_group,kailash_group;
AppCompatButton group_submit_btn;
FirebaseAuth auth;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_belong_to);
        annapurana_group=findViewById(R.id.annapurna_group);
        brahmagiri_group=findViewById(R.id.bhramagiri_group);
        vindhya_group=findViewById(R.id.vindya_group);
        aravali_group=findViewById(R.id.aravali_group);
       palani_group=findViewById(R.id.palani_group);
       himalaya_group=findViewById(R.id.himalaya_group);
        nilagiri_group=findViewById(R.id.nilagiri_group);
       satpura_group=findViewById(R.id.satpura_group);
        sahyadri_group=findViewById(R.id.sahyaderi_group);
        kailash_group=findViewById(R.id.kailash_group);
        group_submit_btn=findViewById(R.id.group_submit_btn);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        group_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(Group_belong_to.this);
                progressDialog.setTitle("Group");
                progressDialog.setMessage("Updating Group Info");
                progressDialog.show();
                String group_value;

                if(annapurana_group.isChecked()){
                    group_value="Annapurna";
                } else if (brahmagiri_group.isChecked()) {
                    group_value="Brahmagiri";
                } else if (vindhya_group.isChecked()) {
                    group_value="Vindhya";
                } else if (aravali_group.isChecked()) {
                    group_value="Aravali";
                } else if (palani_group.isChecked()) {
                    group_value="Palani";
                } else if (himalaya_group.isChecked()) {
                    group_value="Himalaya";
                } else if (nilagiri_group.isChecked()) {
                    group_value="Nilairi";
                } else if (satpura_group.isChecked()) {
                    group_value="Satpura";
                } else if (sahyadri_group.isChecked()) {
                    group_value="Sahyadri";
                }else {
                    group_value="Kailash";
                }
           if(!group_value.isEmpty()){
               FirebaseDatabase.getInstance().getReference().child("Users").child(auth.getUid()).child("group").setValue(group_value).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {


                       if (task.isComplete()){
                           new Handler().postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   progressDialog.dismiss();
                                   startActivity(new Intent(getApplicationContext(),Profile_management.class));
                                   finish();

                               }
                           },3000);


                       }
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       progressDialog.setMessage(e.getMessage());
                       new Handler().postDelayed(new Runnable() {
                           @Override
                           public void run() {
                               progressDialog.dismiss();
                               Toast.makeText(Group_belong_to.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                           }
                       },2000);

                   }
               });
           }else {
               progressDialog.setMessage("Please select one input");
               new Handler().postDelayed(new Runnable() {
                   @Override
                   public void run() {
                       progressDialog.dismiss();
                       Toast.makeText(Group_belong_to.this, "Select one Input", Toast.LENGTH_SHORT).show();
                   }
               },1000);
           }


            }
        });




    }
}