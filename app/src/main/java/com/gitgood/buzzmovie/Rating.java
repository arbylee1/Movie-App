package com.gitgood.buzzmovie;

/**
 * Created by Chudy on 3/7/16.
 */

// Information holder for ratings
public class Rating {
    private String rtid;
    private String owner;
    private float number;
    private String major;
    private float stars;
    private String movieTitle;

    // instantied from json. We store all Ratings data as Strings in the backend. Can create objects
    // out of the strings when pulling them out of the backend
    public Rating(String json){
        String[] tokens = json.split(",");
        this.rtid = tokens[0];
        this.owner = tokens[1];
        this.number = Float.parseFloat(tokens[2]);
        this.major = tokens[3];
        this.stars = Float.parseFloat(tokens[4]);
        if (tokens.length == 6) {
            this.movieTitle = tokens[5];
        } else {
            this.movieTitle = "D";
        }
    }

    // basic constructer
    public Rating(String rtid, String owner, float number, String major, float stars, String movieTitle) {
        this.rtid = rtid;
        this.owner = owner;
        this.number = number;
        this.major = major;
        this.stars = stars;
        this.movieTitle = movieTitle;
        Ratings.getInstance().addRatings(this);
    }

    public Rating() {
        this.stars = 0;
    }

    final public String getKey() {
        return (rtid + "|" + owner);
    }

    // method to turn entire object into a String. This makes it easy to store in the backend
    final public String toString(){
        return (rtid + "," + owner + "," + number + "," + major + "," + stars + "," + movieTitle);
    }

    final public String getMajor(){
        return this.major;
    }

    final public String getRTid() {
        return this.rtid;
    }

    final public String getOwner() {
        return this.owner;
    }

    final public float getStars() {
        return this.stars;
    }

    final public String getTitle() {
        return this.movieTitle;
    }
}
