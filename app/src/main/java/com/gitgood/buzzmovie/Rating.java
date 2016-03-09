package com.gitgood.buzzmovie;

/**
 * Created by Chudy on 3/7/16.
 */
public class Rating {
    private String RTid;
    private String owner;
    private float number;
    private String major;
    private float stars;
    private String movieTitle;

    public Rating(String json){
        String[] tokens = json.split(",");
        this.RTid = tokens[0];
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

    public Rating(String RTid, String owner, float number, String major, float stars, String movieTitle) {
        this.RTid = RTid;
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

    public String getKey() {
        return (RTid + "|" + owner);
    }
    public String toString(){
        return (RTid + "," + owner + "," + number + "," + major + "," + stars + "," + movieTitle);
    }

    public String getMajor(){
        return this.major;
    }

    public String getRTid() {
        return this.RTid;
    }

    public String getOwner() {
        return this.owner;
    }

    public float getStars() {
        return this.stars;
    }

    public String getTitle() {
        return this.movieTitle;
    }
}
