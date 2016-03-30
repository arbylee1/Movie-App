package com.gitgood.buzzmovie.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Albert Li on 3/29/16.
 */
public class RegisterUserRequest extends StringRequest {
    public static final int METHOD = Request.Method.POST;
    public static final String URL = "https://www.albertli.biz/api/register";
    public RegisterUserRequest(String username, String password, Response.Listener<String> listener, Response.ErrorListener errorListener)
            throws JSONException {
        super(METHOD, URL + "?username=" + username + "&password=" + password, listener, errorListener);
    }
}
