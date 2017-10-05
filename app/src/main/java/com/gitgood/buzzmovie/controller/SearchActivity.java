package com.gitgood.buzzmovie.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.gitgood.buzzmovie.model.CurrentUser;
import com.gitgood.buzzmovie.model.Movie;
import com.gitgood.buzzmovie.model.Movies;
import com.gitgood.buzzmovie.R;
import com.gitgood.buzzmovie.model.Ratings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchActivity extends AppCompatActivity {
    public static final int ROTTEN_TOMATO_GET_MATCH = 1;
    public static final int ROTTEN_TOMATO_GET_NEW_DVD = 2;
    public static final int ROTTEN_TOMATO_GET_UPCOMING_MOVIES = 3;
    private RequestQueue queue;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Load all contents and prepare Volley Queue
        setContentView(R.layout.activity_search);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public final boolean onCreateOptionsMenu(Menu menu) {
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
        // profile button
        Button userProfileButton = (Button) findViewById(R.id.userProfileButton);
        // take User to Profile Form Screen
        userProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ProfActivity.class);
                startActivity(i);
            }
        });

        Log.v("IS AN ME, DANIEL" , CurrentUser.getInstance().getIsAdmin().toString());
        if (CurrentUser.getInstance().getIsAdmin()) {
            Button adminButton = (Button) findViewById(R.id.AdminControlButton);
            adminButton.setVisibility(View.VISIBLE);
            adminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getApplicationContext(), AdminUserListActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                }
            });
        }

        // logout button
        Button logOutButton = (Button) findViewById(R.id.LogOut);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get and then clear Current User
                SharedPreferences sharedpreferences = getSharedPreferences(
                        getResources().getString(R.string.CurrentUser), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.apply();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        // Button to go to recomendation Screen
        Button getRecomendationButton = (Button) findViewById(R.id.button2);
        getRecomendationButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), GetRecommendation.class);
                // send all movies into next activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
        // match search button
        Button searchButton = (Button) findViewById(R.id.button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactRottenTomatoes(ROTTEN_TOMATO_GET_MATCH);
            }
        });
        //Top dvd button
        Button topDVDButton = (Button) findViewById(R.id.buttonDvd);
        topDVDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactRottenTomatoes(ROTTEN_TOMATO_GET_NEW_DVD);
            }
        });
        // upcoming movie button
        Button newRelButton = (Button) findViewById(R.id.buttonRel);
        newRelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contactRottenTomatoes(ROTTEN_TOMATO_GET_UPCOMING_MOVIES);
            }
        });
        return true;
    }

    private void contactRottenTomatoes (int id) {
        // All search buttons use this method the int destiguishes what search to use
        String url = "";
        if (id == ROTTEN_TOMATO_GET_MATCH) {
            EditText searchText = (EditText) findViewById(R.id.searchText);
            String searchFor = searchText.getText().toString();
            String myNewStr = searchFor.replaceAll(" ", "%20;");
            url = "http://api.rottentomatoes.com/api/public/v1.0/movies.json?q=" + myNewStr + "&page_limit=20&page=1&apikey=yedukp76ffytfuy24zsqk7f5";
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
                        onPostExecute(resp);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        onPostExecute(null);
                    }
                });
        queue.add(jsObjRequest);

    }
    private void changeView() {
        Intent i = new Intent(getApplicationContext(), SearchResultsActivity.class);
        // send all movies into next activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void onPostExecute(JSONObject resp) {
        //handle a valid response coming back.  Getting this string mainly for debug
        //Now we parse the information.  Looking at the format, everything encapsulated in RestResponse object
        if(resp != null) {
            JSONArray array = null;
            try {
                array = resp.getJSONArray("movies");
                Movies.clear();
            } catch (JSONException e) {
                Log.i("adssda", e.toString());
            }
//                        Movies.clear();
            SharedPreferences sharedPreferences = getSharedPreferences("MovieData", MODE_PRIVATE);
            Ratings.getInstance().setSharedPreference(getSharedPreferences("RatingData", Context.MODE_PRIVATE));
            Ratings.getInstance().reloadMapFromMemory();
            for (int i = 0; i < array.length(); i++) {
                try {
                    //for each array element, we have to create an object
                    JSONObject jsonObject = array.getJSONObject(i);
                    assert jsonObject != null;
                    String title = jsonObject.optString("title");
                    String id = jsonObject.optString("id");
                    String year = jsonObject.optString("year");
                    String rating = jsonObject.optString("mpaa_rating");
                    String synopsis = jsonObject.optString("synopsis");
                    float numRatings = sharedPreferences.getFloat(id + "numRatings", 0.0f);
                    float averageRating = sharedPreferences.getFloat(id + "averageRating", 0.0f);

                    Movie s = new Movie(title, id, year, rating, synopsis, numRatings, averageRating);
                    //save the object for later
                    Movies.addItem(s);

                } catch (JSONException e) {
                    Log.d("VolleyApp", "Failed to get JSON object");
                }
            }
            //once we have all data, then go to list screen
            changeView();
        }
    }

}
