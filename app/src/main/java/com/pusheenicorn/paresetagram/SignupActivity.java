package com.pusheenicorn.paresetagram;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupActivity extends AppCompatActivity {
    EditText etUserName;
    EditText etEmail;
    EditText etPassword;
    ParseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUserName = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);

        // Create the ParseUser
        user = new ParseUser();
    }

    public void onSignup(View view) {

        String username = etUserName.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        // Set core properties
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);

        // Invoke signUpInBackground
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(com.parse.ParseException e) {
                if (e == null) {
                    Log.d("Signup Activity", "Signup successfull");
                    final Intent intent = new Intent(SignupActivity.this,
                            MainActivity.class);
                    startActivity(intent);;
                    finish();
                } else {
                    Log.d("Signup Activity", "Signup unsuccessfull");
                    e.printStackTrace();
                }
            }
        });
    }

}
