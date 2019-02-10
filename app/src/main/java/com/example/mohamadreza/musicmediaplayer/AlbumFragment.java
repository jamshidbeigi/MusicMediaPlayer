package com.example.mohamadreza.musicmediaplayer;

import android.graphics.drawable.Drawable;
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
import android.widget.TextView;

import com.example.mohamadreza.musicmediaplayer.model.Album;
import com.example.mohamadreza.musicmediaplayer.model.MusicLab;

import java.util.List;

public class AlbumFragment extends Fragment {

    private static final String DIALOG_TAG_MUSIC = "DialogMusic";
    private RecyclerView mRecyclerViewAlbum;
    private AlbumAdapter mAlbumAdapter;
    private MusicLab mMusicLab;


    public AlbumFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AlbumFragment newInstance() {
        AlbumFragment fragment = new AlbumFragment();
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

        View view = inflater.inflate(R.layout.album_recycler_layout, container, false);


        mRecyclerViewAlbum = view.findViewById(R.id.recyclerViewalbum);


//        Integer grideCount= getResources().getInteger(R.integer.refs.xmlres);
        mRecyclerViewAlbum.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mAlbumAdapter = new AlbumAdapter(mMusicLab.getAlbumList());
        mRecyclerViewAlbum.setAdapter(mAlbumAdapter);


        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class AlbumHolder extends RecyclerView.ViewHolder {

        private ImageView mAlbumCover;
        private TextView mMusicCount;
        private TextView mAlbumTitle;
        private Album mAlbum;


        public AlbumHolder(@NonNull final View itemView) {
            super(itemView);
            mAlbumCover = itemView.findViewById(R.id.album_cover);
            mAlbumTitle = itemView.findViewById(R.id.album_text_view);
            mMusicCount = itemView.findViewById(R.id.artist_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlbumsMusics albumsMusics = AlbumsMusics.newInstance(mAlbum.getTitle());
                    albumsMusics.setTargetFragment(AlbumFragment.this,
                            1);
                    albumsMusics.show(getFragmentManager(), DIALOG_TAG_MUSIC);

//                    mCallbacks.onMusicUpdate(mAlbum);
//                        mTitle.setSelected(true);
                }
            });
        }


        public void bindSound(Album album) {
            mAlbum = album;

            Drawable img = Drawable.createFromPath(mAlbum.getSrcData());
            mAlbumCover.setImageDrawable(img);
            mAlbumTitle.setText(mAlbum.getTitle());
            mMusicCount.setText(mMusicLab.albumsMusicCount(mAlbum.getTitle()) + " songs");
        }

    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

        private List<Album> mAlbums;

        public AlbumAdapter(List<Album> albums) {
            mAlbums = albums;
        }

        public void setAlbums(List<Album> albums) {
            mAlbums = albums;
        }

        @Override
        public AlbumHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_album, parent, false);
            return new AlbumHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AlbumHolder holder, int position) {
            Album album = mAlbums.get(position);
            holder.bindSound(album);
        }

        @Override
        public int getItemCount() {
            return mAlbums.size();
        }
    }

}