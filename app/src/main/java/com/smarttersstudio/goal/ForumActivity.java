package com.smarttersstudio.goal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ForumActivity extends AppCompatActivity {
    String company;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
    }

    public void goToAddPost(View view) {
        Intent i=new Intent(ForumActivity.this,AddPostActivity.class);
        i.putExtra("company",company);
        startActivity(i);
    }
}
