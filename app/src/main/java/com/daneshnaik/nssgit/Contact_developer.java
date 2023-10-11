package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Contact_developer extends AppCompatActivity {
TextInputEditText email_contact_developer,description_contact_developer;
AppCompatButton submit_btn_contact_developer;
ImageView github_dev,insta_dev,x_dev,linkedin_dev;
FirebaseAuth auth;
FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_developer);
        email_contact_developer=findViewById(R.id.email_contact_developer);
        description_contact_developer=findViewById(R.id.description_contact_developer);
        submit_btn_contact_developer=findViewById(R.id.btn_submit_contact_developer);


       database= FirebaseDatabase.getInstance();
       auth= FirebaseAuth.getInstance();
       database.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               email_contact_developer.setText(snapshot.child("email").getValue().toString());
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        submit_btn_contact_developer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 ProgressDialog progressDialog=new ProgressDialog(Contact_developer.this);
                progressDialog.setTitle("Developer");
                progressDialog.setMessage("Sending Contact Information");
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        String email_data_contact_dev=email_contact_developer.getEditableText().toString().trim();
                        String description_contact_dev=description_contact_developer.getEditableText().toString().trim();
                        String username = "nssgitofficial@gmail.com";
                        String password = "mgscxgqmxsdgzygo";
                        Properties props = new Properties();
                        props.put("mail.smtp.auth", "true");
                        props.put("mail.smtp.SSL", "true");
                        props.put("mail.smtp.starttls.enable", "true");
                        props.put("mail.smtp.host", "smtp.gmail.com");
                        props.put("mail.smtp.SSL", "465");
                        props.put("mail.smtp.port", "587");
                        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                            @Override
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(username, password);
                            }
                        });
                        try {
                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(username));
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("naikdanesh2@gmail.com"));
                            message.setSubject("Contact Developer  " + FirebaseAuth.getInstance().getCurrentUser().getEmail());
                            message.setText("Mr.Danesh Naik "+"\n"+"Some one Trying to contact you"+"\n"+
                                    "Email:"+email_data_contact_dev+"\n"+
                                    "Telling this:"+"\n"+description_contact_dev+"\n"+"Thank You"+"\n"+"NSS GIT");

                            Transport.send(message);

                        } catch (MessagingException e) {
                            throw new RuntimeException(e);
                        }
                        description_contact_developer.setText("");
                        description_contact_developer.clearFocus();
                        progressDialog.setMessage("Sent Successfully");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                            }
                        },2000);


                    }
                },2000);







            }
        });

        github_dev=findViewById(R.id.github_developer);
        github_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/danu20002")));
            }
        });
        insta_dev=findViewById(R.id.instagram_developer);
        insta_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/danu_0002/")));
            }
        });
       x_dev=findViewById(R.id.x_developer);
       x_dev.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://twitter.com/danesh200002")));
           }
       });
       linkedin_dev=findViewById(R.id.linkedin_developer);
       linkedin_dev.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.linkedin.com/in/danesh-naik-74852a1b4/")));
           }
       });
    }
}