package com.gitgood.buzzmovie;
import android.content.res.Resources;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */
public class RandomObjectRequest extends JsonObjectRequest{
    public static final int METHOD = Request.Method.POST;
    public static final String URL = "https://api.random.org/json-rpc/1/invoke";
    private static final String JString ="{\n" +
            " \"jsonrpc\": \"2.0\",\n" +
            " \"method\": \"generateStrings\",\n" +
            " \"params\": {\n" +
            "  \"apiKey\": \"6977995d-861d-4ca6-af82-25c8ff57b1f5\",\n" +
            "  \"n\": 3,\n" +
            "  \"length\": 20,\n" +
            "  \"characters\": \"abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890\",\n" +
            "  \"replacement\": true\n" +
            " },\n" +
            " \"id\": 1\n" +
            "}";
    public RandomObjectRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
            throws JSONException {
        super(METHOD, URL, new JSONObject(JString), listener, errorListener);
    }
}
