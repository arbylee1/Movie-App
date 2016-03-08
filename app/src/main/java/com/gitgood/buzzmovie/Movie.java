package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by Chudy on 2/21/16.
 */
public class Movie implements Serializable {
    String title;
    String rottenTomatoID;
    String year;
    String rating;
    String synopsis;
    float numRatings = 0;
    float averageRating = 0.0f;

    public Movie(String movie, String Id, String year, String rating, String synopsis, float numRatings
                 ,float averageRating) {
        this.title = movie;
        this.rottenTomatoID = Id;
        this.year = year;
        this.rating = rating;
        this.synopsis = synopsis;
        this.numRatings = numRatings;
        this.averageRating = averageRating;
    }

    public float getAverageRating() {
        return averageRating;
    }

    public float getNumRatings() {return numRatings;}

    public void addRating(float stars, String major, String User) {
        averageRating *= numRatings;
        averageRating += stars;
        numRatings++;
        averageRating /= numRatings;

        Rating rating = new Rating(rottenTomatoID, User,(numRatings - 1), major ,stars);
    }

    public void removeRating(float stars) {
        averageRating *= numRatings;
        averageRating -= stars;
        numRatings--;
        averageRating /= numRatings;
    }

    public String getMovie() {
        return title;
    }

    public String getRottenTomatoID() {
        return rottenTomatoID;
    }

    public String getYear() {
        return year;
    }

    public String getSynopsis() {return synopsis;}

    public String getRating() {
        return rating;
    }

    public String toString() {
        return "Name: " + title + " (" + year + "), Rating: " + rating;
    }

//    public Map<String, Rating> GetRating(SharedPreferences movieData){
//        this.movieData = movieData;
//        return new Map<String, Rating>;
//    }

}
