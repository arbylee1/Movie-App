package com.gitgood.buzzmovie;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by Orange Blossom on 2/28/2016.
 * This static object keeps track of the Current users and can be called anywhere in the appilcation
 * this makes user tracking and admin verification easy. Only gets reset at login screen
 */
public final class CurrentUser {
    private static CurrentUser currentUser = new CurrentUser();
    private static String username;
    private static String passwordHash;
    private static Boolean isAdmin;
    private CurrentUser() {
    }

    /**
     * Getter method for the static currentUser class. retrieves the current user
     * @return Currentuser the instance of Currentuser, with all values of the user loaded and ready
     * to be read/set
     */
    public static CurrentUser getInstance() {
        return currentUser;
    }

    /**
     *  setter method for username
     *  @param username String value with username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *  setter method for password
     *  @param passwordHash String value with hashed password
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    /**
     *  setter method for isAdmin
     *  @param isAdmin Boolean value with whether currentuser is an admin or not
     */
    public void setUserIsAdmin(Boolean isAdmin) {this.isAdmin = isAdmin; }

    /**
     *  getter method for isAdmin
     *  @return isAdmin Boolean value with whether currentuser is an admin or not
     */
    public Boolean getIsAdmin() {return isAdmin; }

    /**
     *  getter method for username
     *  @return username String value with username
     */
    public String getUsername() {
        return username;
    }

    /**
     *  getter method for password
     *  @return passwordHash String value with hashed password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     *  method to get all users in system
     *  @param userinfo Sharedpreference object utility used to store and pull data from internal
     *  data storage
     *  @return List<User> List datatype with all Users in system
     */
    public List<User> getAllUsers(SharedPreferences userinfo) {
    Map <String,?> all = userinfo.getAll();
        Map <String,String> usernames = new HashMap <String,String>();
        Map <String,String> passwords = new HashMap <String,String>();
        Map <String,String> names = new HashMap <String,String>();
        Map <String,String> interests = new HashMap <String,String>();
        Map <String,String> majors = new HashMap <String,String>();
        Map <String,Boolean> admins = new HashMap <String,Boolean>();
        Map <String,Boolean> bans = new HashMap <String,Boolean>();

        List<User> users = new ArrayList<User>();

        Set<String> keys = all.keySet();
        String ending = null;
        for (String n : keys) {
            Log.v("|D|", n + "  => " + all.get(n));
            Log.v("|D|", n.substring(n.length() - 4 , n.length()));
            ending = n.substring(n.length() - 4 , n.length());
            switch (ending) {
                case "salt":
                    usernames.put(n.substring(0, n.length() - 4), (String) all.get(n));
                    break;
                case "hash":
                    passwords.put(n.substring(0, n.length() - 4), (String) all.get(n));
                    break;
                case "name":
                    names.put(n.substring(0, n.length() - 5), (String) all.get(n));
                    break;
                case "ests":
                    interests.put(n.substring(0, n.length() - 10), (String) all.get(n));
                    break;
                case "ajor":
                    majors.put(n.substring(0, n.length() - 6), (String) all.get(n));
                    break;
                case "dmin":
                    admins.put(n.substring(0, n.length() - 7), (Boolean) all.get(n));
                    break;
                case "_ban":
                    bans.put(n.substring(0, n.length() - 4), (Boolean) all.get(n));
                default:
                    break;

            }
        }
        Set<String> userSet = usernames.keySet();
        for (String user : userSet) {
            User realUser = new User(user, names.get(user), bans.get(user), admins.get(user));
            users.add(realUser);
            Log.v("|DAN|", realUser.toString());
        }
        return users;
    }

    /**
     *  method to get all users in system
     *  @param userinfo Sharedpreference object utility used to store and pull data from internal
     *  data storage
     *  @param username String of the username to be toggled
     *  @return boolean of current user isBanned var after it has been toggled
     */
    public boolean toggleIsBan(SharedPreferences userinfo, String username) {
        String ban = "_ban";
        Boolean isbanned = userinfo.getBoolean(username + ban, false);
        Log.v("|DONE| ", isbanned.toString());
        SharedPreferences.Editor editor = userinfo.edit();
        if (isbanned) {
            editor.putBoolean(username + ban, false);
        } else {
            editor.putBoolean(username + ban, true);
        }
        editor.apply();

        return !isbanned;
    }
}
