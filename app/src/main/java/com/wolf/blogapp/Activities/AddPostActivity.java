package com.wolf.blogapp.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wolf.blogapp.R;

public class AddPostActivity extends AppCompatActivity {

    private ImageButton mPostImage;
    private EditText mTitleEditText;
    private EditText mDesc;
    private Button postButton;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;
    private DatabaseReference mPostDatabse;
    private ProgressDialog mProgress;


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

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startPosting();
            }
        });
    }

    private void startPosting() {
        mProgress.setMessage("Posting To Blog...");
        mProgress.show();

        String titleVal = mTitleEditText.getText().toString().trim();
        String descVal = mDesc.getText().toString().trim();

        if (!TextUtils.isEmpty(titleVal) && !TextUtils.isEmpty(descVal)) {

        }
    }
}
