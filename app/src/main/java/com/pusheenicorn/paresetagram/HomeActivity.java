package com.pusheenicorn.paresetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeActivity extends AppCompatActivity {
    public static final int LOGIN_REQUEST_CODE = 1;
    public static final int SIGNUP_REQUEST_CODE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void onSignup(View view) {
        Intent i = new Intent(HomeActivity.this, SignupActivity.class);
        startActivityForResult(i, SIGNUP_REQUEST_CODE);
    }

    public void onLogin(View view) {
        Intent i = new Intent(HomeActivity.this, LoginActivity.class);
        startActivityForResult(i, LOGIN_REQUEST_CODE);
    }
}
