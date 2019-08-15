package com.wolf.blogapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wolf.blogapp.R;

public class PostListActivity extends AppCompatActivity {

    private DatabaseReference mDatabaseRefrence;
    private FirebaseDatabase mDataBase;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDataBase = FirebaseDatabase.getInstance();
        mDatabaseRefrence = mDataBase.getReference().child("Blog");
        mDataBase.setPersistenceEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()) {
            case R.id.action_add:
                if (mUser != null && mAuth != null) {
                startActivity(new Intent(PostListActivity.this, AddPostActivity.class));
                finish();
                }
                break;
            case R.id.action_signout:
                if (mUser != null && mAuth != null) {
                    mAuth.signOut();
                    startActivity(new Intent(PostListActivity.this, AddPostActivity.class));
                    finish();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
