package com.gitgood.buzzmovie;

import android.location.GpsStatus;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Albert Li
 */
public class Provider {
    private RequestQueue queue;
    private String ret = "";
    public String getRandomString() {
        try {
            JsonObjectRequest jsObjRequest = new RandomObjectRequest(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject resp) {
                    try {
                        ret = "";
                        JSONArray array = resp.getJSONObject("result").getJSONObject("random").getJSONArray("data");
                        ret += array.getString(0) + array.getString(1);
                    } catch (JSONException f) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            queue.add(jsObjRequest);
        } catch (JSONException e) {
        }
        return ret;
    }
}
