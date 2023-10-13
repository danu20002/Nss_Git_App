package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Raise_query extends AppCompatActivity {
TextInputEditText email_raise_query,describe_raise_query;
AppCompatButton btn_submit_raise_query;
FirebaseAuth auth;
FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_query);
        email_raise_query=findViewById(R.id.email_raise_query);
        describe_raise_query=findViewById(R.id.describe_raise_query);
        btn_submit_raise_query=findViewById(R.id.btn_submit_raise_query);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                   email_raise_query.setText(snapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btn_submit_raise_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}