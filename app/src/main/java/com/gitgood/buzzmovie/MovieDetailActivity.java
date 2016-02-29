package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Albert on 2/28/2016.
 */
public class MovieDetailActivity extends AppCompatActivity {
    final DecimalFormat twoPrecision = new DecimalFormat(".00");
    SharedPreferences userInfo;
    SharedPreferences movieData;
    CurrentUser currentUser;
    float previousUserRating;
    RatingBar ratingBar;
    TextView averageRating;
    Movie movie;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        userInfo = getSharedPreferences(
                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
        movieData = getSharedPreferences(
                getResources().getString(R.string.MovieData), Context.MODE_PRIVATE);
        currentUser = CurrentUser.getInstance();
        movie = Movies.ITEM_MAP.get(getIntent().getExtras().get("Movie"));
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
        String average = "Average Rating: " + twoPrecision.format(movie.getAverageRating());
        averageRating.setText(average);
    }
    private void updateRating() {
        float newUserRating = ratingBar.getRating();
        if (previousUserRating == 0) {
            if(newUserRating != 0) {
                movie.addRating(newUserRating);
            }
        } else {
            if (newUserRating != 0) {
                movie.addRating(newUserRating);
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
        String average = "Average Rating: " + twoPrecision.format(movie.getAverageRating());
        averageRating.setText(average);
    }
}
