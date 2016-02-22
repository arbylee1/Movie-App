package com.gitgood.buzzmovie;

import java.io.Serializable;
/**
 * Created by Chudy on 2/21/16.
 */
public class Movie implements Serializable {
    String title;
    String rottenTomatoID;
    String year;
    String rating;

    public Movie(String movie, String Id, String year, String rating) {
        this.title = movie;
        this.rottenTomatoID = Id;
        this.year = year;
        this.rating = rating;
    }

    public String getMovie() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getRating() {
        return rating;
    }

    public String toString() {
        return "Name: " + title + " (" + year + "), Rating: " + rating;
    }
}
