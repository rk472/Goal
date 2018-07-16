package com.smarttersstudio.goal;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarttersstudio.goal.POJO.Post;
import com.smarttersstudio.goal.adapter.ForumAdapter;

public class SpecialPostActivity extends AppCompatActivity {
    private RecyclerView list;
    private ForumAdapter f;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_post);
        list=findViewById(R.id.special_list);
        mAuth= FirebaseAuth.getInstance();
        String uid=mAuth.getCurrentUser().getUid();
        String company=mAuth.getCurrentUser().getDisplayName().split("/")[1];
        DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("forum").child(company);
        FirebaseRecyclerOptions<Post> options=new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(d.orderByChild("tag").equalTo(uid),Post.class).build();
        f=new ForumAdapter(options,getApplicationContext());
        list.setAdapter(f);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }
    @Override
    protected void onStart() {
        super.onStart();
        f.startListening();
    }
}
