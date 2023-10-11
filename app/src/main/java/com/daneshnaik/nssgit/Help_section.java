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
import com.google.firebase.database.FirebaseDatabase;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Help_section extends AppCompatActivity {
TextInputEditText help_title,help_description;
AppCompatButton help_submit_btn;
FirebaseAuth auth;
FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_section);
        help_description=findViewById(R.id.help_description);
        help_title=findViewById(R.id.help_title);
        help_submit_btn=findViewById(R.id.help_submit_btn);
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();


        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        help_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               final ProgressDialog progressDialog=new ProgressDialog(Help_section.this);
                progressDialog.setTitle("Help?");
                progressDialog.setMessage("Submitting Your Request");
                progressDialog.show();
                String help_title_data=help_title.getEditableText().toString().trim();
                String help_desc_data=help_description.getEditableText().toString().trim();
                if(!help_title_data.isEmpty()){
                          if(!help_desc_data.isEmpty()){
                              queriesTable table=new queriesTable(help_title_data,help_desc_data);
                               database.getReference().child("Helps").child(auth.getCurrentUser().getUid()).push().setValue(table).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                       if(task.isComplete()){
                                           progressDialog.setMessage("Processing Your Request");
//                                           sending_notification();

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
                                               message.setSubject("About Help Asked");
                                               message.setText("Dear" + "  "+FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\n" +"\n"
                                                       +"We Recorded your Help We will solve it as soon as possible"+
                                                       "Your Help asked is As Follows"+
                                                       "\n"+
                                                       help_title_data+"\n"+
                                                       help_desc_data+"\n"+
                                                       "We Solve it asap"+"\n"+
                                                       "\n"+
                                                       "\n"+
                                                       "Thank You"+
                                                       "\n"+
                                                       "NSS GIT"

                                               );


                                               Transport.send(message);

                                           }catch (MessagingException e){
                                               throw new RuntimeException(e);
                                           }
                                           help_title.setText("");
                                           help_description.setText("");
                                           help_description.clearFocus();
                                           progressDialog.dismiss();
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
                          }else {
                              Toast.makeText(Help_section.this, "Please Describe your Request", Toast.LENGTH_SHORT).show();
                              progressDialog.dismiss();
                          }
                }else{
                    Toast.makeText(Help_section.this, "Please provide Title", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }



            }
        });

    }
//    public  void sending_notification(){
//        String username="nssgitofficial@gmail.com";
//        String password="mgscxgqmxsdgzygo";
//        Properties props=new Properties();
//        props.put("mail.smtp.auth","true");
//        props.put("mail.smtp.SSL","true");
//        props.put("mail.smtp.starttls.enable","true");
//        props.put("mail.smtp.host","smtp.gmail.com");
//        props.put("mail.smtp.SSL","465");
//        props.put("mail.smtp.port","587");
//
//        javax.mail.Session session= Session.getInstance(props, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(username,password);
//            }
//        });
//
//        try {
//            MimeMessage message=new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
//            message.setSubject("About Help Asked");
//            message.setText("Dear" + "  "+FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\n" +"\n"
//                    +"We Recorded your Help We will solve it as soon as possible"+
//                    "Your Help asked is As Follows"+
//                    help_title.toString()+"\n"+
//                    help_description.toString()+"\n"+
//                    "We Solve it asap"+"\n"+
//                    "\n"+
//                    "\n"+
//                    "Thank You"+
//                    "\n"+
//                    "NSS GIT"
//
//                    );
//
//
//            Transport.send(message);
//
//        }catch (MessagingException e){
//            throw new RuntimeException(e);
//        }
//
//    }

}