package com.gitgood.buzzmovie;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
/**
 * Created by Chudy on 3/7/16.
 */
public class Ratings {
    private SharedPreferences ratingData;
    private HashMap<String,Rating> ratingsMap = new HashMap<String,Rating>();
    private static Ratings ratings = new Ratings();

    private Ratings(){
    }

    public void setSharedPreference(SharedPreferences ratingData) {
        this.ratingData = ratingData;
    }

    public void reloadMapFromMemory() {
        if (ratingData.getAll().size() != 0) {
            Log.v("||DAN||", "17");
            if (ratingData.getAll() != null && !ratingData.getAll().isEmpty()) {
                Map<String, ?> map1 = ratingData.getAll();
                int counter = 0;
                ratingsMap.clear();

                Log.v("||DAN||", "start in reloadMpa from data");
                for (String key : map1.keySet()) {
                    Log.v("||DAN||", key + " => " + counter + " => " + map1.get(key));
                    ratingsMap.put(key, new Rating((String) map1.get(key)));
                    counter++;
                }
            }
        }
    }

    public static Ratings getInstance() {
        return ratings;
    }

    public void addRatings(Rating newRating) {
        SharedPreferences.Editor ratingsEditor = ratingData.edit();
        ratingsEditor.putString((newRating.getKey()),(newRating.toString()));
        ratingsMap.put(newRating.getKey(), (newRating));
        ratingsEditor.apply();
    }
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

    public HashMap<String, Rating> getAllRatings(){
        return ratingsMap;
    }

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

    public HashMap<String, Rating> getAllRatingsbyMovie(String whatID) {
        HashMap<String, Rating> byID = new HashMap<String, Rating>();
        for (String string : ratingsMap.keySet()) {
            String id = ratingsMap.get(string).getRTid();
            if (id.equals(whatID)) {
                byID.put(string, ratingsMap.get(string));
            }
        }
        return byID;
    }

    public HashMap<String, Rating> getAllRatingsbyUser(String whatUsername) {
        HashMap<String, Rating> byUser = new HashMap<String, Rating>();
        for (String string : ratingsMap.keySet()) {
            String user = ratingsMap.get(string).getOwner();
            if (user.equals(whatUsername)) {
                byUser.put(string, ratingsMap.get(string));
            }
        }
        return byUser;
    }

    public ArrayList<Rating> getRatingsByMajorInOrder(String Whatmajor){
        HashMap<String, Rating> byMajor = getAllRatingsByMajor(Whatmajor);
        ArrayList<Rating> inOrder = new ArrayList<>();
        Rating highest = new Rating();

        for (int i = 0; i < byMajor.size(); i++) {
            String removeString = " ";
            for (String string : byMajor.keySet()) {
                if (highest.getStars() < ratingsMap.get(string).getStars()) {
                    highest = ratingsMap.get(string);
                    removeString = string;
                }
            }
            if (!removeString.equals(" ")) {
                inOrder.add(highest);
                byMajor.remove(removeString);
            }
        }

        return inOrder;
    }
}
