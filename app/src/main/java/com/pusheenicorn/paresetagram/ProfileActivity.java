package com.pusheenicorn.paresetagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }

    public void onPost(View v) {
        Intent intent = new Intent(ProfileActivity.this, PostActivity.class);
        startActivity(intent);
    }

    public void onPerson(View v) {
        return;
    }
}
