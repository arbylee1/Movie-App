package com.gitgood.buzzmovie;

import android.content.Context;
import android.location.GpsStatus;
import android.support.v7.app.AppCompatActivity;

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
    private String ret;
    private RequestQueue queue;
    public Provider(Context context) {
        queue = Volley.newRequestQueue(context);
    }
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
