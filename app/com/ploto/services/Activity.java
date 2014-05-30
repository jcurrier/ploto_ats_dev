package com.ploto.services;

/**
 * Created by jeff on 5/26/14.
 */
public class Activity {

    private String mText;
    private String mUser;
    private String mTime;

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String mUser) {
        this.mUser = mUser;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String mTime) {
        this.mTime = mTime;
    }

    public Activity() {

    }

    public Activity(String text, String user, String time) {
        this.mText = text;
        this.mUser = user;
        this.mTime = time;
    }
}
