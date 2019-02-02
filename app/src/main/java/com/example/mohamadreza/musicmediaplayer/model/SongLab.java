//package com.example.mohamadreza.musicmediaplayer.model;
//
// public class SongLab {
//
//   private void loadAllDeviceSongs(List<Song> songList) {
//        ContentResolver contentResolver = context.getContentResolver();
//
//        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
//
//        Cursor cursor = contentResolver.query(uri, null, selection, null, null);
//        if (cursor == null) return;
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            Song song = new Song();
//
//            song.set_display_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME)));
//            song.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
//            song.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
//            song.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
//            song.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
//            song.setDuration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));
//            song.set_size(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE)));
//            song.set_data(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
//            song.setAlbum_art(getSongsCover(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));
//
//            songList.add(song);
//            cursor.moveToNext();
//        }
//
//        Log.i(TAG, "Song List Count: " + songList.size());
//
//        cursor.close();
//    }
//
//    private String getSongsCover(int albumId) {
//        ContentResolver contentResolver = context.getContentResolver();
//
//        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
//        String[] projection = new String[]{MediaStore.Audio.Albums._ID, MediaStore.Audio.Albums.ALBUM_ART};
//        String selection = MediaStore.Audio.Albums._ID + " = ?";
//        String[] selectionArgs = new String[]{String.valueOf(albumId)};
//
//        Cursor cursor = contentResolver.query(uri, projection, selection, selectionArgs, null);
//        if (cursor == null) return null;
//
//        String path = "";
//
//        cursor.moveToFirst();
//        while (!cursor.isAfterLast()) {
//            path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
//            cursor.moveToNext();
//        }
//
//        cursor.close();
//
//        return path;
