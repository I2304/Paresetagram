package com.pusheenicorn.paresetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        progressBar = (ProgressBar) findViewById(R.id.pbLoading);
        rvPost = (RecyclerView) findViewById(R.id.rvPost);
        posts = new ArrayList<Post>();
        // construct the adapter from this data source
        postAdapter = new PostAdapter(posts);
        // recycler view setup
        rvPost.setLayoutManager(new LinearLayoutManager(this));
        rvPost.setAdapter(postAdapter);

        loadTopPosts();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                fetchTimelineAsync(0);
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
                    // add the objects
                    posts.addAll(objects);
                    // notify the adapter
                    postAdapter.notifyDataSetChanged();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fetchTimelineAsync(int page) {
        // TODO - do stuff
    }

    public void onLogout(View view) {
        ParseUser.logOut();
        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
