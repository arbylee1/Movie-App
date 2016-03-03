package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */


public class Provider{
    private RequestQueue queue;

    public Provider(Context context) {
        queue = Volley.newRequestQueue(context);
    }

    public void getRandomString(final Callback callback) throws JSONException {
        queue.add(new RandomObjectRequest(new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    JSONArray array = jsonObject.getJSONObject("result").getJSONObject("random")
                            .getJSONArray("data");
                    String ret = array.getString(0) + array.getString(1) + array.getString(2);
                    callback.onSuccess(ret);
                } catch (JSONException e) {
                    callback.onFailure(null);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onFailure(null);
            }
        }));
    }
}
