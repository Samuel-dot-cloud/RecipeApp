package com.studiofive.recipeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;

//implementing a changing view interface
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //binding all views in main activity
    @BindView(R.id.findRecipesButton)
    Button mFindRecipesButton;
    @BindView(R.id.appTitle)
    TextView mAppTitle;
    @BindView(R.id.search_bar)
    EditText mSearchBar;
    @BindView(R.id.videoBackground)
    VideoView mVideoBackground;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //binding views
        ButterKnife.bind(this);

        //setting a click listener
        mFindRecipesButton.setOnClickListener(this);

        //Setting a video background
        String path ="android.resource://com.studiofive.recipeapp/"+R.raw.background;
        Uri u = Uri.parse(path);
        mVideoBackground.setVideoURI(u);
        mVideoBackground.start();

        mVideoBackground.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });

    }

    @Override
    public void onClick(View v) {
        if (v == mFindRecipesButton) {//switching to recipes when button pressed
            Intent intent = new Intent(MainActivity.this, RecipeActivity.class);
            startActivity(intent);
        }
    }

    //Override functions to enable optimum video play
    @Override
    protected void onResume(){
        mVideoBackground.resume();
        super.onResume();
    }

    @Override
    protected void onPause(){
        mVideoBackground.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        mVideoBackground.stopPlayback();
        super.onDestroy();
    }

}
