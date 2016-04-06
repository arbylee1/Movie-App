package com.gitgood.buzzmovie;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
/**
 * Created by Chudy on 3/7/16.
 * monostatic global object that handles all ratings within the system.
 * This Object will do all backend tracking, maintaining, adding, removing, and sorting of ratings in the system
 */
final public class Ratings {
    private SharedPreferences ratingData;  // where we store all rating data
    private Map<String,Rating> ratingsMap = new HashMap<>(); // Maps object to easily read all ratings
    private static Ratings ratings = new Ratings();

    private Ratings(){
    }

    public void setSharedPreference(SharedPreferences ratingData) {
        this.ratingData = ratingData;
    }

    // goes into the sharedpreferences and reloads all back-edn rating data into the front end map object
    public void reloadMapFromMemory() {
        if (ratingData.getAll().size() != 0 && ratingData.getAll() != null && !ratingData.getAll().isEmpty()) {
            Map<String, ?> map1 = ratingData.getAll();
            int counter = 0;
            ratingsMap.clear(); // clear map

            // reload entire map from shared preferences
            for (String key : map1.keySet()) {
                ratingsMap.put(key, new Rating((String) map1.get(key)));
                counter++;
            }
        }
    }

    // loads the var into any activity, with this all its methods can be called
    public static Ratings getInstance() {
        return ratings;
    }

    // takes in the Ratings object and stores it into Shared preferences aswell as our collection
    public void addRatings(Rating newRating) {
        SharedPreferences.Editor ratingsEditor = ratingData.edit();
        ratingsEditor.putString((newRating.getKey()),(newRating.toString()));
        ratingsMap.put(newRating.getKey(), (newRating));
        ratingsEditor.apply();
    }

    // runs through collection and finds all majors representing in the ratings data
    public Set<String> getMajorSet() {
        Set<String> majorSet = new HashSet<String>();
        for (String string : ratingsMap.keySet()) {
            String major = ratingsMap.get(string).getMajor();
            if (!majorSet.contains(major)) {
                majorSet.add(major);
            }
        }
        return majorSet;
    }

    // give collection of all ratinging in system
    public Map<String, Rating> getAllRatings(){
        return ratingsMap;
    }

    // runs through collection and finds ratings assosicated with a  major
    public HashMap<String, Rating> getAllRatingsByMajor(String whatMajor) {
        HashMap<String, Rating> byMajor = new HashMap<String, Rating>();
        for (String string : ratingsMap.keySet()) {
            String major = ratingsMap.get(string).getMajor();
            if (major.equals(whatMajor)) {
                byMajor.put(string, ratingsMap.get(string));
            }
        }
        return byMajor;
    }
    // runs through collection and finds ratings assosicated with a movie (rotten tomato Id)
    public Map<String, Rating> getAllRatingsbyMovie(String whatID) {
        HashMap<String, Rating> byID = new HashMap<String, Rating>();
        for (String string : ratingsMap.keySet()) {
            String id = ratingsMap.get(string).getRTid();
            if (id.equals(whatID)) {
                byID.put(string, ratingsMap.get(string));
            }
        }
        return byID;
    }

    // runs through collection and finds ratings assosicated with a User (Username)
    public Map<String, Rating> getAllRatingsbyUser(String whatUsername) {
        HashMap<String, Rating> byUser = new HashMap<String, Rating>();
        for (String string : ratingsMap.keySet()) {
            String user = ratingsMap.get(string).getOwner();
            if (user.equals(whatUsername)) {
                byUser.put(string, ratingsMap.get(string));
            }
        }
        return byUser;
    }

    // runs through collection and sorts top 2 ratings by major and how highly they were rated
    public List<Rating> getRatingsByMajorInOrder(String Whatmajor){
        HashMap<String, Rating> byMajor = getAllRatingsByMajor(Whatmajor);
        ArrayList<Rating> inOrder = new ArrayList<>();
        Rating highest = new Rating();
        Rating second = new Rating();
        Log.v("D","WAS FIRED");

        for (int i = 0; i < byMajor.size(); i++) {
            String removeString = " ";
            for (String string : byMajor.keySet()) {
                if (highest.getStars() < ratingsMap.get(string).getStars()) {
                    second = highest;
                    highest = ratingsMap.get(string);
                    removeString = string;
                }
            }
            if (!removeString.equals(" ")) {
                inOrder.add(highest);
                if (second.getTitle() != null) {
                    inOrder.add(second);
                }
                byMajor.remove(removeString);
            }
        }

        return inOrder;
    }
}
