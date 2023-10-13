package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.view.View;
import android.widget.Toast;

import com.daneshnaik.Tables.queriesTable;
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

        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
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
                ProgressDialog progressDialog=new ProgressDialog(Raise_query.this);
                progressDialog.setTitle("Query");
                progressDialog.setMessage("Submitting Query");
                progressDialog.show();
                String Email_data_query=email_raise_query.getEditableText().toString().trim();
                String Describe_data=describe_raise_query.getEditableText().toString().trim();
           new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {


           if(!Describe_data.isEmpty()){
               queriesTable table=new queriesTable(Email_data_query,Describe_data);
               database.getReference().child("Query").child(auth.getUid()).push().setValue(table).addOnCompleteListener(new OnCompleteListener<Void>() {
                   @Override
                   public void onComplete(@NonNull Task<Void> task) {
                       if(task.isComplete()){


                       progressDialog.setMessage("Query sent Successfully");
                       describe_raise_query.setText("");
                       describe_raise_query.clearFocus();
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
                                   message.setSubject("Query Raised");
                                   message.setText("Dear" + "  "+FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\n" +"We will try to contact You As much as possible"+"\n"+"\n"+"\n"+
                                          "\n"+ "Thank you for contacting Us"+"\n"+
                                           "NSS GIT"
                                   );


                                   Transport.send(message);

                               }catch (MessagingException e){
                                   throw new RuntimeException(e);
                               }
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
                               progressDialog.dismiss();
                           }
                       },2000);
                   }
               });
           }else{
               Toast.makeText(Raise_query.this, "please fill above data", Toast.LENGTH_SHORT).show();
               describe_raise_query.setText("Please fill this");
               progressDialog.dismiss();
           }
               }
           },2000);
            }
        });

    }
}