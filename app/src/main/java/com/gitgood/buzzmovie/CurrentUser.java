package com.gitgood.buzzmovie;

/**
 * Created by Orange Blossom on 2/28/2016.
 */
public class CurrentUser {
    private static CurrentUser currentUser = new CurrentUser();
    private String username;
    private String passwordHash;
    private CurrentUser() {
    }
    public static CurrentUser getInstance() {
        return currentUser;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getUsername() {
        return username;
    }
    public String getPasswordHash() {
        return passwordHash;
    }

}
