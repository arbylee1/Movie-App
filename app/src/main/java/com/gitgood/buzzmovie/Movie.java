package com.gitgood.buzzmovie;

import java.io.Serializable;

/**
 * Created by Chudy on 2/21/16.
 */
public class Movie implements Serializable {
    private String title;
    private String rottenTomatoID;
    private String year;
    private String rating;
    private String synopsis;
    private float numRatings = 0;
    private float averageRating = 0.0f;

    public Movie(String movie, String id, String year, String rating, String synopsis, float numRatings
                 ,float averageRating) {
        this.title = movie;
        this.rottenTomatoID = id;
        this.year = year;
        this.rating = rating;
        this.synopsis = synopsis;
        this.numRatings = numRatings;
        this.averageRating = averageRating;
    }

    final public float getAverageRating() {
        return averageRating;
    }

    final public float getNumRatings() {return numRatings;}

    final public void addRating(float stars, String major, String user) {
        averageRating *= numRatings;
        averageRating += stars;
        numRatings++;
        averageRating /= numRatings;
    }

    final public void removeRating(float stars) {
        averageRating *= numRatings;
        averageRating -= stars;
        numRatings--;
        averageRating /= numRatings;
    }

    final public String getMovie() {
        return title;
    }

    final public String getRottenTomatoID() {
        return rottenTomatoID;
    }

    final public String getYear() {
        return year;
    }

    final public String getSynopsis() {return synopsis;}

    final public String getRating() {
        return rating;
    }

    final public String toString() {
        return "Name: " + title + " (" + year + "), Rating: " + rating;
    }

//    public Map<String, Rating> GetRating(SharedPreferences movieData){
//        this.movieData = movieData;
//        return new Map<String, Rating>;
//    }

}
