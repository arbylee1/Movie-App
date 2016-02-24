package com.gitgood.buzzmovie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

// import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends ActionBarActivity {
    public final static int ROTTEN_TOMATO_GET_MATCH = 1;
    public final static int ROTTEN_TOMATO_GET_NEW_DVD = 2;
    public final static int ROTTEN_TOMATO_GET_UPCOMING_MOVIES = 3;
    private RequestQueue queue;
    private String response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load all contents and prepare Volley Queue
        setContentView(R.layout.activity_search);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // load in all buttons and text edit also set even methods
        MenuInflater inflater = getMenuInflater();
        // Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.search, menu);
        // Associate searchable configuration with the SearchView
//        SearchManager searchManager =
//                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView =
//                (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        searchView.setSearchableInfo(
//                searchManager.getSearchableInfo(getComponentName()));

        // match search button
        Button searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactRottenTomatoes(ROTTEN_TOMATO_GET_MATCH);
            }
        });
        //Top dvd button
        Button TopDVDButton = (Button) findViewById(R.id.buttonDvd);
        TopDVDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactRottenTomatoes(ROTTEN_TOMATO_GET_NEW_DVD);
            }
        });
        // upcoming movie button
        Button NewRelButton = (Button) findViewById(R.id.buttonRel);
        NewRelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContactRottenTomatoes(ROTTEN_TOMATO_GET_UPCOMING_MOVIES);
            }
        });
        return true;
    }

    private void ContactRottenTomatoes (int id) {
        // All search buttons use this method the int destiguishes what search to use
        String url = "";
        if (id == ROTTEN_TOMATO_GET_MATCH) {
            EditText SearchText = (EditText) findViewById(R.id.searchText);
            String searchFor = SearchText.getText().toString();
            String my_new_str = searchFor.replaceAll(" ", "%20;");
            url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=" + my_new_str + "&page_limit=20&page=1&apikey=yedukp76ffytfuy24zsqk7f5";
            Log.v("||DAN||", url);
        } else if (id == ROTTEN_TOMATO_GET_NEW_DVD) {
           url = "http://api.rottentomatoes.com/api/public/v1.0/lists/dvds/new_releases.json?page_limit=20&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        } else if (id == ROTTEN_TOMATO_GET_UPCOMING_MOVIES) {
           url = "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?page_limit=20&page=1&country=us&apikey=yedukp76ffytfuy24zsqk7f5";
        }
        // once rest url is logically choose and built prepare callout and prepare parsing method
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject resp) {
                        //handle a valid response coming back.  Getting this string mainly for debug
                        response = resp.toString();
                        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
                        JSONObject obj1 = null;
                        JSONArray array = null;
                        try {
                            array = resp.getJSONArray("movies");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        assert obj1 != null;
                        //From that object, we extract the array of actual data labeled result

                        ArrayList<Movie> movies = new ArrayList<>();
                        for(int i=0; i < array.length(); i++) {
                            try {
                                //for each array element, we have to create an object
                                JSONObject jsonObject = array.getJSONObject(i);
                                Movie s = new Movie(jsonObject.optString("title"), jsonObject.optString("id"),jsonObject.optString("year"),jsonObject.optString("mpaa_rating") );
                                assert jsonObject != null;

                                //save the object for later
                                movies.add(s);

                            } catch (JSONException e) {
                                Log.d("VolleyApp", "Failed to get JSON object");
                                e.printStackTrace();
                            }
                        }
                        //once we have all data, then go to list screen
                        changeView(movies);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        response = "JSon Request Failed!!";
                        //show error on phone
//                        TextView view = (TextView) findViewById(R.id.textView2);
//                        view.setText(response);
                    }
                });
        //this actually queues up the async response with Volley
        queue.add(jsObjRequest);

    }
    private void changeView(ArrayList<Movie> movies) {
        Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
        // send all movies into next activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("SEARCH", movies);
        startActivity(i);
    }


}
