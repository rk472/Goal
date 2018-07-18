package com.smarttersstudio.goal;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarttersstudio.goal.POJO.Post;
import com.smarttersstudio.goal.adapter.ForumAdapter;

public class ForumActivity extends AppCompatActivity {
    private String company;
    private RecyclerView list;
    private ForumAdapter f;
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        company=getIntent().getExtras().getString("company");
        list=findViewById(R.id.forum_list);
        mAuth=FirebaseAuth.getInstance();
        type=mAuth.getCurrentUser().getDisplayName().split("/")[0];
        fab=findViewById(R.id.add_post_button);
        if(type.equals("a")){
            fab.setImageResource(R.drawable.ic_attach_file_black_24dp);
        }
        DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("forum").child(company);
        FirebaseRecyclerOptions<Post> options=new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(d.orderByChild("order"),Post.class).build();
        f=new ForumAdapter(options,getApplicationContext());
        list.setAdapter(f);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
    }

    public void goToAddPost(View view) {
        if(type.equals("a")){
            Intent i=new Intent(ForumActivity.this,SpecialPostActivity.class);
            startActivity(i);
        }else {
            Intent i = new Intent(ForumActivity.this, AddPostActivity.class);
            i.putExtra("company", company);
            startActivity(i);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i=null;
        if (id == R.id.action_profile) {
            i=new Intent(this,ProfileActivity.class);
            i.putExtra("uid",mAuth.getCurrentUser().getUid());
        }else if(id == R.id.action_doccument){
            i=new Intent(this,PdfActivity.class);
            i.putExtra("company",company);
        }
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        f.startListening();
    }
}
