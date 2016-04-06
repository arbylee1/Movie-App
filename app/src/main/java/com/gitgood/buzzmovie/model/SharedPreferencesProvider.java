package com.gitgood.buzzmovie.model;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.ArraySet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by albert on 4/1/16.
 */
public class SharedPreferencesProvider implements ProviderInterface {
    private Random rand = new Random();
    private Context context;
    private static SharedPreferencesProvider sharedPreferencesProvider;
    private SharedPreferences userInfo;
    private SharedPreferences movieInfo;
    private SharedPreferences.Editor userInfoEditor;
    private SharedPreferences.Editor movieInfoEditor;
    private SharedPreferencesProvider (Context context) {
        this.context = context;
        userInfo = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        movieInfo = context.getSharedPreferences("MovieInfo", Context.MODE_PRIVATE);
        userInfoEditor = userInfo.edit();
        movieInfoEditor = movieInfo.edit();
    }

    public static SharedPreferencesProvider getInstance(Context context) {
        if (sharedPreferencesProvider == null) {
            sharedPreferencesProvider = new SharedPreferencesProvider(context);
        }
        return sharedPreferencesProvider;
    }
    @Override
    public void registerUser(String username, String password, boolean isAdmin, Callback<String> callback) {
        String ret = userInfo.getString(username, null);
        if(ret == null) {
            userInfoEditor.putString(username, password);
            userInfoEditor.putString(username, "duplicate");
            userInfoEditor.commit();
            ret = "success";
        }
        callback.onSuccess(ret);
    }

    @Override
    public void loginUser(String username, String password, Callback<JSONObject> callback) {
        JSONObject ret = new JSONObject();
        try {
            if (password.equals(userInfo.getString(username + "_password", null))) {
                String authToken = String.valueOf(rand.nextInt()) + String.valueOf(rand.nextInt());
                userInfoEditor.putString(username + "_authToken", authToken);
                userInfoEditor.commit();
                ret.put("username", username);
                ret.put("authToken", authToken);
                ret.put("name", userInfo.getString(username + "name", "Set Name"));
                ret.put("major", userInfo.getString(username + "major", "Set Major"));
                ret.put("email", userInfo.getString(username + "email", "Set Email"));
                ret.put("interests", userInfo.getString(username + "interests", "Set Interests"));
                ret.put("isAdmin", userInfo.getBoolean(username + "isAdmin", false));
                ret.put("isBanned", userInfo.getBoolean(username + "isBanned", false));
                ret.put("userRatings", userInfo.getString(username + "ratings", ""));
                ret.put("movieRatings", new JSONArray(movieInfo.getAll()));
            } else {
                ret.put("error", "incorrect");
            }
            callback.onSuccess(ret);
        } catch (JSONException j){
            callback.onSuccess(null);
        }
    }

    @Override
    public void updateProfile(String username, String authToken, JSONObject values, Callback<JSONObject> callback) {
        JSONObject ret = values;
        try {
            if (authToken.equals(userInfo.getString(username + "_authToken", null))) {
                userInfoEditor.putString(username + "_name", values.getString("name"));
                userInfoEditor.putString(username + "_major", values.getString("major"));
                userInfoEditor.putString(username + "_email", values.getString("email"));
                userInfoEditor.putString(username + "_interests", values.getString("interests"));
                userInfoEditor.commit();
            } else {
                ret = new JSONObject("{\"error\":\"incorrect\"}");
            }
            callback.onSuccess(ret);
        } catch (JSONException e) {
            callback.onSuccess(null);
        }
    }

    @Override
    public void getProfile(String username, String authToken, Callback<JSONObject> callback) {
        JSONObject ret = new JSONObject();
        try {
            if (authToken.equals(userInfo.getString(username + "_authToken", null))) {
                ret.put("name", userInfo.getString(username + "_name", "Set Name"));
                ret.put("major", userInfo.getString(username + "_major", "Set Major"));
                ret.put("email", userInfo.getString(username + "_email", "Set Email"));
                ret.put("interests", userInfo.getString(username + "_interests", "Set Interests"));
            } else {
                ret.put("error", "incorrect");
            }
            callback.onSuccess(ret);
        } catch (JSONException e){
            callback.onSuccess(null);
        }
    }

    @Override
    public void getAllUsers(String username, String authToken, Callback<JSONArray> callback) {
        JSONArray ret = new JSONArray();
        try {
            if (authToken.equals(userInfo.getString(username + "_authToken", null)) &&
                    userInfo.getBoolean("_isAdmin", false)) {
                String users = userInfo.getString("users", "");
                for(String user: users.split(",")) {
                    ret.put(new JSONArray(new Object[] {user, userInfo.getBoolean(user + "_isBanned", false) }));
                }
            } else {
                ret.put(false);
            }
            callback.onSuccess(ret);
        } catch (JSONException e){
            callback.onSuccess(null);
        }
    }

    @Override
    public void toggleBanUser(String username, String banner, String authToken, Callback<String> callback) {
        String ret = "success";
        if (authToken.equals(userInfo.getString(banner + "authToken", null))) {
            if (userInfo.getBoolean(banner + "isAdmin", false)) {
                if (userInfo.getString(username, null) != null) {
                    userInfoEditor.putBoolean(username + "isBanned", !userInfo.getBoolean("banee + isBanned", false));
                    userInfoEditor.commit();
                } else {
                    ret = "DNE";
                }
            } else {
                ret = "not admin";
            }
        } else {
            ret = "incorrect";
        }
        callback.onSuccess(ret);
    }

    @Override
    public void rateMovie(String username, String authToken, String movie, String major, float oldRating, float newRating, Callback<String> callback) {
        String sNewRating = String.valueOf(newRating);
        String sOldRating = String.valueOf(oldRating);
        if (authToken.equals(userInfo.getString(username + "_authToken", null))) {
            String userRatingString = userInfo.getString(username + "_ratings", "");
            String movieRatingString=  movieInfo.getString(movie, "");
            if(oldRating == 0) {
                userRatingString = userRatingString + movie + ':' + sNewRating + ',';
                movieRatingString = movieRatingString + major + ':' + sNewRating +',';
            } else {
                userRatingString = userRatingString.replace(movie + ':' + sOldRating, movie + sNewRating);
                movieRatingString = movieRatingString.replace(major + ':' + sOldRating, major + sNewRating);
            }
            userInfoEditor.putString(username + "_ratings", userRatingString);
            userInfoEditor.commit();
            movieInfoEditor.putString(movie, movieRatingString);
            movieInfoEditor.commit();
        } else {
            callback.onSuccess("incorrect");
        }
        callback.onSuccess(sNewRating);
    }


    @Override
    public void queryRottenTomatoes(RottenCall rc, Callback<JSONObject> callback) {
        VolleyProvider.getInstance(context).queryRottenTomatoes(rc, callback);
    }
}
