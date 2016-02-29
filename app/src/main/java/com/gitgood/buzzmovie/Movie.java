package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
/**
 * Created by Chudy on 2/21/16.
 */
public class Movie implements Serializable {
    String title;
    String rottenTomatoID;
    String year;
    String rating;
    String synopsis;
    float averageRating = 0;
    int userRatings[] = new int[5];

    public Movie(String movie, String Id, String year, String rating, String synopsis,
                 float averageRating, int[] userRatings) {
        this.title = movie;
        this.rottenTomatoID = Id;
        this.year = year;
        this.rating = rating;
        this.synopsis = synopsis;
        this.averageRating = averageRating;
        this.userRatings = userRatings;

    }

    public void addRating(int stars) {
        userRatings[stars - 1]++;
        float newAverageRating = 0;
        int numRatings = 0;
        for(int i = 0; i < 5; i++) {
            numRatings += userRatings[i];
            newAverageRating += (i + 1) * (userRatings[i]);
        }
        averageRating = newAverageRating / numRatings;
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
}
