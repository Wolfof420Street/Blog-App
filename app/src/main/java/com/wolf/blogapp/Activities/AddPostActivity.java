package com.wolf.blogapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wolf.blogapp.Model.Blog;
import com.wolf.blogapp.R;

import java.net.URI;

public class AddPostActivity extends AppCompatActivity {

    private ImageButton mPostImage;
    private EditText mTitleEditText;
    private EditText mDesc;
    private Button postButton;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mPostDatabse;
    private ProgressDialog mProgress;
    private Uri mImageUri;
    private static final int GALLERY_CODE =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);


        mProgress = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        mPostDatabse = FirebaseDatabase.getInstance().getReference().child("Blog");

        mPostImage = findViewById(R.id.imageButton);
        mTitleEditText = findViewById(R.id.editText);
        mDesc = findViewById(R.id.editText2);
        postButton = findViewById(R.id.submitPost);


        mPostImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startPosting();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {
            mImageUri = data.getData();

            mPostImage.setImageURI(mImageUri);
        }
    }

    private void startPosting() {
        mProgress.setMessage("Posting To Blog...");
        mProgress.show();

        String titleVal = mTitleEditText.getText().toString().trim();
        String descVal = mDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)) {
        Blog blog = new Blog("Title", "Description", "imageUrl", "timestamp", "userId");
        mPostDatabse.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "blog posted", Toast.LENGTH_LONG).show();
                mProgress.dismiss();

            }
        });
        }
    }
}
