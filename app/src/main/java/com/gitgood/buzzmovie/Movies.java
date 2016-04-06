package com.gitgood.buzzmovie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Albert on 2/28/2016.
 */
public class Movies {
    /**
     * Monostate class to store the Movies most recently searched for by the user.
     */
        public static final List<Movie> ITEMS = new ArrayList<>();

        /**
         * A map of Movies by Name.
         */
        public static final Map<String, Movie> ITEM_MAP = new HashMap<>();

        /*
        * method to add a movie to the total collection of movies in system
        * @param item Movie object that is added to ourt cumalitve collection
         */
        public static void addItem(Movie item) {
            ITEMS.add(item);
            ITEM_MAP.put(item.getRottenTomatoID(), item);
        }
            /*
        * Method to clear all the whole collection
         */
         public static void clear() {
             ITEMS.clear();
             ITEM_MAP.clear();
         }
}
