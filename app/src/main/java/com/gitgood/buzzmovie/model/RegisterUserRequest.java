package com.gitgood.buzzmovie.model;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

/**
 * @author Albert Li
 */
public class RegisterUserRequest extends StringRequest {
    public static final int METHOD = Request.Method.GET;
    public static final String URL = "https://www.albertli.biz/api/register/index.py?username=";
    public RegisterUserRequest(String username, String password, boolean isAdmin, Response.Listener<String> listener, Response.ErrorListener errorListener)
            throws JSONException {
        super(METHOD, (URL + username + "&password=" + password + "&admin=" + String.valueOf(isAdmin)), listener, errorListener);
    }
}
