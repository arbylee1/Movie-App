package com.gitgood.buzzmovie;

import java.util.Map;

/**
 * Created by Jeff on 2/15/2016.
 * User infoholder class. Will act
 * as superclass and admin will be
 * a subclass of User.
 */
public class User {
    public static User currentUser;
    private String username;
    private String authToken;
    private String name;
    private String major;
    private String email;
    private String interests;
    private boolean isAdmin;
    private boolean isBanned;
    private Map<String, Float> ratings;

    public User(String username, String authToken, String name, String major, String email,
                String interests, boolean isAdmin, boolean isBanned, Map<String, Float> ratings) {
        this.username = username;
        this.authToken = authToken;
        this.email = email;
        this.name = name;
        this.username = username;
        this.major = major;
        this.interests = interests;
        this.isAdmin = isAdmin;
        this.isBanned = isBanned;
        this.ratings = ratings;
    }

    public User(String username, Boolean isBanned) {
        this.username = username;
        if (name != null) {
            this.name = name;
        }
        if (isBanned != null) {
            this.isBanned = isBanned;
        }
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAuthToken() { return authToken; }

    public String getMajor() {
        return major;
    }

    public String getInterests() {
        return interests;
    }

    public void setEmail(String input) {
        this.email = input;
    }

    public void setName(String input) {
        this.name = input;
    }

    public void setUserName(String input) {
        this.username = input;
    }

    public void setAuthToken(String input) {this.authToken = input; }

    public void setMajor(String input) {
        this.major = input;
    }

    public void setInterests(String input) {
        this.interests = input;
    }

    public boolean getAdminStatus() {
        return isAdmin;
    }

    public void createAdmin() {
        this.isAdmin = true;
    }

    public void ban() {
        this.isBanned = true;
    }

    public void unBan() {
        this.isBanned = false;
    }

    public boolean getBanStatus() {
        return isBanned;
    }

    public String toString() {
        return (username + " " + name + " " + isAdmin + " " + isBanned);
    }

    public Map<String, Float> getRatings() {
        return ratings;
    }
}
