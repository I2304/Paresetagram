package com.pusheenicorn.paresetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseUser;
import com.pusheenicorn.paresetagram.Models.Post;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextView tvUserMain;
    TextView tvDescription;
    TextView tvTimestamp;
    TextView tvLocation;
    TextView tvUser;
    ImageView ivImage;
    ImageView ivProfileImage;
    TextView tvComment1;
    EditText tvNewComment;
    TextView tvLikes;
    Context context;
    Post post;
    BottomNavigationView bottomNavigationView;
    Button btnComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        post = getIntent().getParcelableExtra("post");

        tvUserMain = (TextView) findViewById(R.id.tvUsername);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvTimestamp = (TextView) findViewById(R.id.tvTimestamp);
        tvLocation = (TextView) findViewById(R.id.tvLocation);
        tvUser = (TextView) findViewById(R.id.tvUserMain);
        tvLikes = (TextView) findViewById(R.id.tvLikes);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvNewComment = (EditText) findViewById(R.id.tvNewComment);
        tvComment1 = (TextView) findViewById(R.id.tvComment1);
        btnComment = (Button) findViewById(R.id.btnComment);

        tvUserMain.setText(post.getUser().getUsername());
        tvDescription.setText(post.getDescription());

        Date date = post.getCreatedAt();
        DateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        String formatedDate = dateFormat.format(date);
        String newString = getRelativeTimeAgo(formatedDate);
        tvTimestamp.setText(newString);

        tvLocation.setText(post.getLocation());
        tvUser.setText(post.getUser().getUsername());
        tvLikes.setText("Likes: " + post.getLikes().toString());
        context = this;

        Glide.with(context).load(post.getImage().getUrl()).into(ivImage);
        Glide.with(context).load(post.getProfileImage().getUrl()).into(ivProfileImage);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Intent intent1 = new Intent(DetailActivity.this, MainActivity.class);
                                startActivity(intent1);
                                return true;
                            case R.id.action_compose:
                                Intent intent2 = new Intent(DetailActivity.this, PostActivity.class);
                                startActivity(intent2);
                                return true;
                            case R.id.action_profile:
                                Intent intent3 = new Intent(DetailActivity.this, PersonalActivity.class);
                                startActivity(intent3);
                                return true;
                        }
                        return true;
                    }
                });

        tvComment1.setText(post.getString("Comment1"));

    }

    public void onPost(View v) {
        Intent intent = new Intent(DetailActivity.this, PostActivity.class);
        startActivity(intent);
    }

    public void onPerson(View view) {
        //ParseUser.logOut();
        Intent i = new Intent(DetailActivity.this, PersonalActivity.class);
        startActivity(i);
    }

    public static String getRelativeTimeAgo(String rawDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return relativeDate;
    }

    public void onLike(View view) {
        ImageButton aButton = (ImageButton)view;
        if (post.liked == false) {
            aButton.setImageResource(R.drawable.ufi_heart_active);
            Number num = post.getLikes();
            int numLikes = num.intValue() + 1;
            Number newNum = (Number) numLikes;
            post.setLikes(newNum);
            tvLikes.setText("Likes: " + numLikes);
        }
        else {
            aButton.setImageResource(R.drawable.ufi_heart);
            Number num = post.getLikes();
            int numLikes = num.intValue() - 1;
            Number newNum = (Number) numLikes;
            post.setLikes(newNum);

            tvLikes.setText("Likes: " + numLikes);
        }
        post.liked = !post.liked;
    }

    public void onProfile(View v) {
        Intent intent = new Intent(DetailActivity.this, ProfileActivity.class);
        intent.putExtra("post", post);
        startActivity(intent);
    }

    public void onComment(View view) {
        tvNewComment.setVisibility(View.VISIBLE);
        btnComment.setVisibility(View.VISIBLE);
    }


    public void onCommentThis(View view) {
        String comment = tvNewComment.getText().toString();
        tvComment1.setText(ParseUser.getCurrentUser().getUsername() + ": " + comment);
        tvNewComment.setText("");
        tvNewComment.setVisibility(View.INVISIBLE);
        post.put("Comment1", comment);
        btnComment.setVisibility(View.INVISIBLE);
    }
}
