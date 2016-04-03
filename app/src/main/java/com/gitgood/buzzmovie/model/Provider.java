package com.gitgood.buzzmovie.model;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */


public class Provider implements ProviderInterface{
    private RequestQueue queue;
    private static Provider provider;

    public static Provider getInstance(Context context) {
        if(provider == null) {
            provider = new Provider(context);
        }
        return provider;
    }

    private Provider(Context context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context);
        }
    }

    @Override
    public void registerUser(String username, String password, final Callback<String> callback) throws JSONException  {
        queue.add(new RegisterUserRequest(username, password, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(null);
            }
        }));
    }

    @Override
    public void loginUser(String username, String password, final Callback<JSONObject> callback) throws JSONException {
        queue.add(new LoginRequest(username, password, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(null);
            }
        }));
    }


    @Override
    public void getAllUsers(String authtoken, final Callback<JSONObject> callback) throws JSONException {
        queue.add(new AllUsersRequest(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(null);
            }
        }));
    }

    @Override
    public void banUser(String authtoken, final Callback<String> callback) throws JSONException {
        queue.add(new BanUserRequest(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(null);
            }
        }));
    }

    @Override
    public void rateMovie(String authtoken, final Callback<String> callback) throws JSONException {
        queue.add(new RateMovieRequest(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(null);
            }
        }));
    }

    @Override
    public void updateProfile(String authtoken, final Callback<String> callback) throws JSONException {
        queue.add(new UpdateProfileRequest(new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(null);
            }
        }));
    }
}
