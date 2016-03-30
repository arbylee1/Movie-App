package com.gitgood.buzzmovie.model;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */
public interface ProviderInterface {
    void registerUser(String username, String password, boolean isAdmin,
                      final Callback<String> callback) throws JSONException;
    void loginUser(String username, String password, final Callback<JSONObject> callback) throws JSONException ;
    void getAllUsers(String authtoken, final Callback<JSONObject> callback) throws JSONException ;
    void banUser(String authtoken, final Callback<String> callback) throws JSONException ;
    void rateMovie(String authtoken, final Callback<String> callback) throws JSONException ;
    void updateProfile(String authtoken, final Callback<String> callback) throws JSONException ;
}
