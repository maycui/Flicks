package com.example.mayc.flicks;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class MovieTrailerActivity extends YouTubeBaseActivity {

    String youtubeKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        //youtube ID
        Intent intent = getIntent();
        youtubeKey = intent.getStringExtra("MovieID");
        //movieID = getIntent().getExtrasString("Movie ID");
        //movieID = intent.getStringExtra(MovieDetailsActivity.EXTRA_MESSAGE);

        //Intent i = new Intent(MovieDetailsActivity.this, MovieTrailerActivity.class);
       // String strData = i.getStringExtra("Movie ID");


        // temporary test video id -- TODO replace with movie trailer video id
        final String videoId = youtubeKey;

        // resolve the player view from the layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        // initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.youtube_key), new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                YouTubePlayer youTubePlayer, boolean b) {
                // do any work here to cue video, play video, etc.
                youTubePlayer.cueVideo(videoId);
            }
            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                YouTubeInitializationResult youTubeInitializationResult) {
                // log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");

            }
        });
    }







}
