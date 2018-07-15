package com.smarttersstudio.goal;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {
    private EditText postText;
    private Spinner alumniList;
    private String company;
    private Button postButton;
    private List<String> nameList,uidList;
    private FirebaseAuth mAuth;
    private String uid,tag;
    private DatabaseReference userRef,forumRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        postText=findViewById(R.id.add_post_text);
        postButton=findViewById(R.id.add_post);
        alumniList=findViewById(R.id.alumni_list);
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();
        company=getIntent().getExtras().getString("company");
        userRef= FirebaseDatabase.getInstance().getReference().child("users");
        postButton.setEnabled(false);
        forumRef=FirebaseDatabase.getInstance().getReference().child("forum").child(company);
        userRef.orderByChild("company").equalTo(company).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                nameList=new ArrayList<>();
                uidList=new ArrayList<>();
                nameList.add("none");
                uidList.add("none");
                for(DataSnapshot d:dataSnapshot.getChildren()){
                    String name=d.child("name").getValue().toString();
                    String uid=d.getKey();
                    nameList.add(name);
                    uidList.add(uid);
                }
                ArrayAdapter<String> adapter1 = new ArrayAdapter<>(AddPostActivity.this,android.R.layout.simple_list_item_1,nameList);
                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                alumniList.setAdapter(adapter1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        alumniList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tag=uidList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                tag="none";
            }
        });
        postText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(TextUtils.isEmpty(s))
                    postButton.setEnabled(false);
                else
                    postButton.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void post(View view) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String d=formatter.format(date);
        String post=postText.getText().toString();
        Map<String, Object> m=new HashMap<>();
        m.put("name",uid);
        m.put("tag",tag);
        m.put("text",post);
        m.put("time",d);
        final String key=forumRef.push().getKey();
        forumRef.child(key).updateChildren(m).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                if(!tag.equals("none")){
                    userRef.child(tag).child("post").push().child("key").setValue(key);
                    Toast.makeText(AddPostActivity.this, "Post Successful...", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
