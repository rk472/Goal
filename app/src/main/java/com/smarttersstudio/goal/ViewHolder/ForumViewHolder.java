package com.smarttersstudio.goal.ViewHolder;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarttersstudio.goal.CommentsActivity;
import com.smarttersstudio.goal.ProfileActivity;
import com.smarttersstudio.goal.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class ForumViewHolder extends RecyclerView.ViewHolder {
    private View v;
    private ImageButton goTOComments;
    private TextView nameText,postText,timeText,tagText;
    private ImageView dp;
    public ForumViewHolder(View itemView) {
        super(itemView);
        v=itemView;
        goTOComments=v.findViewById(R.id.go_to_comments_button);
        nameText=v.findViewById(R.id.post_name);
        postText=v.findViewById(R.id.post_text);
        timeText=v.findViewById(R.id.post_time);
        tagText=v.findViewById(R.id.post_tag);
        dp=v.findViewById(R.id.post_dp);
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
        postText.setText(text);
    }
    public void setTime(String time){
        timeText.setText(time);
    }
    public void setTag(final String tag){
       if(tag.equals("none")){
            tagText.setText("");
        }else {
            DatabaseReference d = FirebaseDatabase.getInstance().getReference().child("users").child(tag);
            d.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String n = dataSnapshot.child("name").getValue().toString();
                    tagText.setText("@"+n);
                    tagText.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(v.getContext(), ProfileActivity.class);
                            i.putExtra("uid",tag);
                            v.getContext().startActivity(i);
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
    public void setCommentButton(final String pid, final String name, final String time, final String tag, final String text){
        goTOComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(), CommentsActivity.class);
                i.putExtra("pid",pid);
                i.putExtra("uid",name);
                i.putExtra("time",time);
                i.putExtra("tag",tag);
                i.putExtra("text",text);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                v.getContext().startActivity(i);
            }
        });
    }


}
