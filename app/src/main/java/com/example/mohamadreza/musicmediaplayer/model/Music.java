package com.example.mohamadreza.musicmediaplayer.model;

public class Music {
    private long mId;
    private String mTitle;
    private String mAlbum;
    private String mArtist;
    private String srcData;
    private int durationmusic;

    public Music(long id, String title, String album, String artist, String srcData, int durationmusic) {
        mId = id;
        mTitle = title;
        mAlbum = album;
        mArtist = artist;
        this.srcData = srcData;
        this.durationmusic = durationmusic;
    }

    public int getDurationmusic() {
        return durationmusic;
    }

    public String getSrcData() {
        return srcData;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public String getArtist() {
        return mArtist;
    }

    public Music() {
    }

    public void setId(long id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public void setAlbum(String album) {
        mAlbum = album;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public void setSrcData(String srcData) {
        this.srcData = srcData;
    }

    public void setDurationmusic(int durationmusic) {
        this.durationmusic = durationmusic;
    }
}
