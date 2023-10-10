package com.daneshnaik.nssgit;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_settings extends AppCompatActivity {
CircleImageView profile_settings_image;

TextInputEditText email_profile_settings,fullname_profile_settings,mobile_num_profile_settings;
TextView password_change_profile_settings;
AppCompatButton update_btn_profile_settings;

Uri selectimage;


FirebaseAuth auth;
FirebaseDatabase database;
FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        storage=FirebaseStorage.getInstance();

        profile_settings_image=findViewById(R.id.profile_settings_image);
        email_profile_settings=findViewById(R.id.Email_profile_settings);
        fullname_profile_settings=findViewById(R.id.fullname_profile_settings);
        mobile_num_profile_settings=findViewById(R.id.mobile_num_profile_settings);
        password_change_profile_settings=findViewById(R.id.password_change_profile_settings);
        update_btn_profile_settings=findViewById(R.id.update_btn_profile_settings);





        database.getReference().child("Users").child(auth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                email_profile_settings.setText(snapshot.child("email").getValue().toString());
                fullname_profile_settings.setText(snapshot.child("fullName").getValue().toString());
                mobile_num_profile_settings.setText(snapshot.child("mobileNumber").getValue().toString());
                String profile_image=snapshot.child("imageurl").getValue().toString();
                Glide.with(getApplicationContext()).load(profile_image).placeholder(R.drawable.baseline_cloud_sync_24).into(profile_settings_image);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        update_btn_profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog=new ProgressDialog(Profile_settings.this);
                progressDialog.setTitle("Updating");
                progressDialog.setMessage("Updating Your Data");
                progressDialog.show();
                String FullName_data=fullname_profile_settings.getEditableText().toString().trim();
                String Phone_number_data=mobile_num_profile_settings.getEditableText().toString().trim();
                if(!FullName_data.isEmpty()){
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("fullName").setValue(FullName_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Profile_settings.this, "Updated Name", Toast.LENGTH_SHORT).show();
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
                    fullname_profile_settings.setError("Enter Your Name");
                }
                if(!Phone_number_data.isEmpty()){
                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("mobileNumber").setValue(Phone_number_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Profile_settings.this, "Updated Mobile Number", Toast.LENGTH_SHORT).show();
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
                    mobile_num_profile_settings.setError("Enter your Mobile Number");
                }


                if(selectimage!=null){
                    StorageReference reference=storage.getReference().child("profiles").child(auth.getUid());
                    reference.putFile(selectimage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String image_changed=uri.toString();
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getUid())
                                            .child("imageurl").setValue(image_changed).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    Toast.makeText(Profile_settings.this, "Updated Profile Picture", Toast.LENGTH_SHORT).show();
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
                }




                progressDialog.dismiss();

            }
        });

        profile_settings_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });

        password_change_profile_settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              AlertDialog.Builder dialog=new AlertDialog.Builder(Profile_settings.this);
              dialog.setTitle("Update password").setIcon(R.drawable.baseline_lock_24).setMessage("Are you sure want to update password").setPositiveButton("YES", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail()).addOnCompleteListener(new OnCompleteListener<Void>() {

                          @Override
                          public void onComplete(@NonNull Task<Void> task) {
                              password_change_profile_settings.setText("Check your Email");
                              password_change_profile_settings.setTextColor(getResources().getColor(R.color.red));
                          }
                      }).addOnFailureListener(new OnFailureListener() {
                          @Override
                          public void onFailure(@NonNull Exception e) {
                              Toast.makeText(Profile_settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                          }
                      });
                  }
              }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                  @Override
                  public void onClick(DialogInterface dialogInterface, int i) {
                      Toast.makeText(Profile_settings.this, "WEll WEll", Toast.LENGTH_SHORT).show();
                  }
              });
              dialog.show();
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                profile_settings_image.setImageURI(data.getData());
                selectimage=data.getData();
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Profile_settings.this).setIcon(R.drawable.baseline_logout_24).setTitle("Saved Changes").setMessage("Are you sure! want to Go Back?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(),Profile_management.class));
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Profile_settings.this, "Save the Changes", Toast.LENGTH_SHORT).show();
                    }
                }).setCancelable(true);
        alertDialog.show();
    }
}