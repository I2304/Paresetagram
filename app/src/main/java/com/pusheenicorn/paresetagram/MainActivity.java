package com.pusheenicorn.paresetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.pusheenicorn.paresetagram.Models.Post;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    PostAdapter postAdapter;
    ArrayList<Post> posts;
    RecyclerView rvPost;

    ProgressBar progressBar;
    private SwipeRefreshLayout swipeContainer;
    private Toolbar toolbar;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar =  (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        rvPost = (RecyclerView) findViewById(R.id.rvPost);
        posts = new ArrayList<Post>();
        // construct the adapter from this data source
        postAdapter = new PostAdapter(posts);
        // recycler view setup
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setAdapter(postAdapter);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        return true;
                    case R.id.action_compose:
                        Intent intent = new Intent(MainActivity.this,
                                PostActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_profile:
                        Intent profileIntent = new Intent(MainActivity.this,
                                PersonalActivity.class);
                        startActivity(profileIntent);
                        return true;
                }
                return true;
            }
        });

        loadTopPosts();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(posts.size());
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void onPost(View v) {
        Intent intent = new Intent(MainActivity.this, PostActivity.class);
        startActivity(intent);
    }

    private void loadTopPosts() {

        final Post.Query postQuery = new Post.Query();
        postQuery
                .getTop()
                .withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    showProgressBar();
                    for (int i = objects.size() - 1; i > -1; i--)
                    {
                        posts.add(objects.get(i));
                        // notify the adapter
                        postAdapter.notifyDataSetChanged();
                    }
                    hideProgressBar();

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        final Post.Query postQuery = new Post.Query();
        postQuery
                .getTop()
                .withUser();

        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e) {
                if (e == null) {
                    showProgressBar();
                    posts.clear();
                    for (int i = objects.size() - 1; i > -1; i--)
                    {
                        posts.add(objects.get(i));
                        // notify the adapter
                        postAdapter.notifyDataSetChanged();
                    }
                    hideProgressBar();
                } else {
                    e.printStackTrace();
                }
            }
        });

        swipeContainer.setRefreshing(false);
    }

    public void onPerson(View view) {
        //ParseUser.logOut();
        Intent i = new Intent(MainActivity.this, PersonalActivity.class);
        startActivity(i);
    }

    public void showProgressBar() {
        // Show progress item
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        // Hide progress item
        progressBar.setVisibility(View.INVISIBLE);
    }
}