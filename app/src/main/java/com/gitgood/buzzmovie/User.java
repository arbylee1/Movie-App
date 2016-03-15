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
    private String password;
    private String major;
    private String interests;
    private boolean isAdmin;
    private boolean isBanned;

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

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() { return password; }

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

    public void setPassword(String input) {this.password = input; }

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
        return (userName + " " + name + " " + isAdmin + " " + isBanned);
    }
}
