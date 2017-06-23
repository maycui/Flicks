package com.example.mayc.flicks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mayc.flicks.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.example.mayc.flicks.MovieListActivity.API_BASE_URL;
import static com.example.mayc.flicks.MovieListActivity.API_KEY_PARAM;

public class MovieDetailsActivity extends AppCompatActivity {

    Context context;
    AsyncHttpClient client;
    Movie movie;
    String youtubeKey1;
    Integer id;
    public static final String MOVIE_ID = "MovieID";
    public final static String TAG = "MovieTrailerActivity";

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
    @BindView(R.id.videoPreview) ImageView videoPreview;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        ButterKnife.bind(this);
        client = new AsyncHttpClient();
        context = this;

        // resolve the view objects
        //tvTitle = (TextView) findViewById(R.id.tvTitle);
        //tvOverview = (TextView) findViewById(R.id.tvOverview);
       // rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        //voteCount = (TextView) findViewById(R.id.voteCount);
        //releaseDate = (TextView) findViewById(R.id.releaseDate);

        // unwrap the movie passed in via intent, using its name as key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        //set the stuff
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        voteCount.setText(movie.getVoteCount());
        releaseDate.setText(movie.getReleaseDate());
        popularity.setText(movie.getPop());

        int placeholderId = R.drawable.flicks_backdrop_placeholder;

        Glide.with(context)
                .load(movie.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 20, 0))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(videoPreview);


        //vote average is 0..10, convert to 0..5 by dividing by 2
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage = voteAverage >  0 ?  voteAverage / 2.0f : voteAverage);
        LayerDrawable stars = (LayerDrawable) rbVoteAverage.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(Color.RED, PorterDuff.Mode.SRC_ATOP);


        videoPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTrailerid();

                if (youtubeKey1 != null) {
                    // switch to the MovieTrailerActivity, passing movie id as a string using an intent
                    Intent intent = new Intent(context, MovieTrailerActivity.class);
                    intent.putExtra(MOVIE_ID, youtubeKey1);
                    startActivity(intent);
                }
            }
        });
    }

    // gets the trailer key
    private void getTrailerid() {
        String url = API_BASE_URL + "/movie/" + movie.getId() + "/videos";
        // set the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute a GET request expecting a jason object response
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("results");
                    //JSONObject idObject = results.getJSONObject(0);
                    //youtubeKey = idObject.getString("key");
                    youtubeKey1 = results.getJSONObject(0).getString("key");
                    Log.i(TAG, String.format("Acquired Youtube Id", results.length()));
                } catch (JSONException e) {
                    logError("Failed to find a movie ID", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get data from movie_id endpoint", throwable, true);
            }
        });

    }

    //handle errors, log and alert user
    private void logError(String message, Throwable error, boolean alertUser) {
        //always log the error
        Log.e(TAG, message, error);
        // alert the user
        if (alertUser) {
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }

}
