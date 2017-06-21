package com.example.mayc.flicks.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mayc on 6/21/17.
 */

public class Movie {

    private String title;
    private String overview;
    private String posterPath; // only the path
    private String backdropPath;

    //initialize from JSON data
    public Movie(JSONObject o) throws JSONException {
        title = o.getString("title");
        overview = o.getString("overview");
        posterPath = o.getString("poster_path");
        backdropPath = o.getString("backdrop_path");

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
}
