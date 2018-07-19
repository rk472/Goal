package com.smarttersstudio.goal.ViewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarttersstudio.goal.ProfileActivity;
import com.smarttersstudio.goal.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class CommentsViewHolder extends RecyclerView.ViewHolder{
    private TextView nameText,timeText,commentText;
    private ImageView dp;
    private View v;
    public CommentsViewHolder(View itemView) {
        super(itemView);
        v=itemView;
        nameText=itemView.findViewById(R.id.comment_name);
        timeText=itemView.findViewById(R.id.comment_time);
        commentText=itemView.findViewById(R.id.comment_text);
        dp=itemView.findViewById(R.id.comment_dp);
    }
    public  void setName(final String name){
        DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("users").child(name);
        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String n=dataSnapshot.child("name").getValue().toString();
                nameText.setText(n);
                nameText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(v.getContext(),ProfileActivity.class);
                        i.putExtra("uid",name);
                        v.getContext().startActivity(i);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        d.keepSynced(true);
    }
    public void setDp(final String uid){

        DatabaseReference d=FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String url=dataSnapshot.child("dp").getValue().toString();
                Picasso.with(v.getContext()).load(url).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.logo)
                        .into(dp, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Picasso.with(v.getContext()).load(url).placeholder(R.drawable.logo).into(dp);
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setText(String text){
        commentText.setText(text);
    }
    public void setTime(String time){
        timeText.setText(time);
    }
}
