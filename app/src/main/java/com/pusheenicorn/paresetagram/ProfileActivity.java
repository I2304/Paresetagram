package com.pusheenicorn.paresetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.pusheenicorn.paresetagram.Models.Post;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {

    ImageView ivProfileImage;
    ImageView ivUserPost1;
    ImageView ivUserPost2;
    ImageView ivUserPost3;
    ImageView ivUserPost4;
    ImageView ivUserPost5;
    ImageView ivUserPost6;
    ImageView ivUserPost7;
    ImageView ivUserPost8;
    ImageView ivUserPost9;

    TextView tvUsername;
    Context context;
    ProgressBar progressBar;
    ParseUser user;
    ParseUser current_user;
    Post post;
    BottomNavigationView bottomNavigationView;
    ArrayList<Post> posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        user = ParseUser.getCurrentUser();
        post = getIntent().getParcelableExtra("post");
        current_user = post.getUser();

        posts = new ArrayList<Post>();

        setContentView(R.layout.activity_profile);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);
        ivUserPost1 = (ImageView) findViewById(R.id.ivUserPost1);
        ivUserPost2 = (ImageView) findViewById(R.id.ivUserPost2);
        ivUserPost3 = (ImageView) findViewById(R.id.ivUserPost3);
        ivUserPost4 = (ImageView) findViewById(R.id.ivUserPost4);
        ivUserPost5 = (ImageView) findViewById(R.id.ivUserPost5);
        ivUserPost6 = (ImageView) findViewById(R.id.ivUserPost6);
        ivUserPost7 = (ImageView) findViewById(R.id.ivUserPost7);
        ivUserPost8 = (ImageView) findViewById(R.id.ivUserPost8);
        ivUserPost9 = (ImageView) findViewById(R.id.ivUserPost9);

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

        loadUserPosts();
    }

    private void loadUserPosts() {
        final Post.Query postQuery = new Post.Query();
        postQuery
                .getTop()
                .withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    posts.addAll(objects);
                    if (objects.size() == 9)
                    {
                        Glide.with(context).load(posts.get(0).getImage().getUrl()).
                                into(ivUserPost1);
                        Glide.with(context).load(posts.get(1).getImage().getUrl())
                                .into(ivUserPost2);
                        Glide.with(context).load(posts.get(2).getImage().getUrl())
                                .into(ivUserPost3);
                        Glide.with(context).load(posts.get(3).getImage().getUrl())
                                .into(ivUserPost4);
                        Glide.with(context).load(posts.get(4).getImage().getUrl())
                                .into(ivUserPost5);
                        Glide.with(context).load(posts.get(5).getImage().getUrl())
                                .into(ivUserPost6);
                        Glide.with(context).load(posts.get(6).getImage().getUrl())
                                .into(ivUserPost7);
                        Glide.with(context).load(posts.get(7).getImage().getUrl())
                                .into(ivUserPost8);
                        Glide.with(context).load(posts.get(8).getImage().getUrl())
                                .into(ivUserPost9);
                    }
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}