package com.daneshnaik.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.daneshnaik.Tables.Users;
import com.daneshnaik.nssgit.R;
import com.daneshnaik.nssgit.chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    FirebaseStorage storage;
    FirebaseAuth auth;
    FirebaseDatabase database;
    Context context;
    ArrayList<Users> users;
    public UserAdapter(Context context,ArrayList<Users> users){
        this.context=context;
        this.users=users;
    }
    public  void setfilterdlist(ArrayList<Users> filterlist){
        this.users=filterlist;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.single_user_format,parent,false);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        Users user=users.get(position);

        String senderId=FirebaseAuth.getInstance().getUid();
        String senderRoom=senderId+user.getId();

        FirebaseDatabase.getInstance().getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {



            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String lastmsg = snapshot.child("lastMsg").getValue(String.class);
                    long time = snapshot.child("lastMsgTime").getValue(Long.class);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy hh:mm a");
                    holder.time_setting.setText(dateFormat.format(new Date(time)));

                    holder.lastmessage.setText(lastmsg);

                }else{
                    holder.time_setting.setText("Start Chatting");
                    holder.lastmessage.setText("No Recent Messages");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.Name_taker.setText(user.getFullName());
        holder.Email_taker.setText(user.getEmail());
        Glide.with(context).load(user.getImageurl()).placeholder(R.drawable.baseline_person_24).into(holder.photo_taker);
//        holder.photo_taker.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, chat.class);
                intent.putExtra("name",user.getFullName());
                intent.putExtra("uid",user.getId());
                intent.putExtra("email",user.getEmail());
                intent.putExtra("profile_image",user.getImageurl());
                context.startActivity(intent);
            }
        });

        int lastPosition=-1;
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom
                        : R.anim.down_from_top);
        holder.itemView.startAnimation(animation);
        lastPosition = position;


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView Email_taker,Name_taker,time_setting,lastmessage;
        ImageView photo_taker;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Email_taker=itemView.findViewById(R.id.email_taker);
            Name_taker=itemView.findViewById(R.id.name_taker);
            photo_taker=itemView.findViewById(R.id.photo_taker);
            time_setting=itemView.findViewById(R.id.time_seeting);
            lastmessage=itemView.findViewById(R.id.last_message);
            lastmessage.setSelected(true);
            time_setting.setSelected(true);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }
}