package com.pusheenicorn.paresetagram.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_USER = "user";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_LIKED = "likes";
    private static final String KEY_PROFILE_IMAGE = "profileImage";
    public boolean liked = false;

    public ParseFile getProfileImage() {
        return getUser().getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setProfileImage(ParseFile image) {
        getUser().put(KEY_PROFILE_IMAGE, image);
    }

    public Number getLikes() {
        return getNumber(KEY_LIKED);
    }

    public void setLikes(Number number) {
        put(KEY_LIKED, number);
    }

    public String getLocation() {
        return getString(KEY_LOCATION);
    }

    public void setLocation(String location) {
        put(KEY_LOCATION, location);
    }

    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile image) {
        put(KEY_IMAGE, image);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public static class Query extends ParseQuery<Post>{
        public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }
    }

}
