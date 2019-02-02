package com.example.mohamadreza.musicmediaplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private TabItem mSongs;
    private TabItem mAlbums;
    private TabItem mArtists;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_tab);

        mTabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.task_view_pager);
        mSongs =findViewById(R.id.sogs_tab);
        mAlbums =findViewById(R.id.albums_tab);
        mArtists =findViewById(R.id.artists_tab);


        mViewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {

                switch (position) {
                    case 0:
                        return MusicPlayerFragment.newInstance();
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

}
