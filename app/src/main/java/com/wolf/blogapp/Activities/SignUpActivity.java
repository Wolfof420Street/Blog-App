package com.wolf.blogapp.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wolf.blogapp.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText secondName;
    private EditText email;
    private EditText password;
    private ImageButton profilePic;
    private Button createAcctButton;
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFireBaseDatabase;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private static final int GALLERY_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFireBaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFireBaseDatabase.getReference().child("users");

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        firstName = findViewById(R.id.firstName);
        secondName = findViewById(R.id.lastName);
        email = findViewById(R.id.emailText);
        password = findViewById(R.id.passwordText);
        createAcctButton = findViewById(R.id.submitButton);
        profilePic = findViewById(R.id.profilePicture);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_CODE);
            }
        });

        createAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        String name = firstName.getText().toString().trim();
        String lastName = secondName.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pwd = password.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(em) && !TextUtils.isEmpty(pwd)) {
            progressDialog.setMessage("creating Account...");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(em, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {

                    String userId = mAuth.getCurrentUser().getUid();
                    DatabaseReference currentUserDb= mDatabaseReference.child(userId);
                    currentUserDb.child("firstName").setValue(firstName);
                    currentUserDb.child("lastName").setValue(secondName);
                    currentUserDb.child("images").setValue("none");

                    progressDialog.dismiss();

                    Intent intent = new Intent(SignUpActivity.this, PostListActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  GALLERY_CODE && requestCode == RESULT_OK) {

        }
    }
}
