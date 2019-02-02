package com.example.mohamadreza.musicmediaplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.mohamadreza.musicmediaplayer.model.Music;
import com.example.mohamadreza.musicmediaplayer.model.MusicLab;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MusicPlayerFragment extends Fragment {

    private static final String DIALOG_TAG = "DialogDate";
    private RecyclerView mRecyclerView;
    private SoundAdapter mSoundAdapter;
    private MusicLab mMusicLab;
    private SeekBar mSeekBar;
    private Integer mRate;
    private ImageView mPlay;
    private ImageView mPrevious;
    private ImageView mNext;
    private ImageView mPause;
    private TextView mTitle;


    private Long musicId;

    private OnFragmentInteractionListener mListener;

    public MusicPlayerFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static MusicPlayerFragment newInstance() {
        MusicPlayerFragment fragment = new MusicPlayerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);
        mMusicLab = new MusicLab(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_player, container, false);

        LinearLayout layout = view.findViewById(R.id.linear_layout_root);

        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment blankFragment = BlankFragment.newInstance();
                blankFragment.setTargetFragment(MusicPlayerFragment.this,
                        1);
                    blankFragment.show(getFragmentManager(), DIALOG_TAG);

            }
        });
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSeekBar = view.findViewById(R.id.seekBar);


        mPlay = view.findViewById(R.id.play);
        mNext = view.findViewById(R.id.next);
        mPrevious = view.findViewById(R.id.previous);
//        Integer grideCount= getResources().getInteger(R.integer.refs.xmlres);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        mSoundAdapter = new SoundAdapter(mMusicLab.getMusicList());
        mRecyclerView.setAdapter(mSoundAdapter);



        mPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mMusicLab.isPlayed()) {
                    mPlay.setImageResource(R.drawable.ic_play);
                    mMusicLab.pauseMedia();
                }
                else {
                    mPlay.setImageResource(R.drawable.ic_pause);
                    mMusicLab.playMedia();
                }
            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mMusicLab.nextMedia();
            }
        });


        return view;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
////        mMusicLab.release();
//    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class SoundHolder extends RecyclerView.ViewHolder {

        private ImageView mMusicImage;
        private TextView mTitle;
        private TextView mAlbum;
        private TextView mDuration;

        private Music mMusic;


        public SoundHolder(@NonNull View itemView) {
            super(itemView);
            mMusicImage = itemView.findViewById(R.id.image_music);
            mTitle = itemView.findViewById(R.id.title);
            mAlbum = itemView.findViewById(R.id.album);
            mDuration = itemView.findViewById(R.id.duration);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mMusicLab.playSong(mMusic);
                    mTitle.setSelected(true);
                }
            });
        }


        public void bindSound(Music music) {
            mMusic = music;
            mMusicImage.setImageBitmap(MusicLab.getInstance(getActivity()).getMusicClipArt(mMusic.getSrcData()));
            mTitle.setText(mMusic.getTitle());
            mAlbum.setText(mMusic.getAlbum());
            String time = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(mMusic.getDurationmusic()),
                    TimeUnit.MILLISECONDS.toSeconds(mMusic.getDurationmusic()) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(mMusic.getDurationmusic()))
            );
            mDuration.setText(time);

        }

    }

    private class SoundAdapter extends RecyclerView.Adapter<SoundHolder> {

        private List<Music> mSounds;

        public void setSounds(List<Music> sounds) {
            mSounds = sounds;
        }

        public SoundAdapter(List<Music> sounds) {
            mSounds = sounds;
        }

        @Override
        public SoundHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, parent, false);
            return new SoundHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull SoundHolder holder, int position) {
            Music music = mSounds.get(position);
            holder.bindSound(music);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }

}
