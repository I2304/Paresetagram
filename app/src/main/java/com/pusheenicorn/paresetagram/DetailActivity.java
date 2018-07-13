package com.pusheenicorn.paresetagram;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    TextView tvLikes;
    Context context;
    Post post;

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
}
