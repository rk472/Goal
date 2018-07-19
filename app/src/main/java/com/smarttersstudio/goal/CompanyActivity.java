package com.smarttersstudio.goal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.smarttersstudio.goal.adapter.CompanyAdapter;

public class CompanyActivity extends AppCompatActivity {
    private RecyclerView list;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        list=findViewById(R.id.company_list);
        String names[]=getResources().getStringArray(R.array.company);
        CompanyAdapter c=new CompanyAdapter(names,getApplicationContext());
        list.setAdapter(c);
        list.setLayoutManager(new GridLayoutManager(this,2));
        mAuth=FirebaseAuth.getInstance();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.company_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent i=null;
        if (id == R.id.menu_profile) {
            i=new Intent(this,ProfileActivity.class);
            i.putExtra("uid",mAuth.getCurrentUser().getUid());
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
