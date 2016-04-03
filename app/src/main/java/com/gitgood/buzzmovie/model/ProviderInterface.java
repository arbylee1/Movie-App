package com.gitgood.buzzmovie.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Albert Li on 3/29/16.
 */
public interface ProviderInterface {
    public void registerUser(String username, String password, final Callback<String> callback) throws JSONException;
    public void loginUser(String username, String password, final Callback<JSONObject> callback) throws JSONException ;
    public void getAllUsers(String authtoken, final Callback<JSONObject> callback) throws JSONException ;
    public void banUser(String authtoken, final Callback<String> callback) throws JSONException ;
    public void rateMovie(String authtoken, final Callback<String> callback) throws JSONException ;
    public void updateProfile(String authtoken, final Callback<String> callback) throws JSONException ;
}
