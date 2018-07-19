package com.smarttersstudio.goal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;

public class ProfileActivity extends AppCompatActivity {
    private CircleImageView dp;
    private TextView nameText,emailText,companyText;
    private FirebaseAuth mAuth;
    private String myUid;
    private Button logoutButton,uploadButton;
    private DatabaseReference userRef;
    private Bitmap thumb_bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dp=findViewById(R.id.profile_image);
        nameText=findViewById(R.id.profile_name);
        emailText=findViewById(R.id.profile_email);
        companyText=findViewById(R.id.profile_company);
        String uid=getIntent().getExtras().getString("uid");
        mAuth=FirebaseAuth.getInstance();
        myUid=mAuth.getCurrentUser().getUid();
        logoutButton=findViewById(R.id.logout_button);
        uploadButton=findViewById(R.id.upload_button);
        if(myUid.equals(uid)){
            uploadButton.setVisibility(View.VISIBLE);
            logoutButton.setVisibility(View.VISIBLE);
        }
        userRef= FirebaseDatabase.getInstance().getReference().child("users").child(uid);
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String name=dataSnapshot.child("name").getValue().toString();
                String email=dataSnapshot.child("email").getValue().toString();
                final String url=dataSnapshot.child("dp").getValue().toString();
                String company=dataSnapshot.child("company").getValue().toString();
                nameText.setText(name);
                emailText.setText(email);
                if(!company.equals("none")){
                    companyText.setText(company);
                    companyText.setVisibility(View.VISIBLE);
                }
                Picasso.with(ProfileActivity.this).load(url).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.logo)
                        .into(dp, new Callback() {
                            @Override
                            public void onSuccess() {
                            }

                            @Override
                            public void onError() {
                                Picasso.with(ProfileActivity.this).load(url).placeholder(R.drawable.logo).into(dp);
                            }
                        });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        userRef.keepSynced(true);

    }

    public void logout(View view) {
        mAuth.signOut();
        startActivity(new Intent(ProfileActivity.this,LoginActivity.class));
        finishAffinity();
        finish();
    }

    public void upload(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(ProfileActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                final ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Wait while We are updating your Profile Picture..");
                progressDialog.setTitle("Please Wait");
                progressDialog.show();
                Uri resultUri = result.getUri();
                final String uid=mAuth.getCurrentUser().getUid();
                File thumb_filePath=new File(resultUri.getPath());
                try{
                    thumb_bitmap=new Compressor(this)
                            .setMaxHeight(200)
                            .setMaxWidth(200)
                            .setQuality(10)
                            .compressToBitmap(thumb_filePath);
                }catch (Exception e){
                    e.printStackTrace();
                }
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                thumb_bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                final byte[] mbyte=byteArrayOutputStream.toByteArray();
                final StorageReference thumbFilePath= FirebaseStorage.getInstance().getReference().child(uid+".jpg");
                thumbFilePath.putBytes(mbyte).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> thumb_task) {
                        if(thumb_task.isSuccessful()) {
                            final String thumb_downloadUrl = thumb_task.getResult().getDownloadUrl().toString();
                            FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("dp").setValue(thumb_downloadUrl).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                        Toast.makeText(ProfileActivity.this, "Profile Picture Updated Successfully..", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(ProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            Toast.makeText(ProfileActivity.this, thumb_task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                Toast.makeText(ProfileActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
