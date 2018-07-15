package com.smarttersstudio.goal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.smarttersstudio.goal.adapter.CompanyAdapter;

public class CompanyActivity extends AppCompatActivity {
    private RecyclerView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        list=findViewById(R.id.company_list);
        String names[]=getResources().getStringArray(R.array.company);
        CompanyAdapter c=new CompanyAdapter(names,getApplicationContext());
        list.setAdapter(c);
        list.setLayoutManager(new LinearLayoutManager(this));
    }
}
