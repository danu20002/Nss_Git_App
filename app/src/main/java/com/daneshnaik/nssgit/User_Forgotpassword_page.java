package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;

public class User_Forgotpassword_page extends AppCompatActivity {
TextInputEditText Forgot_email;
AppCompatButton reset_button;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgotpassword_page);
        Forgot_email=findViewById(R.id.forgot_email);
        reset_button=findViewById(R.id.reset_forgot);
        auth=FirebaseAuth.getInstance();
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog=new ProgressDialog(User_Forgotpassword_page.this);
                progressDialog.setTitle("Reset Password");
                progressDialog.setMessage("Sending Reset Email to You.....");
                progressDialog.show();
                String Email_Reset=Forgot_email.getEditableText().toString().trim();
                if(!Email_Reset.isEmpty()){
                 auth.sendPasswordResetEmail(Email_Reset).addOnCompleteListener(new OnCompleteListener<Void>() {
                     @Override
                     public void onComplete(@NonNull Task<Void> task) {
                         Toast.makeText(User_Forgotpassword_page.this, "Check your Email ", Toast.LENGTH_SHORT).show();
                         progressDialog.dismiss();
                         startActivity(new Intent(getApplicationContext(),User_login_page.class));
                         finish();
                     }
                 }).addOnFailureListener(new OnFailureListener() {
                     @Override
                     public void onFailure(@NonNull Exception e) {
                         progressDialog.setMessage(e.getMessage());
                         new Handler().postDelayed(new Runnable() {
                             @Override
                             public void run() {
                                 Toast.makeText(User_Forgotpassword_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                 progressDialog.dismiss();
                             }
                         },2000);

                     }
                 });
                }
            }
        });
    }
}