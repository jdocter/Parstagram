package com.example.jdocter.parstagram.model;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject{
    public static final String descriptionKey = "description";
    public static final String imageKey = "image";
    public static final String userKey = "user";

    public String getDescription() {
        return getString(descriptionKey);
    }

    public void setDescription(String description) {
        put(descriptionKey,description);

    }

    public ParseFile getImage() {
        return getParseFile(imageKey);
    }

    public void setImage(ParseFile image) {
        put(imageKey,image);
    }

    public ParseUser getUser() {
        return getParseUser(userKey);
    }

    public void setUser(ParseUser user) {
        put(userKey,user);
    }

    public static class Query extends ParseQuery<Post> {
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
