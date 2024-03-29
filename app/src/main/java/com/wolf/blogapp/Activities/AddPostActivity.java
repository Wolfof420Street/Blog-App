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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.wolf.blogapp.Model.Blog;
import com.wolf.blogapp.R;

import java.util.HashMap;
import java.util.Map;

public class AddPostActivity extends AppCompatActivity {

    private ImageButton mPostImage;
    private EditText mTitleEditText;
    private EditText mDesc;
    private StorageReference mStorageReference;
    private Button postButton;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mPostDatabase;
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

        mStorageReference = FirebaseStorage.getInstance().getReference();

        mPostDatabase = FirebaseDatabase.getInstance().getReference().child("Blog");

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

        final String titleVal = mTitleEditText.getText().toString().trim();
        final String descVal = mDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal) && mImageUri != null) {
       /* Blog blog = new Blog("Title", "Description", "imageUrl", "timestamp", "userId");
        mPostDatabase.setValue(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "blog posted", Toast.LENGTH_LONG).show();
                mProgress.dismiss();*/

       StorageReference filePath = mStorageReference.child("Blog_images").child(mImageUri.getLastPathSegment());
                filePath.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> downloadUrl = taskSnapshot.getStorage().getDownloadUrl();

                        DatabaseReference newPost = mPostDatabase.push();

                        Map<String, String> dataToSave = new HashMap<>();

                        dataToSave.put("title", titleVal);
                        dataToSave.put("desc", descVal);
                        dataToSave.put("image", downloadUrl.toString());
                        dataToSave.put("timestamp", String.valueOf(java.lang.System.currentTimeMillis()));
                        dataToSave.put("userId", mUser.getUid());

                        newPost.setValue(dataToSave);


                        mProgress.dismiss();


                        startActivity(new Intent(AddPostActivity.this, PostListActivity.class));
                    }
                });

            }

        }
    }

