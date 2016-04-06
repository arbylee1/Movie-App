package com.gitgood.buzzmovie.model;

/**
 * Created by albert on 4/2/16.
 */
public enum RottenCall {
    GET_MATCH (""),
    GET_NEW_DVDS ("lists/dvds/new_releases.json"),
    GET_UPCOMING_MOVIES ("lists/movies/upcoming.json");

    private String url;
    RottenCall(String url) {
        this.url = url;
    }
    public void setString(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }
}
