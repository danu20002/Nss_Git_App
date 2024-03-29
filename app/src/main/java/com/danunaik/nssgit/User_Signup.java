package com.danunaik.nssgit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.danunaik.Tables.Users;
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

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

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
    TextView terms_and_conditions;

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
        terms_and_conditions=findViewById(R.id.term_and_conditions_signup);
        signup_btn=findViewById(R.id.signup_btn);

        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();
        database=FirebaseDatabase.getInstance();




        if(Build.VERSION.SDK_INT>9){
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        profile_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });



    signup_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            final ProgressDialog progressDialog=new ProgressDialog(User_Signup.this);
            progressDialog.setTitle("Uploading");
            progressDialog.setMessage("Compressing Data");
            progressDialog.show();
            String FullName=full_name.getEditableText().toString().trim();
            String Email=email_signup.getEditableText().toString().trim();
            String Mobile_Number_signup=mobile_number_signup.getEditableText().toString().trim();
            String Password=password_signup.getEditableText().toString().trim();
            String Confirm_password=confirm_signup.getEditableText().toString().trim();
              if(!FullName.isEmpty()){
                     if(!Email.isEmpty()){
                         if(!Mobile_Number_signup.isEmpty() && Pattern.matches("^[0-9]{10}$",Mobile_Number_signup)){
                             if(!Password.isEmpty()){
                                 if(!Confirm_password.isEmpty()){
                                     if(selectImage!=null){

                                        if(Password.equals(Confirm_password)){
                                            if(checkbox_signup.isChecked()){
                                                signup_btn.setBackground(getDrawable(R.drawable.btn_background));
                                         auth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                             @Override
                                             public void onComplete(@NonNull Task<AuthResult> task) {
                                                 if(task.isSuccessful()){

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
                                                                                 String Group="not mentioned yet";
                                                                                 Users users=new Users(uid,name,email,mobile_number,password,imageurl,Group);
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
                                                                                                 add_ItemtoSheet();
                                                                                                 progressDialog.setMessage("All the  Data Uploaded");
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
                                                 progressDialog.dismiss();
                                             }
                                         });

                                            }else {
                                                Toast.makeText(User_Signup.this, "Did You Read Terms and Conditions?", Toast.LENGTH_SHORT).show();
                                                progressDialog.dismiss();
                                            }

                                        }else{
                                         Toast.makeText(User_Signup.this, "passwords Not Matching", Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }
                                     }else {
                                         Toast.makeText(User_Signup.this, "Please select Image", Toast.LENGTH_SHORT).show();
                                         progressDialog.dismiss();
                                     }
                                 }else{
                                     confirm_signup.setError("Confirm Your password");
                                     progressDialog.dismiss();
                                 }
                             }else{
                                 password_signup.setError("Enter password");
                                 progressDialog.dismiss();
                             }
                         }else{
                             mobile_number_signup.setError("Enter Mobile Number Correctly");
                             progressDialog.dismiss();
                         }
                     }else{
                         Toast.makeText(User_Signup.this, "Please provide College Email ", Toast.LENGTH_SHORT).show();
                         email_signup.setError("Enter Email");
                         progressDialog.dismiss();
                     }
              }else{
                  full_name.setError("Enter Name");
                  progressDialog.dismiss();
              }
        }
    });

terms_and_conditions.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.termsandcondiitionssample.com/live.php?token=swCDADyAeJmYbMsTUBHkfDhSXEkIcKGr")));
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




    public  void sending_notification(){
        String username="nssgitofficial@gmail.com";
        String password="mgscxgqmxsdgzygo";
        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.SSL","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.SSL","465");
        props.put("mail.smtp.port","587");

       javax.mail.Session session=Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username,password);
            }
        });

        try {
            MimeMessage message=new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(MimeMessage.RecipientType.TO,InternetAddress.parse(FirebaseAuth.getInstance().getCurrentUser().getEmail()));
            message.setSubject("Welcome to NSS GIT");
            message.setText("Dear" + "  "+FirebaseAuth.getInstance().getCurrentUser().getEmail()+"\n" +
                    "Hi!\n" +
                    "\n" +
                    "Welcome to NSS GIT app!\n" +
                    "\n" +
                    "Here you can get all the info about NSS GIT in one place. You can find the latest updates about NSS GIT, events, activities, and more.\n" +
                    "\n" +
                    "To get started, simply sign into the app with your NSS GIT account. Once you're logged in, you'll be able to explore all the features that NSS GIT has to offer.\n" +
                    "\n" +
                    "Here are some of the great things you can do with the NSS GIT app:\n" +
                    "\n" +
                    "Get the latest news and updates about NSS GIT\n" +
                    "Find out about upcoming events and activities\n" +
                    "Register for events and activities\n" +
                    "Connect with other NSS GIT students\n" +
                    "Get help and support from NSS GIT staff.\n"+
                    "\n" +
                    "We hope you enjoy using the NSS GIT app!\n"+
                    "If you have any questions or feedback, please feel free to contact us at nssgitofficial@gmail.com!\n" +
                    "\n" +
                    "Thank you,\n" +
                    "The NSS GIT team");


          Transport.send(message);

        }catch (MessagingException e){
            throw new RuntimeException(e);
        }

    }
    private void add_ItemtoSheet(){
         final String Name_sheet=full_name.getEditableText().toString().trim();
         final String Email_sheet=email_signup.getEditableText().toString().trim();
         final  String Phone_sheet=mobile_number_signup.getEditableText().toString().trim();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, "https://script.google.com/macros/s/AKfycby5u-XMd4MLSRhPB2VyfAnovxDTPRIkz2YIbvwSEB6Jd622YWyzUA6uK0mTVxq8bO30VA/exec", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(User_Signup.this, ""+response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams(){
                Map<String,String> params=new HashMap<>();
                params.put("action","addItem");
                params.put("Username",Name_sheet);
                params.put("Email",Email_sheet);
                params.put("PhoneNumber",Phone_sheet);
                return params;

            }



        };
        int timeOut=50000;
        RetryPolicy retryPolicy=new DefaultRetryPolicy(timeOut,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(retryPolicy);
        RequestQueue queue= Volley.newRequestQueue(User_Signup.this);
        queue.add(stringRequest);
    }


}
