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

    final public String getEmail() {
        return email;
    }

    final public String getName() {
        return name;
    }

    final public String getUserName() {
        return userName;
    }

    final public String getPassword() { return password; }

    final public String getMajor() {
        return major;
    }

    final public String getInterests() {
        return interests;
    }

    final public void setEmail(String input) {
        this.email = input;
    }

    final public void setName(String input) {
        this.name = input;
    }

    final public void setUserName(String input) {
        this.userName = input;
    }

    final public void setPassword(String input) {this.password = input; }

    final public void setMajor(String input) {
        this.major = input;
    }

    final public void setInterests(String input) {
        this.interests = input;
    }

    final public boolean getAdminStatus() {
        return isAdmin;
    }

    final public void createAdmin() {
        this.isAdmin = true;
    }

    final public void ban() {
        this.isBanned = true;
    }

    final public void unBan() {
        this.isBanned = false;
    }

    final public boolean getBanStatus() {
        return isBanned;
    }

    final public String toString() {
        return (userName + " " + name + " " + isAdmin + " " + isBanned);
    }
}
