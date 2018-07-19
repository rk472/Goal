package com.smarttersstudio.goal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarttersstudio.goal.POJO.PDF;
import com.smarttersstudio.goal.adapter.PdfAdapter;

public class PdfActivity extends AppCompatActivity {
    private RecyclerView list;
    private PdfAdapter f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        list=findViewById(R.id.pdf_list);
        String company=getIntent().getExtras().getString("company");
        DatabaseReference d= FirebaseDatabase.getInstance().getReference().child("document").child(company);
        FirebaseRecyclerOptions<PDF> options=new FirebaseRecyclerOptions.Builder<PDF>().setQuery(d,PDF.class).build();
        f=new PdfAdapter(options,getApplicationContext());
        list.setAdapter(f);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        d.keepSynced(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        f.startListening();
    }
}
