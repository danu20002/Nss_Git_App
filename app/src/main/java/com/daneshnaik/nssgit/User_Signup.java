package com.daneshnaik.nssgit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.net.Uri;
import android.os.Bundle;

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
    }
}