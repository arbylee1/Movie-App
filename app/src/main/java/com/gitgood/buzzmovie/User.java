package com.gitgood.buzzmovie;

/**
 * Created by Jeff on 2/15/2016.
 * User infoholder class. Will act
 * as superclass and admin will be
 * a subclass of User.
 */
public class User {
    private String email;
    private String name;
    private String userName;
    private String major;
    private String interests;

    public User(String email, String name, String userName, String major) {
        this.email = email;
        this.name = name;
        this.userName = userName;
        this.major = major;
        this.interests = "";
    }

    public User() {
        this.email = "";
        this.name = "";
        this.userName = "";
        this.major = "";
        this.interests = "";
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

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
        this.userName = input;
    }

    public void setMajor(String input) {
        this.major = input;
    }

    public void setInterests(String input) {
        this.interests = input;
    }
}
