package com.danunaik.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import com.danunaik.Tables.Messges;

import com.danunaik.nssgit.R;
import com.github.pgreze.reactions.ReactionPopup;
import com.github.pgreze.reactions.ReactionsConfig;
import com.github.pgreze.reactions.ReactionsConfigBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {
    Context context;
    ArrayList<Messges> messges;

    final  int  ITEM_SENT=1;
    final int    ITEM_RECIEVED=2;

    public MessageAdapter(Context context, ArrayList<Messges> messges){
        this.messges=messges;
        this.context=context;

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==ITEM_SENT){
            View view= LayoutInflater.from(context).inflate(R.layout.send_message_chat,parent,false);
            return  new SendViewHolder(view);
        }else{
            View view1=LayoutInflater.from(context).inflate(R.layout.received_messge_layout,parent,false);
            return  new ReceivedViewHolder(view1);
        }

    }

    @Override
    public int getItemViewType(int position) {
        Messges messge=messges.get(position);
        if (FirebaseAuth.getInstance().getUid().equals(messge.getSendId())){
            return ITEM_SENT;
        }else {
            return ITEM_RECIEVED;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Messges messges1=messges.get(position);

        int reactions[]=new int[]{
                R.drawable.ic_fb_like,
                R.drawable.ic_fb_love,
                R.drawable.ic_fb_laugh,
                R.drawable.ic_fb_wow,
                R.drawable.ic_fb_sad,
                R.drawable.ic_fb_angry
        };
        ReactionsConfig config = new ReactionsConfigBuilder(context)
                .withReactions(reactions)
                .build();

        ReactionPopup popup = new ReactionPopup(context, config, (pos) -> {

            return true; // true is closing popup, false is requesting a new selection
        });





        if (holder.getClass()==SendViewHolder.class) {
            SendViewHolder viewHolder = (SendViewHolder) holder;
            viewHolder.send_text.setText(messges1.getMessage());

            viewHolder.send_text.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {


                    AlertDialog.Builder alertdialog = new AlertDialog.Builder(context);
                    alertdialog.setTitle("Delete Message?");
                    alertdialog.setIcon(R.drawable.baseline_delete_forever_24);
                    alertdialog.setMessage("Are You sure want to delete it? permanent delete...  ");
                    alertdialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            viewHolder.send_text.setVisibility(View.GONE);
                            FirebaseDatabase.getInstance().getReference().child("chats").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("messages").child(messges1.getMessage()).child("message").setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(context, "message deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(context, "something went wrong", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, " Ok Fine why you Clicked  ", Toast.LENGTH_SHORT).show();
                        }
                    }).show();
                    return true;
                }
            });





//     viewHolder.send_text.setOnTouchListener(new View.OnTouchListener() {
//          @Override
//          public boolean onTouch(View v, MotionEvent event) {
//              popup.onTouch(v,event);
//              return false;
//          }
//      });

        }else {

            ReceivedViewHolder viewHolder=(ReceivedViewHolder) holder;
            viewHolder.recived_text.setText(messges1.getMessage());

//      viewHolder.recived_text.setOnTouchListener(new View.OnTouchListener() {
//          @Override
//          public boolean onTouch(View v, MotionEvent event) {
//              popup.onTouch(v,event);
//              return false;
//          }
//      });

        }
    }

    @Override
    public int getItemCount() {
        return messges.size();
    }

    public class ReceivedViewHolder extends RecyclerView.ViewHolder{
        TextView recived_text;
        ImageView feling_recived;
        public ReceivedViewHolder(@NonNull View itemView) {
            super(itemView);
            recived_text=itemView.findViewById(R.id.text_recived);
            feling_recived=itemView.findViewById(R.id.feeling);
        }
    }

    public class  SendViewHolder extends RecyclerView.ViewHolder{
        TextView send_text;
        ImageView feelings_send;

        public SendViewHolder(@NonNull View itemView) {
            super(itemView);
            send_text=itemView.findViewById(R.id.text_sent);
            feelings_send=itemView.findViewById(R.id.feeling);
        }
    }
}
