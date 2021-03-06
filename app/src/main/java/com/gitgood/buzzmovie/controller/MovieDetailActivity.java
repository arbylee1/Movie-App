package com.gitgood.buzzmovie.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.gitgood.buzzmovie.model.CurrentUser;
import com.gitgood.buzzmovie.model.Movie;
import com.gitgood.buzzmovie.model.Movies;
import com.gitgood.buzzmovie.R;
import com.gitgood.buzzmovie.model.Ratings;

import java.text.DecimalFormat;

/**
 * Created by Albert on 2/28/2016.
 */
public class MovieDetailActivity extends AppCompatActivity {
    private final DecimalFormat twoPrecision = new DecimalFormat(".00");
    private SharedPreferences userInfo;
    private SharedPreferences movieData;
    private SharedPreferences ratingData;
    private CurrentUser currentUser;
    private float previousUserRating;
    private RatingBar ratingBar;
    private TextView averageRating;
    private Movie movie;
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ratingData = getSharedPreferences("RatingData", Context.MODE_PRIVATE);
        userInfo = getSharedPreferences(
                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
        movieData = getSharedPreferences(
                getResources().getString(R.string.MovieData), Context.MODE_PRIVATE);
        currentUser = CurrentUser.getInstance();
        movie = Movies.ITEM_MAP.get(getIntent().getExtras().get("Movie"));
        Log.d("Hi", String.valueOf(System.identityHashCode(movie)));
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        previousUserRating = userInfo.getFloat(movie.getRottenTomatoID() +
                currentUser.getUsername() + "rating", 0);
        ratingBar.setRating(previousUserRating);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                updateRating();
            }
        });
        averageRating = (TextView) findViewById(R.id.averageRating);
        updateText();
    }
    private void updateText() {
        float numRatings = movie.getNumRatings();
        String average = null;
        if(numRatings != 0) {
            average = "Average Rating: " + twoPrecision.format(movie.getAverageRating()) + " (out of :"
            + numRatings + " votes)";
        } else {
            average = "No user ratings yet!";
        }
        averageRating.setText(average);
    }
    private void updateRating() {
        float newUserRating = ratingBar.getRating();
        Ratings.getInstance().setSharedPreference(ratingData);
        if (previousUserRating == 0) {
            if(newUserRating != 0) {
                // create a rating based off number of stars, User, and the movie itself.
                movie.addRating(newUserRating, userInfo.getString(currentUser.getUsername() + "_major", "!"), currentUser.getUsername());
            }
        } else {
            if (newUserRating != 0) {
                movie.addRating(newUserRating, userInfo.getString(currentUser.getUsername() + "_major", "!"), currentUser.getUsername());
            }
            movie.removeRating(previousUserRating);
        }

        SharedPreferences.Editor userInfoEditor = userInfo.edit();
        SharedPreferences.Editor movieDataEditor = movieData.edit();
        userInfoEditor.putFloat(movie.getRottenTomatoID() + currentUser.getUsername() + "rating"
                , newUserRating);
        movieDataEditor.putFloat(movie.getRottenTomatoID() + "averageRating"
                , movie.getAverageRating());
        movieDataEditor.putFloat(movie.getRottenTomatoID() + "numRatings"
                , movie.getNumRatings());
        userInfoEditor.apply();
        movieDataEditor.apply();
        previousUserRating = newUserRating;
        updateText();
    }
}
