package com.danunaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.danunaik.Tables.updates_table;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class updates_taker extends AppCompatActivity {
    TextInputLayout Title,description,personal_email;
    AppCompatButton send_update_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updates_taker);
        Title=findViewById(R.id.title_updates);
        description=findViewById(R.id.description_updates);
        personal_email=findViewById(R.id.email_updates);
        send_update_btn= findViewById(R.id.send_update_btn);
        final String senderId= FirebaseAuth.getInstance().getCurrentUser().getEmail();

        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        send_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog=new ProgressDialog(updates_taker.this);
                progressDialog.setTitle("Uploading Data");
                progressDialog.setMessage("Please wait uploading Data");
                progressDialog.show();
                String email_data=personal_email.getEditText().getText().toString().trim();
                String title_data=Title.getEditText().getText().toString().trim();
                String description_data=description.getEditText().getText().toString().trim();
                if(!email_data.isEmpty()){
                    if(!title_data.isEmpty()){
                        if(!description_data.isEmpty()){
                            final updates_table updatesTable=new updates_table(title_data,description_data,email_data);

                            FirebaseDatabase.getInstance().getReference().child("Updates").push().setValue(updatesTable).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    sending_notification();
                                    progressDialog.dismiss();
                                    Toast.makeText(updates_taker.this, "Data stored successfully", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(updates_taker.this,updates.class));

//                                    String[] emails=new String[]{"nssgitofficial@gmail.com"};
//
//                                    Intent intent=new Intent(Intent.ACTION_SEND);
//                                    intent.putExtra(Intent.EXTRA_EMAIL,emails);
//                                    intent.putExtra(Intent.EXTRA_SUBJECT,title_data);
//                                    intent.putExtra(Intent.EXTRA_TEXT,description_data);
//                                    intent.setType("message/rfc822");
//                                    startActivity(Intent.createChooser(intent,"choose to send "));

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(updates_taker.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            });



                        }else{
                            Toast.makeText(updates_taker.this, "Please Enter description", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }else{
                        Toast.makeText(updates_taker.this, "Please provide title", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }else{
                    Toast.makeText(updates_taker.this, "Please Provide Email", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }
    public  void sending_notification(){

        String Title_notify=Title.getEditText().toString().trim();
        String description_notify=description.getEditText().toString().trim();
        String username="nssgitofficial@gmail.com";
        String password="mgscxgqmxsdgzygo";
        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.SSL","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.SSL","465");
        props.put("mail.smtp.port","587");

        javax.mail.Session session= Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try {
            MimeMessage message=new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse("nssgitofficial@gmail.com"));
            message.setSubject("New update Details");
            message.setText("Hello Admin This is new update added in updates section\n\n\n\n"+
                    Title_notify+"\n"+description_notify+

                    "\n"+"Thank you\n\n "+"NSS GIT");


            Transport.send(message);

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }
}