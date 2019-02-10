package com.example.mohamadreza.musicmediaplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mohamadreza.musicmediaplayer.model.Artist;
import com.example.mohamadreza.musicmediaplayer.model.MusicLab;

import java.util.List;

public class ArtistsFragment extends Fragment {

    private static final String DIALOG_TAG_MUSIC = "DialogMusic";
    private RecyclerView mRecyclerViewArtist;
    private ArtistAdapter mAlbumAdapter;
    private MusicLab mMusicLab;


    public ArtistsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ArtistsFragment newInstance() {
        ArtistsFragment fragment = new ArtistsFragment();
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

        View view = inflater.inflate(R.layout.artist_recycler_layout, container, false);


        mRecyclerViewArtist = view.findViewById(R.id.recyclerViewartist);

        mRecyclerViewArtist.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAlbumAdapter = new ArtistAdapter(mMusicLab.getArtistList());
        mRecyclerViewArtist.setAdapter(mAlbumAdapter);


        return view;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public class ArtistHolder extends RecyclerView.ViewHolder {

        private TextView mMusicCount;
        private TextView mArtistTitle;
        private Artist mArtist;


        public ArtistHolder(@NonNull final View itemView) {
            super(itemView);
            mArtistTitle = itemView.findViewById(R.id.artist_name);
            mMusicCount = itemView.findViewById(R.id.count);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ArtistsMusics artistsMusics = ArtistsMusics.newInstance(mArtist.getTitle());
                    artistsMusics.setTargetFragment(ArtistsFragment.this,
                            1);
                    artistsMusics.show(getFragmentManager(), DIALOG_TAG_MUSIC);

                }
            });
        }


        public void bindSound(Artist artist) {
            mArtist = artist;
            mArtistTitle.setText(mArtist.getTitle());
            mMusicCount.setText(mMusicLab.artistsMusicCount(mArtist.getTitle()) + " songs");
        }

    }

    private class ArtistAdapter extends RecyclerView.Adapter<ArtistHolder> {

        private List<Artist> mArtists;

        public ArtistAdapter(List<Artist> artists) {
            mArtists = artists;
        }

        public void setArtists(List<Artist> artists) {
            mArtists = artists;
        }

        @Override
        public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.artist_item, parent, false);
            return new ArtistHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ArtistHolder holder, int position) {
            Artist artist = mArtists.get(position);
            holder.bindSound(artist);
        }

        @Override
        public int getItemCount() {
            return mArtists.size();
        }
    }

}
