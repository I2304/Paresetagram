package com.pusheenicorn.paresetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onLogout(View view) {
        ParseUser.logOut();
        Intent i = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
