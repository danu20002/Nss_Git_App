package com.daneshnaik.nssgit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Signup extends AppCompatActivity {
    TextInputEditText full_name,email_signup,password_signup,confirm_signup;
    CircleImageView profile_pic;
    AppCompatButton signup_btn;
    Uri selectImage;

    FirebaseStorage storage;
    FirebaseDatabase database;

    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_signup);
        full_name=findViewById(R.id.full_name_signup);
        email_signup=findViewById(R.id.email_signup);
        password_signup=findViewById(R.id.password_signup);
        confirm_signup=findViewById(R.id.confirm_signup);
        profile_pic=findViewById(R.id.profile_image_signup);
        signup_btn=findViewById(R.id.signup_btn);

        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();


        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });










    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                profile_pic.setImageURI(data.getData());
                selectImage=data.getData();
            }
        }

    }
}