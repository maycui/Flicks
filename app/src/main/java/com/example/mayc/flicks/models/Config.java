package com.example.mayc.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mayc on 6/21/17.
 */

public class Config {

    String imageBaseURl;
    String posterSize;
    //backdrop size
    String backdropSize;

    public Config(JSONObject o) throws JSONException {
        JSONObject images = o.getJSONObject("images");
        // get image base url
        imageBaseURl = images.getString("secure_base_url");
        //get the poster size
        JSONArray posterSizeOptions = images.getJSONArray("poster_sizes");
        // use the option at index
        posterSize = posterSizeOptions.optString(3, "w342");
        // parse the backdrop size and use the option at index 1 or w780
        JSONArray backdropSizeOptions = images.getJSONArray("backdrop_sizes");
        backdropSize = backdropSizeOptions.optString(1, "w780");

    }

    //helper method for creating urls

    public String getImageUrl(String size, String path) {
        return String.format("%s%s%s", imageBaseURl, size, path);
    }

    public String getImageBaseURl() {
        return imageBaseURl;
    }

    public String getPosterSize() {
        return posterSize;
    }

    public String getBackdropSize() {
        return backdropSize;
    }
}
