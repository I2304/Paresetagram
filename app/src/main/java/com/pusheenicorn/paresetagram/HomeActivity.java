package com.pusheenicorn.paresetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parse.ParseUser;

public class HomeActivity extends AppCompatActivity {
    public static final int LOGIN_REQUEST_CODE = 1;
    public static final int SIGNUP_REQUEST_CODE = 2;
    public ParseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        currentUser = ParseUser.getCurrentUser();

        if (currentUser != null) {
            Intent i = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(i);
        }
    }

    public void onSignup(View view) {
        Intent i = new Intent(HomeActivity.this, SignupActivity.class);
        startActivity(i);
    }

    public void onLogin(View view) {
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(i);
    }
}
