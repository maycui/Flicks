package com.example.mayc.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by mayc on 6/21/17.
 */

@Parcel
public class Movie {

    String title;
    String overview;
    String posterPath; // only the path
    String backdropPath;
    Double voteAverage;
    String voteCount;
    String releaseDate;
    String pop;
    Integer id;
    String imageUrl;

    public Movie() {}

    //initialize from JSON data
    public Movie(JSONObject o) throws JSONException {
        title = o.getString("title");
        overview = o.getString("overview");
        posterPath = o.getString("poster_path");
        backdropPath = o.getString("backdrop_path");
        voteAverage = o.getDouble("vote_average");
        voteCount = o.getString("vote_count");
        releaseDate = o.getString("release_date");
        pop = o.getString("popularity");
        id = o.getInt("id");
        imageUrl = null;
    }

    public void setImageurl(String url) {
        imageUrl = url;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getPop() {
        return pop;
    }

    public Integer getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
