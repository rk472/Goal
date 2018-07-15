package com.smarttersstudio.goal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText emailText,passwordText;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth=FirebaseAuth.getInstance();
        emailText=findViewById(R.id.login_email);
        passwordText=findViewById(R.id.login_password);
    }

    public void login(View view) {
        final ProgressDialog p=new ProgressDialog(this);
        p.setMessage("Please Wait While We are logging you in");
        p.setTitle("Please Wait");
        p.show();
        String email=emailText.getText().toString();
        String pass=passwordText.getText().toString();
        mAuth.signInWithEmailAndPassword(email,pass).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                p.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                String type=authResult.getUser().getDisplayName().split("/")[0];
                String company=authResult.getUser().getDisplayName().split("/")[1];
                Intent i;
                if(type.equals("s")){
                    i=new Intent(LoginActivity.this,CompanyActivity.class);
                }else{
                    i=new Intent(LoginActivity.this,ForumActivity.class);
                    i.putExtra("company",company);
                }
                startActivity(i);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            String type=mAuth.getCurrentUser().getDisplayName().split("/")[0];
            String company=mAuth.getCurrentUser().getDisplayName().split("/")[1];
            Intent i;
            if(type.equals("s")){
                i=new Intent(LoginActivity.this,CompanyActivity.class);
            }else{
                i=new Intent(LoginActivity.this,ForumActivity.class);
                i.putExtra("company",company);
            }
            startActivity(i);
            finish();
        }

    }

    public void goToForgotPassword(View view) {
        Intent i=new Intent(LoginActivity.this,ForgotActivity.class);
        startActivity(i);
    }
}
