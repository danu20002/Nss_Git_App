package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User_login_page extends AppCompatActivity {
    TextView new_account,forgot_password;
    TextInputEditText Email_login,password_login;
    AppCompatButton login_btn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login_page);
        new_account=findViewById(R.id.text_new_account);
        Email_login=findViewById(R.id.email_login);
        password_login=findViewById(R.id.password_login);
        login_btn=findViewById(R.id.login_btn);
        auth=FirebaseAuth.getInstance();

        if (auth.getCurrentUser()!=null && auth.getCurrentUser().isEmailVerified()){
            startActivity(new Intent(User_login_page.this,MainActivity.class));
            finish();
        }
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email_log=Email_login.getEditableText().toString().trim();
                String Password=password_login.getEditableText().toString().trim();
                final ProgressDialog progressDialog=new ProgressDialog(User_login_page.this);
                progressDialog.setTitle("Verifying");
                progressDialog.setMessage("Are You Verified Mail?");
                progressDialog.show();
                if(!Email_log.isEmpty()){
                    if(!Password.isEmpty()){
                        auth.signInWithEmailAndPassword(Email_log,Password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                if(auth.getCurrentUser().isEmailVerified()){
                                    progressDialog.setTitle("Good! Verified");
                                    progressDialog.setMessage("Verified SuccessFully");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            intent.putExtra("email",Email_log);
                                            startActivity(intent);
                                            finish();
                                        }
                                    },2000);


                                }else{
                                    progressDialog.setTitle("Verification Failed");
                                    progressDialog.setMessage("Your Mail is not verified yet");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                        }
                                    },3000);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.setMessage(e.getMessage());
                                Toast.makeText(User_login_page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        });
                    }else{
                        Toast.makeText(User_login_page.this, "Enter the Password", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(User_login_page.this, "Enter the Email", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_login_page.this, User_Signup.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        forgot_password=findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),com.daneshnaik.nssgit.User_Forgotpassword_page.class));
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });

    }
}