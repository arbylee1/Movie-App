package com.gitgood.buzzmovie.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.gitgood.buzzmovie.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

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

    private Provider(final Context context) {
        if (queue == null) {
            HurlStack hurlStack = new HurlStack() {
                @Override
                protected HttpURLConnection createConnection(URL url) throws IOException {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) super.createConnection(url);
                    try {
                        httpsURLConnection.setSSLSocketFactory(newSslSocketFactory(context));
                        httpsURLConnection.setHostnameVerifier(getHostnameVerifier());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return httpsURLConnection;
                }
            };
            queue = Volley.newRequestQueue(context, hurlStack);
        }
    }

    @Override
    public void registerUser(String username, String password, boolean isAdmin, final Callback<String> callback) throws JSONException  {
        queue.add(new RegisterUserRequest(username, password, isAdmin, new Response.Listener<String>() {
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

    private SSLSocketFactory newSslSocketFactory(Context context) {
        try {
            // Get an instance of the Bouncy Castle KeyStore format
            KeyStore trusted = KeyStore.getInstance("BKS");
            // Get the raw resource, which contains the keystore with
            // your trusted certificates (root and any intermediate certs)
            InputStream in = context.getResources().openRawResource(R.raw.apache);
            try {
                // Initialize the keystore with the provided trusted certificates
                // Provide the password of the keystore
                trusted.load(in, "mysecret".toCharArray());
            } finally {
                in.close();
            }

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(trusted);

            SSLContext sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, tmf.getTrustManagers(), null);
            return sslcontext.getSocketFactory();
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
    private HostnameVerifier getHostnameVerifier() {
        return new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                //return true;
                HostnameVerifier hv = HttpsURLConnection.getDefaultHostnameVerifier();
                return hv.verify("albertli.biz", session);
            }
        };
    }
}
