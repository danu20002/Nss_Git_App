package com.daneshnaik.nssgit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.daneshnaik.Tables.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import de.hdodenhof.circleimageview.CircleImageView;

public class User_Signup extends AppCompatActivity {
    TextInputEditText full_name,email_signup,password_signup,confirm_signup,mobile_number_signup;
    CircleImageView profile_pic;
    AppCompatButton signup_btn;
    CheckBox checkbox_signup;
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
        mobile_number_signup=findViewById(R.id.mobile_num_signup);
        password_signup=findViewById(R.id.password_signup);
        confirm_signup=findViewById(R.id.confirm_signup);
        profile_pic=findViewById(R.id.profile_image_signup);
        checkbox_signup=findViewById(R.id.checkbox_signup);
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

if(checkbox_signup.isChecked()){

    signup_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String FullName=full_name.getEditableText().toString().trim();
            String Email=email_signup.getEditableText().toString().trim();
            String Mobile_Number_signup=mobile_number_signup.getEditableText().toString().trim();
            String Password=password_signup.getEditableText().toString().trim();
            String Confirm_password=confirm_signup.getEditableText().toString().trim();
              if(!FullName.isEmpty()){
                     if(!Email.isEmpty()){
                         if(!Mobile_Number_signup.isEmpty()){
                             if(!Password.isEmpty()){
                                 if(!Confirm_password.isEmpty()){
                                     if(selectImage!=null){

                                        if(Password.equals(Confirm_password)){
                                         auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                             @Override
                                             public void onComplete(@NonNull Task<AuthResult> task) {
                                                 if(task.isSuccessful()){
                                                     final ProgressDialog progressDialog=new ProgressDialog(User_Signup.this);
                                                     progressDialog.setTitle("Uploading");
                                                     progressDialog.show();
                                                     auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {
                                                             Toast.makeText(User_Signup.this, "User Created Successfully", Toast.LENGTH_SHORT).show();
                                                             StorageReference reference=storage.getReference().child("profiles").child(auth.getUid());
                                                             reference.putFile(selectImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                                                     if(task.isComplete()){
                                                                         reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                                             @Override
                                                                             public void onSuccess(Uri uri) {
                                                                                 String imageurl=uri.toString();
                                                                                 String uid=auth.getUid();
                                                                                 String name=full_name.getEditableText().toString().trim();

                                                                                 String email=email_signup.getEditableText().toString().trim();
                                                                                 String mobile_number=mobile_number_signup.getEditableText().toString().trim();
                                                                                 String password=password_signup.getEditableText().toString().trim();
                                                                                 Users users=new Users(uid,name,email,mobile_number,password,imageurl);
                                                                                 progressDialog.setMessage(" See the Email and Verify");
                                                                                 database.getReference().child("Users").child(uid).setValue(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                     @Override
                                                                                     public void onSuccess(Void unused) {
                                                                                         new Handler().postDelayed(new Runnable() {
                                                                                             @Override
                                                                                             public void run() {

                                                                                                 Toast.makeText(User_Signup.this, "All Data uploaded", Toast.LENGTH_SHORT).show();
                                                                                                 progressDialog.setMessage("Just a Second please");
                                                                                                 sending_notification();
                                                                                                 progressDialog.setMessage("All Data Uploaded");
                                                                                                 progressDialog.dismiss();
                                                                                                 Intent intent=new Intent(User_Signup.this,User_login_page.class);
                                                                                                 startActivity(intent);
                                                                                                 finish();
                                                                                             }
                                                                                         },2000);
                                                                                     }
                                                                                 }).addOnFailureListener(new OnFailureListener() {
                                                                                     @Override
                                                                                     public void onFailure(@NonNull Exception e) {
                                                                                         progressDialog.dismiss();
                                                                                         Toast.makeText(User_Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                     }
                                                                                 });

                                                                             }
                                                                         }).addOnFailureListener(new OnFailureListener() {
                                                                             @Override
                                                                             public void onFailure(@NonNull Exception e) {
                                                                                 progressDialog.dismiss();
                                                                                 Toast.makeText(User_Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                             }
                                                                         });
                                                                     }
                                                                 }
                                                             }).addOnFailureListener(new OnFailureListener() {
                                                                 @Override
                                                                 public void onFailure(@NonNull Exception e) {
                                                                     progressDialog.dismiss();
                                                                     Toast.makeText(User_Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                 }
                                                             });
                                                         }
                                                     }).addOnFailureListener(new OnFailureListener() {
                                                         @Override
                                                         public void onFailure(@NonNull Exception e) {
                                                             progressDialog.dismiss();
                                                             Toast.makeText(User_Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                         }
                                                     });
                                                 }
                                             }
                                         }).addOnFailureListener(new OnFailureListener() {
                                             @Override
                                             public void onFailure(@NonNull Exception e) {

                                                 Toast.makeText(User_Signup.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                             }
                                         });
                                        }else{
                                         Toast.makeText(User_Signup.this, "passwords Not Matching", Toast.LENGTH_SHORT).show();
                                        }
                                     }else {
                                         Toast.makeText(User_Signup.this, "Please select Image", Toast.LENGTH_SHORT).show();
                                     }
                                 }else{
                                     confirm_signup.setError("Confirm Your password");
                                 }
                             }else{
                                 password_signup.setError("Enter password");
                             }
                         }else{
                             mobile_number_signup.setError("Enter Mobile Number");
                         }
                     }else{
                         email_signup.setError("Enter Email");
                     }
              }else{
                  full_name.setError("Enter Name");
              }
        }
    });

}else {
    Toast.makeText(this, "Do You Read Term s and Conditions?", Toast.LENGTH_SHORT).show();
}





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

    public  void sending_notification(){
        String username="collagebuddy111@gmail.com";
        String password="rvorsglrrmacwhck";
        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.SSL","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.SSL","465");
        props.put("mail.smtp.port","587");

        Session session=Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try {
            MimeMessage message=new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
            message.setSubject("Welcome to JobAvailableApp");
            message.setText("Dear" + "  "+FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\n" +

                    "\n" +
                    "Hi [user name],\n" +
                    "\n" +
                    "Welcome to JobAvailableApp We're so excited to have you on board.\n" +
                    "\n" +
                    "[App name] is a [app description] that helps you [list of benefits]. Whether you're looking to [benefit 1], [benefit 2], or [benefit 3], [app name] has you covered.\n" +
                    "\n" +
                    "To get started, simply download the app from the [App Store or Google Play] and create an account. Once you're logged in, you can start exploring all that [app name] has to offer.\n" +
                    "\n" +
                    "Here are a few tips to help you get the most out of [app name]:\n" +
                    "\n" +
                    "Explore the different features. Take some time to browse the app and learn about all the different things you can do with it. There are lots of hidden gems, so don't be afraid to experiment.\n" +
                    "Customize your experience. [App name] allows you to tailor the experience to your specific needs and interests. Be sure to update your profile and settings so that you're getting the most out of the app.\n" +
                    "Connect with other users. [App name] has a thriving community of users who are always happy to help and support each other. Join the forums or chat groups to connect with other people who are using [app name] for the same things you are.\n" +
                    "We're always working to improve [app name], so please feel free to send us feedback or suggestions. You can reach us at [email address] or [social media links].\n" +
                    "\n" +
                    "Thanks again for joining the [app name] community!\n" +
                    "\n" +
                    "Sincerely,\n" +
                    "The [app name] team");


            Transport.send(message);

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }
}