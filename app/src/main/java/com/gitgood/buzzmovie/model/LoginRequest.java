package com.gitgood.buzzmovie.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */
public class LoginRequest extends JsonObjectRequest{
    public static final int METHOD = Request.Method.GET;
    public static final String URL = "https://www.albertli.biz/api/login/";
    public LoginRequest(String username, String password, Response.Listener<JSONObject> listener,
                        Response.ErrorListener errorListener) {
        super(METHOD, URL + "?username=" + username + "&password=" + password, null, listener,
                errorListener);
    }
}
