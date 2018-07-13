package com.pusheenicorn.paresetagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;
import com.pusheenicorn.paresetagram.Models.Post;

import java.io.File;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    TextView tvUsername;
    Context context;
    public File photoFile;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public Bitmap bitmap;
    ProgressBar progressBar;
    ParseUser user;
    Post post;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        user = ParseUser.getCurrentUser();
        post = getIntent().getParcelableExtra("post");

        setContentView(R.layout.activity_profile);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);

        Glide.with(context).load(post.getProfileImage().getUrl()).into(ivProfileImage);
        tvUsername.setText(post.getUser().getUsername());

        progressBar = (ProgressBar) findViewById(R.id.pbLoading);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Intent intent1 = new Intent(ProfileActivity.this, MainActivity.class);
                                startActivity(intent1);
                                return true;
                            case R.id.action_compose:
                                Intent intent2 = new Intent(ProfileActivity.this, PostActivity.class);
                                startActivity(intent2);
                                return true;
                            case R.id.action_profile:
                                Intent intent3 = new Intent(ProfileActivity.this, PersonalActivity.class);
                                startActivity(intent3);
                                return true;
                        }
                        return true;
                    }
                });
    }

    public void onPost(View v) {
        Intent intent = new Intent(ProfileActivity.this, PostActivity.class);
        startActivity(intent);
    }

    public void onPerson(View v) {
        return;
    }
}
