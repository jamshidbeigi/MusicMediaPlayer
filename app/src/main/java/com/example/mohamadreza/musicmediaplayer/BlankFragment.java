package com.example.mohamadreza.musicmediaplayer;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamadreza.musicmediaplayer.model.Music;
import com.example.mohamadreza.musicmediaplayer.model.MusicLab;


public class BlankFragment extends DialogFragment {


    private static final String ARG_MUSIC_ID="music_id";
    private static final String ARG_MUSIC_LAB="music_lab_obj";


    private ImageView mCover;
    private ImageView mPlay;
    private ImageView mPrevious;
    private ImageView mNext;
    private ImageView mShuffle;
    private ImageView mRepeat;
    private TextView mTitle;
    private TextView mArtist;
    private MusicLab mMusicLab;
    private Music mMusic;

    public BlankFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }

    }

    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(Long musicId,MusicLab musicLab) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MUSIC_ID,musicId);
        args.putSerializable(ARG_MUSIC_LAB,musicLab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        Long musicId = getArguments().getLong(ARG_MUSIC_ID);
        mMusicLab = (MusicLab) getArguments().getSerializable(ARG_MUSIC_LAB);
        mMusic = mMusicLab.getMusic(musicId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_blank, container, false);
         mPlay = view.findViewById(R.id.play);
        mNext = view.findViewById(R.id.forward);
        mPrevious = view.findViewById(R.id.rewind);
        mShuffle = view.findViewById(R.id.shuffle);
        mRepeat = view.findViewById(R.id.repeat);
        mCover = view.findViewById(R.id.image_cover);

        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMusicLab.isPlayed()) {
                    mPlay.setImageResource(R.drawable.ic_play);
                    mMusicLab.pauseMedia();
                }
                else {
                    mPlay.setImageResource(R.drawable.ic_pause);
                    mMusicLab.playSong(mMusic);
                    mMusicLab.resumeMedia();
                }
            }
        });

        mCover.setImageBitmap(mMusicLab.getMusicClipArt(mMusic.getSrcData()));


        return view;
    }

}
