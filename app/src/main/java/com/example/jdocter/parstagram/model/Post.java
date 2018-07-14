package com.example.jdocter.parstagram.model;

import android.text.format.DateUtils;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.Date;

@ParseClassName("Post")
public class Post extends ParseObject{
    public static final String descriptionKey = "description";
    public static final String imageKey = "image";
    public static final String userKey = "user";
    public static final String likeKey = "likes";
    public static final String locationKey = "Location";

    public String getDescription() {
        return getString(descriptionKey);
    }

    public void setDescription(String description) { put(descriptionKey,description); }

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

    public String getTimestamp() { return getRelativeTimeAgo(getCreatedAt()); }

    public String getLocationKey() { return getString(locationKey); }

    public void setLocationKey(String location) { put(locationKey,location); }

    public String getLikeKey() { return getInt(likeKey) + ""; }

    public void setLikeKey() { // increment like count by one
        String like = getLikeKey();
        int likeInt = Integer.parseInt(like);
        put(likeKey,likeInt+1);
    }

    public static class Query extends ParseQuery<Post> {
        public Query() {
            super(Post.class);
        }

        public Query getTop() {
            setLimit(20);
            return this;
        }

        public Query dec() {
            orderByDescending("createdAt");
            return this;
        }

        public Query withUser() {
            include("user");
            return this;
        }

    }

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(Date date) {

        String relativeDate = "";
        long dateMillis = date.getTime();
        relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();


        return relativeDate;
    }
}
