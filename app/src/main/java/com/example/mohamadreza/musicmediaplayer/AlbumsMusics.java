package com.example.mohamadreza.musicmediaplayer;


import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mohamadreza.musicmediaplayer.model.Music;
import com.example.mohamadreza.musicmediaplayer.model.MusicLab;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlbumsMusics extends DialogFragment {

    private static final String ALBUM_NAME = "album_name";
    private Callbacks mCallbacks;
    private MusicLab mMusicLab;
    private RecyclerView mRecyclerView;
    private MusicAdapter mMusicAdapter;
    private String mAlbumName;


    public AlbumsMusics() {
        // Required empty public constructor
    }

    public static AlbumsMusics newInstance(String albumName) {
        AlbumsMusics fragment = new AlbumsMusics();
        Bundle args = new Bundle();
        args.putString(ALBUM_NAME, albumName);
        fragment.setArguments(args);
        return fragment;
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof AlbumsMusics.Callbacks) {
            mCallbacks = (Callbacks) context;
        } else {
            throw new RuntimeException("Activity not impl callback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle);
        mMusicLab = MusicLab.getInstance(getActivity());
        mAlbumName = getArguments().getString(ALBUM_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.recycler_view, container, false);

        mRecyclerView = view.findViewById(R.id.album_musics_list);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        mMusicAdapter = new MusicAdapter(mMusicLab.getAlbumsMusics(mAlbumName));
        mRecyclerView.setAdapter(mMusicAdapter);

        return view;
    }

    public interface Callbacks {
        void onMusicUpdate(Music music);
    }

    public class MusicHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        private ImageView mMusicImage;
        private TextView mTitle;
        private TextView mAlbum;
        private TextView mDuration;

        private Music mMusic;


        public MusicHolder(@NonNull final View itemView) {
            super(itemView);
            mMusicImage = itemView.findViewById(R.id.image_music);
            mTitle = itemView.findViewById(R.id.title);
            mAlbum = itemView.findViewById(R.id.album);
            mDuration = itemView.findViewById(R.id.duration);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    mCallbacks.onMusicUpdate(mMusic);
                    dismiss();

//                        mTitle.setSelected(true);
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

    private class MusicAdapter extends RecyclerView.Adapter<MusicHolder> {

        private List<Music> mSounds;

        public MusicAdapter(List<Music> sounds) {
            mSounds = sounds;
        }

        public void setSounds(List<Music> sounds) {
            mSounds = sounds;
        }

        @Override
        public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.list_item, parent, false);
            return new MusicHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MusicHolder holder, int position) {
            Music music = mSounds.get(position);
            holder.bindSound(music);
        }

        @Override
        public int getItemCount() {
            return mSounds.size();
        }
    }


}
