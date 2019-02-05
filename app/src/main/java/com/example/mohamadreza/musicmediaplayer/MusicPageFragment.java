package com.example.mohamadreza.musicmediaplayer;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.mohamadreza.musicmediaplayer.model.Music;
import com.example.mohamadreza.musicmediaplayer.model.MusicLab;

import java.util.concurrent.TimeUnit;


public class MusicPageFragment extends DialogFragment {


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
    private SeekBar mSeekBar;
    private TextView mEndTime;
    private TextView mCurrentTime;
    private Boolean mISShuffle;
    private Boolean mIsRepeat;
    private Integer randomIndex;

    private MusicPageFragment.Callbacks mCallbacks;

    public interface Callbacks{
        void onMusicUpdatePage(Music music);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof MusicPageFragment.Callbacks) {
            mCallbacks = (MusicPageFragment.Callbacks) context;
        } else {
            throw new RuntimeException("Activity not impl callback");
        }
    }


    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            if (mMusicLab.isPlayed()) {
                mSeekBar.setProgress(mMusicLab.getCurrentPosition());
//               int currentDuration = mMusicLab.getCurrentPosition();
                mSeekbarUpdateHandler.postDelayed(this, 500);

//                mCurrentTime.setText("" + milliSecondsToTimer((long) currentDuration));
//                mCurrentTime.postDelayed(this, 1000);
//            }
//            else {
//            mCurrentTime.removeCallbacks(this);
//        }
            }
        }
    };

    public MusicPageFragment() {
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
    public static MusicPageFragment newInstance(Long musicId) {
        MusicPageFragment fragment = new MusicPageFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_MUSIC_ID,musicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        Long musicId = getArguments().getLong(ARG_MUSIC_ID);
        mMusicLab = MusicLab.getInstance(getActivity());
        mMusic = mMusicLab.getMusic(musicId);
        mISShuffle=false;
        mIsRepeat=false;

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.dialog_fragment_player, container, false);
        mTitle = view.findViewById(R.id.album_text_view);
        mArtist = view.findViewById(R.id.artist_text_view);
        mPlay = view.findViewById(R.id.play);
        mNext = view.findViewById(R.id.forward);
        mPrevious = view.findViewById(R.id.rewind);
        mShuffle = view.findViewById(R.id.shuffle);
        mRepeat = view.findViewById(R.id.repeat);
        mCover = view.findViewById(R.id.image_cover);
        mSeekBar = view.findViewById(R.id.seekBar);
        mCurrentTime = view.findViewById(R.id.tv_current_time);
        mEndTime = view.findViewById(R.id.tv_full_time);

        mSeekBar.setMax(mMusic.getDurationmusic()); // Set the Maximum range of the
        mSeekBar.setProgress(mMusicLab.getCurrentPosition());


        mRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMusicLab.Repeat();
            }
        });



        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    mMusicLab.seekBar(progress);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 50);

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

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mISShuffle){
                    randomIndex= mMusicLab.shuffle();
                    Long music_id = mMusicLab.getMusicId(randomIndex);
                    mMusic = mMusicLab.nextMusic(music_id);
                    updateUI();

                    if (mMusicLab.isPlayed()) {
                        mMusicLab.playSong(mMusic);
                        mMusicLab.playMedia();
                        mPlay.setImageResource(R.drawable.ic_pause);
                    }
                }
                    else{
                    mMusic = mMusicLab.nextMusic(mMusic.getId());
                    updateUI();
                    if (mMusicLab.isPlayed()) {
                        mMusicLab.playSong(mMusic);
                        mMusicLab.playMedia();
                        mPlay.setImageResource(R.drawable.ic_pause);
                    }
                }
            }
        });

        mPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mISShuffle){
                    randomIndex= mMusicLab.shuffle();
                    Long music_id = mMusicLab.getMusicId(randomIndex);
                    mMusic = mMusicLab.nextMusic(music_id);
                    updateUI();
                    if (mMusicLab.isPlayed()) {
                        mMusicLab.playSong(mMusic);
                        mMusicLab.playMedia();
                        mPlay.setImageResource(R.drawable.ic_pause);
                    }
                }

                mMusic=mMusicLab.previousMusic(mMusic.getId());
                updateUI();
                if(mMusicLab.isPlayed()) {
                    mMusicLab.playSong(mMusic);
                    mMusicLab.playMedia();
                    mPlay.setImageResource(R.drawable.ic_pause);
                }
            }
        });

        mShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 randomIndex = mMusicLab.shuffle();
                mISShuffle=!mISShuffle;
            }
        });

        mCover.setImageBitmap(mMusicLab.getMusicClipArt(mMusic.getSrcData()));

        updateUI();

        return view;
    }


    public  String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    @SuppressLint("ResourceAsColor")
    public void updateUI() {

        mTitle.setText(mMusic.getTitle());
        mTitle.setSelected(true);
        mArtist.setText(mMusic.getArtist());
        mCover.setImageBitmap(mMusicLab.getMusicClipArt(mMusic.getSrcData()));

        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(mMusic.getDurationmusic()),
                TimeUnit.MILLISECONDS.toSeconds(mMusic.getDurationmusic()) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mMusic.getDurationmusic()))
        );
        mEndTime.setText(time);
        mSeekBar.setMax(mMusic.getDurationmusic());
        mCallbacks.onMusicUpdatePage(mMusic);

    }
}
