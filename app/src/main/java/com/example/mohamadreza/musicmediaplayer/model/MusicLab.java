package com.example.mohamadreza.musicmediaplayer.model;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MusicLab implements Serializable {

    private static MusicLab instance;
    private List<Music> musicList = new ArrayList<>();
    private List<Album> albums = new ArrayList<>();
    private List<Artist> artists = new ArrayList<>();
    private MediaPlayer mMediaPlayer;
    private Context mContext;
    private int resumePosition;

    public MusicLab(Context context) {
        mMediaPlayer=new MediaPlayer();
        mContext = context;
        loadMusic();
        loadAlbum();
        loadArtist();
    }

    public static MusicLab getInstance(Context context) {
        if (instance == null)
            instance = new MusicLab(context);

        return instance;
    }


    public List<Music> getMusicList() {
        return musicList;
    }
    public List<Album> getAlbumList() {
        return albums;
    }
    public List<Artist> getArtistList() {
        return artists;
    }


    public void loadMusic() {

        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri, null, selection, null, sortOrder);

        try {
            if (musicCursor != null && musicCursor.moveToFirst()) {
                //get columns
                int idColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media._ID);
                int titleColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Media.DISPLAY_NAME);
                int artistColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ARTIST);
                int albumColumn = musicCursor.getColumnIndex
                        (android.provider.MediaStore.Audio.Media.ALBUM);
                Long albumId = musicCursor.getLong(musicCursor
                        .getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                int duration_song = musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);

                int data = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);


                Uri sArtworkUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
                Uri albumArtUri = ContentUris.withAppendedId(sArtworkUri, albumId);

                albumArtUri.toString();

                do {
                    long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisAlbum = musicCursor.getString(albumColumn);
                    String thisArtist = musicCursor.getString(artistColumn);
                    String thisdata = musicCursor.getString(data);
                    int durationmusic = musicCursor.getInt(duration_song);

//                    Long thisImage = musicCursor.getString(sArtworkUri);
                    musicList.add(new Music(thisId, thisTitle, thisAlbum ,thisArtist,thisdata,durationmusic ));
                }
                while (musicCursor.moveToNext());
            }
        } finally {
            musicCursor.close();

        }
    }

    public void loadAlbum() {

        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        try {
            if (musicCursor != null && musicCursor.moveToFirst()) {
                //get columns
//                int idColumn = musicCursor.getColumnIndex
//                        (MediaStore.Audio.Albums.ALBUM_ID);
                int titleColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Albums.ALBUM);

                int data = musicCursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);


                do {
//                    Long thisId = musicCursor.getLong(idColumn);
                    String thisTitle = musicCursor.getString(titleColumn);
                    String thisdata = musicCursor.getString(data);

                    albums.add(new Album(thisTitle,thisdata));

                }
                while (musicCursor.moveToNext());
            }
        } finally {
            musicCursor.close();

        }
    }


    public void loadArtist() {

        ContentResolver musicResolver = mContext.getContentResolver();
        Uri musicUri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        try {
            if (musicCursor != null && musicCursor.moveToFirst()) {

                int titleColumn = musicCursor.getColumnIndex
                        (MediaStore.Audio.Artists.ARTIST);

                do {
                    String thisTitle = musicCursor.getString(titleColumn);

                    artists.add(new Artist(thisTitle));

                }
                while (musicCursor.moveToNext());
            }
        } finally {
            musicCursor.close();

        }
    }


        public List<Music> getAlbumsMusics(String albumName) {

        List<Music> albumsMusics = new ArrayList();

        for(int i =0 ; i<musicList.size();i++){
            if(musicList.get(i).getAlbum().equals(albumName)) {
                Music music = musicList.get(i);
                albumsMusics.add(music);
            }
        }
        return albumsMusics;
    }


    public List<Music> getArtistsMusics(String ArtistName) {

        List<Music> albumsMusics = new ArrayList();

        for(int i =0 ; i<musicList.size();i++){
            if(musicList.get(i).getArtist().equals(ArtistName)) {
                Music music = musicList.get(i);
                albumsMusics.add(music);
            }
        }
        return albumsMusics;
    }

    public int albumsMusicCount(String albumName) {
        List<Music> albumsMusics = new ArrayList();
        int count = 0;
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getAlbum().equals(albumName)) {
                count++;
            }
        }
        return count;

    }

    public int artistsMusicCount(String artistName) {
        List<Music> artistsMusics = new ArrayList();
        int count = 0;
        for (int i = 0; i < musicList.size(); i++) {
            if (musicList.get(i).getArtist().equals(artistName)) {
                count++;
            }
        }
        return count;

    }


        public int shuffle(){
            Random random =new Random();
            int size = musicList.size();
            return random.nextInt((size-1)+1);
        }

        public Long getMusicId(int index){

        Music music = musicList.get(index);
           return music.getId();
        }

        public Bitmap getMusicClipArt(String path){

            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(path);
            byte [] data = mmr.getEmbeddedPicture();
            if(data!=null)
                return BitmapFactory.decodeByteArray(data,0,data.length);
                return null;
        }

        public void seekBar(int progress){
        mMediaPlayer.seekTo(progress);
        }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

        public void playMedia() {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.start();
            }
        }

        public void stopMedia() {
            if (mMediaPlayer == null) return;
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
        }

        public void autoPlayNext(Music music){
            final Long currentId = music.getId();
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                public void onCompletion(MediaPlayer mp) {

                   Music nextmusic = nextMusic(currentId);
                   playSong(nextmusic);
                    if(!isPlayed()) {
                        playMedia();
                    }
                }
            });
            }


        public void pauseMedia() {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.pause();
                resumePosition = mMediaPlayer.getCurrentPosition();
            }
        }

        public void resumeMedia() {
            if (!mMediaPlayer.isPlaying()) {
                mMediaPlayer.seekTo(resumePosition);
                mMediaPlayer.start();
            }
        }

        public void Repeat(){

                if(mMediaPlayer.isLooping())
                mMediaPlayer.setLooping(false);
                else
                    mMediaPlayer.setLooping(true);

        }


    public void playSong(Music music){
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(mContext, Uri.parse(music.getSrcData()));
            mMediaPlayer.prepare();
            autoPlayNext(music);
        } catch (IOException e) {

        }
    }

    public Music getMusic(Long musicId){
        for (int i=0; i<musicList.size();i++) {
            if(musicList.get(i).getId()==musicId){
                return musicList.get(i);
            }
        }
        return null;
    }

    public Music nextMusic(Long id){
        List<Music> musics = musicList;
        for(int i=0; i<musics.size(); i++){
            if(musics.get(i).getId()==id) {
                if(i!=musics.size()-1) {
                    return musics.get(i + 1);
                }
                else {
                    return musics.get(0);
                }
            }
        }

        return null;
    }

    public Music previousMusic(Long id){
        List<Music> musics = musicList;
        for(int i=0; i<musics.size(); i++){
                if(musics.get(i).getId()==id) {
                    if(i!=0) {
                        return musics.get(i - 1);
                    }
                    else {
                        return musics.get(0);
                    }
            }

        }

        return null;
    }

    public boolean isPlayed(){
        return mMediaPlayer.isPlaying();
    }

}