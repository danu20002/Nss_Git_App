package com.danunaik.nssgit;

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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Contact_us extends AppCompatActivity {
    TextInputEditText email_contact_us;
    AppCompatButton btn_send_email;
    ImageView conatct_us_instagram,contact_us_x;
FirebaseAuth auth;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        email_contact_us=findViewById(R.id.email_contact_us);

        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email_contact_us.setText(snapshot.child("email").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btn_send_email=findViewById(R.id.btn_send_email);
        btn_send_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog=new ProgressDialog(Contact_us.this);
                progressDialog.setTitle("Send Email");
                progressDialog.setMessage("Sending Email About Contacting");
                progressDialog.show();
                String email_send_btn_data=email_contact_us.getEditableText().toString().trim();


                database.getReference().child("ContactUs").child(auth.getUid()).push().setValue(email_send_btn_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
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
                                        message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
                                        message.setSubject("Contact Us");
                                        message.setText("Dear" + "  "+FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\n" +"We will try to contact You As much as possible"+"\n"+
                                                "Thank you for contacting Us"+"\n"+
                                                "NSS GIT"
                                                );


                                        Transport.send(message);

                                    }catch (MessagingException e){
                                        throw new RuntimeException(e);
                                    }
                                    progressDialog.setMessage("Sent Email Successfully");
                                    Toast.makeText(Contact_us.this, "Sent Email Has been Recorded", Toast.LENGTH_SHORT).show();
                                    progressDialog.dismiss();
                                }
                            },2000);
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.setMessage(e.getMessage());
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(Contact_us.this, "Something Error Occurred ", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        },2000);
                    }
                });
            }
        });


        contact_us_x=findViewById(R.id.contact_us_x);
        conatct_us_instagram=findViewById(R.id.contact_us_instagram);
        contact_us_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://x.com/nssklsgit22?t=KvRkfNB3j_W7MdAqi7dyrw&s=09")));
            }
        });
        conatct_us_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://instagram.com/klsgit_nss?igshid=MmU2YjMzNjRlOQ==")));
            }
        });

    }
}