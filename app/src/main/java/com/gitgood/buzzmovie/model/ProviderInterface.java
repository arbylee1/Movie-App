package com.gitgood.buzzmovie.model;

import android.content.Context;
import android.telecom.Call;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */
public interface ProviderInterface {
    /**
     * Calls the back-end implementation to register a user with specified username and password.
     * callback.onSuccess("success") is called, if the registration was successful.
     * callback.onSuccess("duplicate") is called, if there is another user with that username.
     * callback.onSuccess(null) is called, if there was a failure in registration.
     * @param username username of the new user
     * @param password password of the new user
     * @param isAdmin status of whether the new user is an administrator or not
     * @param callback Callback object which implements onSuccess(String result)
     */
    void registerUser(String username, String password, boolean isAdmin,
                      final Callback<String> callback);
    /**
     * Calls the back-end implementation to log in a user with specified username and password.
     * The back-end generates the user a session authentication token and sends profile information.
     * callback.onSuccess(JSONObject result) is called on execution.
     * If the login was successful:
     * result == {
     *      "username"  :"username of the user",
     *      "authToken" :"session token of the user",
     *      "name"      :"name of the user"
     *      "major"     :"major of the user"
     *      "email"     :"email address of the user"
     *      "interests" :"interests of the user"
     *      "isAdmin"   :Boolean "is the user an admin?"
     *      "isBanned"  :Boolean "is the user banned?"
     *      "ratings"   :"All ratings of the user in comma delimited form:
     *                   movie tomato id + ':' rating + ','
     * }
     * Otherwise, if the password was incorrect:
     * result == {
     *     "error":"incorrect"
     * }
     * Otherwise, for a failure
     * result == null
     *
     * @param username username of the user
     * @param password password of the user
     * @param callback Callback object which implements onSuccess(JSONObject result)
     */
    void loginUser(String username, String password, final Callback<JSONObject> callback);

    /**
     * Calls the back-end implementation to update the user's profile with specified values in JSON
     * form.
     * values == {
     *              "name"      :"name of the user"
     *              "major"     :"major of the user"
     *              "email"     :"email address of the user"
     *              "interests" :"interests of the user"
     * }
     * callback.onSuccess(JSONObject result) is called on execution.
     * If the update was successful:
     * result == values.
     * Otherwise, if the authToken was incorrect for the user:
     * result == {
     *     "error":"incorrect"
     * }
     * Otherwise, for a failure
     * result == null
     *
     * @param username username of the user
     * @param authToken authentication token for the user provided at login
     * @param values values for the profile information to be updated to
     * @param callback Callback object which implements onSuccess(JSONObject result)
     */
    void updateProfile(String username, String authToken, JSONObject values, final Callback<JSONObject> callback);

    /**
     * Calls the back-end implementation to retrieve the user's profile values in JSON form.
     * callback.onSuccess(JSONObject result) is called on execution.
     * If the call was successful:
     * result == {
     *              "name"      :"name of the user"
     *              "major"     :"major of the user"
     *              "email"     :"email address of the user"
     *              "interests" :"interests of the user"
     * }
     * Otherwise, if the authToken was incorrect for the user:
     * result == {
     *     "error":"incorrect"
     * }
     * Otherwise, for a failure:
     * result == null
     * @param username username of the user
     * @param authToken authentication token for the user provided at login
     * @param callback Callback object which implements onSuccess(JSONObject result)
     */
    void getProfile(String username, String authToken, final Callback<JSONObject> callback);

    /**
     * Calls the back-end implementation to retrieve a set of all users registered with the database
     * in JSONArray form.
     * callback.onSuccess(JSONObject result) is called on execution.
     * If the call was successful:
     * result == [
     *      ["username1", Boolean isUser1Banned],
     *      ["username2", Boolean isUser2Banned],
     *      ["username3", Boolean isUser3Banned],
     *      ...
     *      ...
     *      ["usernameN", Boolean isUserNBanned]
     * ]
     * Otherwise, if the authToken was incorrect for the user:
     * result == {
     *     "error":"incorrect"
     * }
     * Otherwise, if the user attempting retrieve all users is not an admin.
     * result == {
     *     "error":"not_admin"
     * }
     * Otherwise, for failure
     * result == null
     *
     * @param username username of the user
     * @param authToken authentication token for the user provided at login
     * @param callback Callback object which implements onSuccess(JSONArray result)
     */
    void getAllUsers(String username, String authToken, final Callback<JSONArray> callback);

    /**
     * Calls the back-end implementation to ban or unban a user whose username is specified by
     * param bannee. callback.onSuccess(String result) is called on execution.
     * If the call was successful:
     * result == "success"
     * Otherwise, if the authToken was incorrect for the user:
     * result == "incorrect"
     * Otherwise, if the user attempting to toggle ban is not an admin
     * result == "not admin"
     * Otherwise, if the user does not exist:
     * result == "DNE"
     * Otherwise, for failure
     * result == null
     *
     * @param username username of user being banned
     * @param banner username of user banning
     * @param authToken authentication token for the banner provided at login
     * @param callback Callback object which implements onSuccess(String result)
     */
    void toggleBanUser(String username, String banner, String authToken, final Callback<String> callback);

    /**
     * Calls the back-end implementation to add a rating to a movie (and remove the old one if it
     * existed) or remove a movie rating. The rating is passed by a float. If the rating is a zero,
     * then that implies that the call should only remove the old rating.
     * callback.onSuccess(String result) is called on execution.
     * If the call was successful:
     * result == String.valueOf(rating);
     * Otherwise, if the authToken was incorrect for the user:
     * result == "incorrect"
     * Otherwise, for failure
     * result == null
     *
     * @param username username of the rater
     * @param authToken authentication token for the user provided at login
     * @param movie rottenTomatoID of the movie to be rated
     * @param oldRating the old movie rating. to assist in searching.
     * @param newRating the new movie rating. 0 if removing the old one.
     * @param callback Callback object which implements onSuccess(String result)
     */
    void rateMovie(String username, String authToken, String movie, String major, float oldRating, float newRating, final Callback<String> callback);

    /**
     * Queries rottenTomatoes with the API query specified by the RottenCall enum.
     * callback.onSuccess(JSONObject result) is called on execution.
     * If the call was successful:
     * result == JSON format rottenTomatoes data.
     * Otherwise, for failure
     * result == null
     *
     * @param rc RottenCall specifying the API call
     * @param callback  Callback object which implements onSuccess(JSONObject result)
     */
    void queryRottenTomatoes(RottenCall rc, final Callback<JSONObject> callback);
}
