//package com.example.mohamadreza.musicmediaplayer;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v7.app.AppCompatActivity;
//
///**
// * All activities that host one fragment must extend this class
// */
//public abstract class SingleFragmentActivity extends AppCompatActivity {
//
//    public abstract Fragment createFragment();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_pager_tab);
//
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        if (fragmentManager.findFragmentById(R.id.task_view_pager) == null) {
//            fragmentManager.beginTransaction()
//                    .add(R.id.task_view_pager, createFragment())
//                    .commit();
//        }
//    }
//}
//
