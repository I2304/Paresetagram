package com.pusheenicorn.paresetagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class PersonalActivity extends AppCompatActivity {
    ImageView ivProfileImage;
    TextView tvUsername;
    public File photoFile;
    EditText etDescription;
    EditText etLocation;

    ProgressBar progressBar;
    static final int REQUEST_TAKE_PHOTO = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public Bitmap bitmap;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        ivProfileImage = (ImageView) findViewById(R.id.ivProfileImage);
        tvUsername = (TextView) findViewById(R.id.tvUsername);

        tvUsername.setText(ParseUser.getCurrentUser().getUsername());
        Glide.with(this).load(ParseUser.getCurrentUser()
                .getParseFile("profileImage").getUrl()).into(ivProfileImage);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_home:
                                Intent intent = new Intent(PersonalActivity.this,
                                        MainActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.action_compose:
                                Intent newIntent = new Intent(PersonalActivity.this,
                                        PostActivity.class);
                                startActivity(newIntent);
                                return true;
                            case R.id.action_profile:
                                return true;
                        }
                        return true;
                    }
                });

    }

    public void onPost(View v) {
        Intent intent = new Intent(PersonalActivity.this, PostActivity.class);
        startActivity(intent);
        return;
    }

    public void onPerson(View view) {
        return;
    }

    public void onPic(View v) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.pusheenicorn.paresetagram",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "PROFILE" + "profile" + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            ivProfileImage.setImageBitmap(bitmap);
            final ParseFile parseFile = new ParseFile(photoFile);

            parseFile.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        final ParseUser user = ParseUser.getCurrentUser();
                        user.put("profileImage", parseFile);
                        user.saveInBackground();
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public void onLogout(View v) {
        ParseUser.logOut();
        Intent i = new Intent(PersonalActivity.this, HomeActivity.class);
        startActivity(i);
    }
}
