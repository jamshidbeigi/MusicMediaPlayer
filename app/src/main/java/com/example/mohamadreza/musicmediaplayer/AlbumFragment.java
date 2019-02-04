package com.example.mohamadreza.musicmediaplayer;

import android.content.Context;
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

    private RecyclerView mRecyclerView2;
    private AlbumAdapter mAlbumAdapter;
    private MusicLab mMusicLab;

//    private Callbacks mCallbacks;

//    public interface Callbacks{
//        void onMusicUpdate(Music music);
//    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//
//        if (context instanceof Callbacks) {
//            mCallbacks = (Callbacks) context;
//        } else {
//            throw new RuntimeException("Activity not impl callback");
//        }
//    }

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


        mRecyclerView2 = view.findViewById(R.id.recyclerView2);



//        Integer grideCount= getResources().getInteger(R.integer.refs.xmlres);
        mRecyclerView2.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        mAlbumAdapter = new AlbumAdapter(mMusicLab.getAlbumList());
        mRecyclerView2.setAdapter(mAlbumAdapter);



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
//                    MusicPageFragment musicPageFragment = MusicPageFragment.newInstance(mAlbum.getId(),mMusicLab);
//                    musicPageFragment.setTargetFragment(mMusicPlayerFragment,
//                            1);
//                    musicPageFragment.show(getSupportFragmentManager(), DIALOG_TAG);

//                    mCallbacks.onMusicUpdate(mAlbum);
//                        mTitle.setSelected(true);
                }
            });
        }


        public void bindSound(Album album) {
            mAlbum = album;

            Drawable img = Drawable.createFromPath(mAlbum.getSrcData());
            mAlbumCover.setImageDrawable(img);
//            mAlbumCover.setImageResource(mAlbum.getSrcData());
            mAlbumTitle.setText(mAlbum.getTitle());
//            mMusicCount.setText(mAlbum.ge);
        }

    }

    private class AlbumAdapter extends RecyclerView.Adapter<AlbumHolder> {

        private List<Album> mAlbums;

        public void setAlbums(List<Album> albums) {
            mAlbums = albums;
        }

        public AlbumAdapter(List<Album> albums) {
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