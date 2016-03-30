package com.gitgood.buzzmovie.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */
public class AllUsersRequest extends JsonObjectRequest {
    public static final int METHOD = Request.Method.POST;
    public static final String URL = "https://www.albertli.biz/api/login";
    public AllUsersRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
            throws JSONException {
        super(METHOD, URL, null, listener, errorListener);
    }
}
