package com.pusheenicorn.paresetagram;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;
import com.pusheenicorn.paresetagram.Models.Post;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        final Parse.Configuration configuration = new Parse.Configuration.Builder(this)
                .applicationId("ishani-karmarkar")
                .clientKey("ishanikarmarkar99")
                .server("http://ikarmarkar-fbu-instagram.herokuapp.com/parse")
                .build();
        Parse.initialize(configuration);
    }
}
