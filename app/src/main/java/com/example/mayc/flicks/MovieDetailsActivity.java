package com.example.mayc.flicks;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mayc.flicks.models.Movie;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    Integer id;

    //TextView tvTitle;
    //TextView tvOverview;
    //RatingBar rbVoteAverage;
    //TextView voteCount;
    //TextView releaseDate;

    @BindView(R.id.tvTitle) TextView tvTitle;
    @BindView(R.id.tvOverview) TextView tvOverview;
    @BindView(R.id.rbVoteAverage) RatingBar rbVoteAverage;
    @BindView(R.id.voteCount) TextView voteCount;
    @BindView(R.id.releaseDate) TextView releaseDate;
    @BindView(R.id.pop) TextView popularity;
    @BindView(R.id.videoPreview) ImageButton videoPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);

        // resolve the view objects
        //tvTitle = (TextView) findViewById(R.id.tvTitle);
        //tvOverview = (TextView) findViewById(R.id.tvOverview);
       // rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        //voteCount = (TextView) findViewById(R.id.voteCount);
        //releaseDate = (TextView) findViewById(R.id.releaseDate);

        // unwrap the movie passed in via intent, using its name as key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // GETTING ID OF MOVIE
        id = movie.getId();

        //set the stuff
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        voteCount.setText(movie.getVoteCount());
        releaseDate.setText(movie.getReleaseDate());
        popularity.setText(movie.getPop());


        //vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage >  0 ?  voteAverage / 2.0f : voteAverage);
        LayerDrawable stars = (LayerDrawable) rbVoteAverage.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);

        videoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // switch to the MovieTrailerActivity, passing movie id as a string using an intent
                Intent intent = new Intent(v.getContext(), MovieTrailerActivity.class);
                intent.putExtra("movieID", movie.getId());
                startActivity(intent);
            }
        });
    }

}
