package com.example.mohamadreza.musicmediaplayer.model;

public class Album {

    private long mId;
    private String mTitle;
    private String srcData;

    public Album(String title, String srcData) {
//        mId = id;
        mTitle = title;
        this.srcData = srcData;
    }

    public long getId() {

        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSrcData() {
        return srcData;
    }
}
