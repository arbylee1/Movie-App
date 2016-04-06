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
    String synopsis;
    float numRatings = 0;
    float averageRating = 0.0f;

    /* Trivial constructor for Movie object
    * @param movie String value of movie
    * @param Id String value of Id
    * @param year String value of year it was released
    * @param rating String rt overall rating for movie
    * @param sysnopsis String value of rt movie summary
    * @param numRating how many rating has been submitted for this movie
    * @param averageRating float value of rating out of 5.0
    *
     */
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

    /*
    * getter method for AverageRating
    * @return float of averageRating Variable
     */
    public final float getAverageRating() {
        return averageRating;
    }
    /*
    * getter method for AverageRating
    * @return float of numRating Variable
     */
    public final float getNumRatings() {return numRatings;}

    /*
    * method containg logic for adding a rating to the movie
    * @param stars float rating out of 5
    * @param major String value of major how set the rating
    * @param User String username of the user that set the rating
     */
    public final void addRating(float stars, String major, String User) {
        averageRating *= numRatings;
        averageRating += stars;
        numRatings++;
        averageRating /= numRatings;
    }

    /*
    * method containing logic to remove a rating
    * @param stars float rating out of 5 to be removed
     */
    public final void removeRating(float stars) {
        averageRating *= numRatings;
        averageRating -= stars;
        numRatings--;
        averageRating /= numRatings;
    }

    /*
    * getter method for movie movie title
    * @return String value of title
     */
    public final String getMovie() {
        return title;
    }

    /*
    * getter method for movie's movie title
    * @return String value of rt id
     */
    public final String getRottenTomatoID() {
        return rottenTomatoID;
    }

    /*
    * getter method for movie's release year
    * @return String value of release year
    */
    public final String getYear() {
        return year;
    }

    /*
     * getter method for movie's summary
     * @return String value of movie summary
     */
    public final String getSynopsis() {return synopsis;}

    /*
     * getter method for movie's overall rating
     * @return String value of movie rating
     */
    public final String getRating() {
        return rating;
    }

    /*
     * method to formaulate movie object as string
     * @return String value of summation of movie object
     */
    public final String toString() {
        return "Name: " + title + " (" + year + "), Rating: " + rating;
    }
}
