package com.gitgood.buzzmovie.model;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

/**
 * Created by Albert on 4/1/16.
 */

public class RottenTomatoesRequest extends JsonObjectRequest {

    private static final String preURL = "http://api.rottentomatoes.com/api/public/v1.0/";
    private static final String postURL = "?page_limit=20&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";

    public RottenTomatoesRequest(int method, RottenCall rc, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, preURL + rc.getUrl() + postURL, null, listener, errorListener);
    }
}
