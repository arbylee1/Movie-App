package com.gitgood.buzzmovie.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

/**
 * Created by Albert Li on 3/29/16.
 */
public class UpdateProfileRequest extends StringRequest {
    public static final int METHOD = Request.Method.POST;
    public static final String URL = "https://www.albertli.biz/api/register";
    public UpdateProfileRequest(Response.Listener<String> listener, Response.ErrorListener errorListener)
            throws JSONException {
        super(METHOD, URL, listener, errorListener);
    }
}
