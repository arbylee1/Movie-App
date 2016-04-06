package com.gitgood.buzzmovie;

/**
 * Created by Jeff on 2/15/2016.
 * User infoholder class. Will act
 * as superclass and admin will be
 * a subclass of User.
 */
public class User {
    public User(String email, String name, String userName, String password, String major) {
        this.email = email;
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.major = major;
        this.interests = "";
        this.isAdmin = false;
        this.isBanned = false;
    }

    public User(String name, String userName, String password) {
        this.email = "";
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.major = "";
        this.interests = "";
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public User(String username, String name, Boolean isBanned, Boolean isAdmin) {
        this.userName = username;
        if (name != null) {
            this.name = name;
        }
        if (isBanned != null) {
            this.isBanned = isBanned;
        }
        if (isAdmin != null ) {
            this.isAdmin = isAdmin;
        }
    }
    public final String getEmail() {
        return email;
    }

    public final String getName() {
        return name;
    }

    public final String getUserName() {
        return userName;
    }

    public final String getPassword() { return password; }

    public final String getMajor() {
        return major;
    }

    public final String getInterests() {
        return interests;
    }

    public final void setEmail(String input) {
        this.email = input;
    }

    public final void setName(String input) {
        this.name = input;
    }

    public final void setUserName(String input) {
        this.userName = input;
    }

    public final void setPassword(String input) {this.password = input; }

    public final void setMajor(String input) {
        this.major = input;
    }

    public final void setInterests(String input) {
        this.interests = input;
    }

    public final boolean getAdminStatus() {
        return isAdmin;
    }

    public final void createAdmin() {
        this.isAdmin = true;
    }

    public final void ban() {
        this.isBanned = true;
    }

    public final void unBan() {
        this.isBanned = false;
    }

    public final boolean getBanStatus() {
        return isBanned;
    }

    public final String toString() {
        return (userName + " " + name + " " + isAdmin + " " + isBanned);
    }

    private String email;
    private String name;
    private String userName;
    private String password;
    private String major;
    private String interests;
    private boolean isAdmin;
    private boolean isBanned;
}
