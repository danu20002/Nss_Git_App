package com.danunaik.nssgit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.danunaik.Adapters.MessageAdapter;
import com.danunaik.Tables.Messges;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class chat extends AppCompatActivity {
    ImageView profile_image_chat;
    TextView name_chat, email_chat;
    EditText messge_carrier;
    ImageView send_btn;
    MessageAdapter adapter;
    ArrayList<Messges> messges;
    String senderRoom, ReciverROom;
    FirebaseDatabase database;
    RecyclerView recyclerView;
    Dialog dialog;
    String email_chat_got;
    String name_chat_got;
    String messge_typed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_chat);
        profile_image_chat = findViewById(R.id.imageview_chat);
        name_chat = findViewById(R.id.text_name_chat);
        email_chat = findViewById(R.id.text_email_chat);
        send_btn = findViewById(R.id.sendBtn);
        messge_carrier = findViewById(R.id.messageBox);
        recyclerView = findViewById(R.id.recyclerView);
        dialog = new Dialog(this);


        messges = new ArrayList<>();
        adapter = new MessageAdapter(this, messges);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        database = FirebaseDatabase.getInstance();


        Intent intent = getIntent();
        email_chat_got = intent.getStringExtra("email");
        name_chat_got = intent.getStringExtra("name");
        String image_taker = intent.getStringExtra("profile_image");
        String reciver_uid = intent.getStringExtra("uid");
        email_chat.setText(email_chat_got);
        name_chat.setText(name_chat_got);

        Glide.with(getApplicationContext()).load(image_taker).placeholder(R.drawable.baseline_person_24).into(profile_image_chat);


        String sender_uid = FirebaseAuth.getInstance().getUid();

        senderRoom = sender_uid + reciver_uid;
        ReciverROom = reciver_uid + sender_uid;


        database.getReference().child("chats").child(senderRoom)
                .child("messages").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messges.clear();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Messges messges1 = snapshot1.getValue(Messges.class);
                            messges1.setMessageId(snapshot1.getKey());
                            messges.add(messges1);
                        }
                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//profile_image_chat.setOnClickListener(new View.OnClickListener() {
//    @Override
//    public void onClick(View v) {
//       dialog.setContentView(R.layout.single_image_viewer);
//       ImageView imageView=findViewById(R.id.imageview_imagesetup);
//
//
//       dialog.show();
//    }
//});
        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messge_typed = messge_carrier.getText().toString().trim();
                if (!messge_typed.isEmpty()) {

                    Date date = new Date();
                    Messges message = new Messges(messge_typed, sender_uid, date.getTime());
                    messge_carrier.setText("");

                    HashMap<String, Object> lastmessage = new HashMap<>();
                    lastmessage.put("lastMsg", message.getMessage());
                    lastmessage.put("lastMsgTime", date.getTime());
                    database.getReference().child("chats").child(senderRoom).updateChildren(lastmessage);
                    database.getReference().child("chats").child(ReciverROom).updateChildren(lastmessage);


                    database.getReference().child("chats")
                            .child(senderRoom)
                            .child("messages")
                            .push()
                            .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    database.getReference().child("chats")
                                            .child(ReciverROom)
                                            .child("messages")
                                            .push()//to give random key for data
                                            .setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    sending_notification();
                                                }
                                            });

                                    HashMap<String, Object> lastmessage = new HashMap<>();
                                    lastmessage.put("lastMsg", message.getMessage());
                                    lastmessage.put("lastMsgTime", date.getTime());
                                    database.getReference().child("chats").child(senderRoom).updateChildren(lastmessage);
                                    database.getReference().child("chats").child(ReciverROom).updateChildren(lastmessage);
                                }
                            });
                    adapter.notifyDataSetChanged();
                }

            }


        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void sending_notification() {


        String name_chat_got_not=FirebaseAuth.getInstance().getCurrentUser().getEmail();
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
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email_chat_got));
            message.setSubject("Just got Message from   " +name_chat_got_not);
            message.setText(messge_typed);

            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }


}