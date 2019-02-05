package com.example.mohamadreza.musicmediaplayer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mohamadreza.musicmediaplayer.model.Music;
import com.example.mohamadreza.musicmediaplayer.model.MusicLab;

public class MainActivity extends AppCompatActivity implements  MusicPlayerFragment.Callbacks , MusicPageFragment.Callbacks
        ,AlbumsMusics.Callbacks,ArtistsMusics.Callbacks {


    private static final String DIALOG_TAG = "DialogDate";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private ImageView mPlay;
    private ImageView mPrevious;
    private ImageView mNext;
    private TextView mTitle;
    private MusicLab mMusicLab;
    private Music mMusic;
    private MusicPlayerFragment mMusicPlayerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        mMusicLab = MusicLab.getInstance(this);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);
        mPlay = findViewById(R.id.play);
        mNext = findViewById(R.id.next);
        mTitle =findViewById(R.id.bar_title);
        mPrevious = findViewById(R.id.previous);

        LinearLayout layout = findViewById(R.id.controller_linear);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMusic == null) {
                    Toast.makeText(MainActivity.this, "not any music selected", Toast.LENGTH_SHORT).show();
                } else {
                    MusicPageFragment musicPageFragment = MusicPageFragment.newInstance(mMusic.getId());
                    musicPageFragment.setTargetFragment(mMusicPlayerFragment,
                            1);
                    musicPageFragment.show(getSupportFragmentManager(), DIALOG_TAG);
                }
            }
        });


        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMusic == null) {
                    Toast.makeText(MainActivity.this, "not any music selected", Toast.LENGTH_SHORT).show();
                } else {
                    if (mMusicLab.isPlayed()) {
                        mPlay.setImageResource(R.drawable.ic_play);
                        mMusicLab.pauseMedia();
                    } else {
                        mPlay.setImageResource(R.drawable.ic_pause);
                        mMusicLab.playSong(mMusic);
                        mMusicLab.resumeMedia();
                    }
                }
            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMusic == null) {
                    Toast.makeText(MainActivity.this, "not any music selected", Toast.LENGTH_SHORT).show();
                } else {
                    mMusic = mMusicLab.nextMusic(mMusic.getId());
                    mTitle.setText(mMusic.getTitle());
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

                if (mMusic == null) {
                    Toast.makeText(MainActivity.this, "not any music selected", Toast.LENGTH_SHORT).show();
                } else {
                    mMusic = mMusicLab.previousMusic(mMusic.getId());
                    mTitle.setText(mMusic.getTitle());
                    if (mMusicLab.isPlayed()) {
                        mMusicLab.playSong(mMusic);
                        mMusicLab.playMedia();
                        mPlay.setImageResource(R.drawable.ic_pause);
                    }
                }
            }
        });


        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        mMusicPlayerFragment = MusicPlayerFragment.newInstance();
                        return mMusicPlayerFragment;
                    case 1:
                        return AlbumFragment.newInstance();
                    case 2:
                        return ArtistsFragment.newInstance();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
//        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onMusicUpdate(Music music) {

        mMusic = music;
        mTitle.setText(mMusic.getTitle());
        mPlay.setImageResource(R.drawable.ic_pause);
        mMusicLab.playSong(music);
        mMusicLab.playMedia();

    }

    @Override
    public void onMusicUpdatePage(Music music) {
        mMusic = music;
        mTitle.setText(mMusic.getTitle());
        mPlay.setImageResource(R.drawable.ic_pause);
    }
}
