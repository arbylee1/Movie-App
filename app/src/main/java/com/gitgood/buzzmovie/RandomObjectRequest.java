package com.gitgood.buzzmovie;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */
public class RandomObjectRequest extends JsonObjectRequest {
    String ret = null;
    public static final int METHOD = Request.Method.POST;
    public static final String URL = "https://api.random.org/json-rpc/1/invoke";
    public static final JSONObject REQUEST = new JSONObject();
//

    public RandomObjectRequest(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
            throws JSONException {
        super(METHOD, URL, REQUEST, listener, errorListener);
        REQUEST.put("jsonrpc", "2.0");
        REQUEST.put("method", "generateStrings");
        REQUEST.put("params", new Object[]{"6977995d-861d-4ca6-af82-25c8ff57b1f5", 2, 20, ""});
        REQUEST.put("id", 0);
    }
}
