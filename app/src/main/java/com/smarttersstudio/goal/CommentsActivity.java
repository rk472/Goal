package com.smarttersstudio.goal;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarttersstudio.goal.POJO.Comments;
import com.smarttersstudio.goal.adapter.CommentsAdapter;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CommentsActivity extends AppCompatActivity {
    private RecyclerView list;
    private DatabaseReference commentRef;
    private String pid,uid,text,time,tag;
    private ImageView dp;
    private TextView nameText,postText,timeText,tagText;
    private EditText commentText;
    private ImageButton commentButton;
    private CommentsAdapter f;
    private String myUid;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        list=findViewById(R.id.comment_list);
        pid=getIntent().getExtras().getString("pid");
        uid=getIntent().getExtras().getString("uid");
        text=getIntent().getExtras().getString("text");
        time=getIntent().getExtras().getString("time");
        tag=getIntent().getExtras().getString("tag");
        commentRef= FirebaseDatabase.getInstance().getReference().child("comments").child(pid);
        FirebaseRecyclerOptions<Comments> options=new FirebaseRecyclerOptions.Builder<Comments>().setQuery(commentRef.orderByChild("order"),Comments.class).build();
        f=new CommentsAdapter(options,getApplicationContext());
        list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        list.setHasFixedSize(true);
        list.setAdapter(f);
        mAuth=FirebaseAuth.getInstance();
        myUid=mAuth.getCurrentUser().getUid();
        nameText=findViewById(R.id.comment_post_name);
        dp=findViewById(R.id.comment_post_dp);
        postText=findViewById(R.id.comment_post_text);
        timeText=findViewById(R.id.comment_post_time);
        tagText=findViewById(R.id.comment_post_tag);
        commentText=findViewById(R.id.comment_input);
        commentButton=findViewById(R.id.comment_button);
        commentButton.setEnabled(false);
        commentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s)){
                    commentButton.setEnabled(false);
                }else{
                    commentButton.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        setName();
        setDp();
        setTag();
        timeText.setText(time);
        postText.setText(text);
        commentRef.keepSynced(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        f.startListening();
    }

    private void setTag() {
        DatabaseReference d=FirebaseDatabase.getInstance().getReference().child("users").child(tag);
        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                tagText.setText("@"+name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        d.keepSynced(true);
    }

    private void setDp() {
        DatabaseReference d=FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final String url=dataSnapshot.child("dp").getValue().toString();
                Picasso.with(CommentsActivity.this).load(url).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.logo)
                        .into(dp, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Picasso.with(CommentsActivity.this).load(url).placeholder(R.drawable.logo).into(dp);
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        d.keepSynced(true);
    }

    private void setName() {
        DatabaseReference d=FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        d.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                nameText.setText(name);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        d.keepSynced(true);
    }

    public void comment(final View view) {
        view.setEnabled(false);
        String comment=commentText.getText().toString();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String d=formatter.format(date);
        String post=postText.getText().toString();
        Map m=new HashMap();
        m.put("uid",myUid);
        m.put("text",comment);
        m.put("time",d);
        m.put("order",-1*date.getTime());
        commentRef.push().updateChildren(m).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                commentText.setText("");
                view.setEnabled(true);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CommentsActivity.this, "Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                view.setEnabled(true);
            }
        });
    }
}
