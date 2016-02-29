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
     *
     */
        public static final List<Movie> ITEMS = new ArrayList<>();

        /**
         * A map of Movies  by Name.
         */
        public static final Map<String, Movie> ITEM_MAP = new HashMap<>();

        public static void addItem(Movie item) {
            ITEMS.add(item);
            ITEM_MAP.put(item.getRottenTomatoID(), item);
        }

        private static String makeDetails(int position) {
            StringBuilder builder = new StringBuilder();
            builder.append("Details about Movie: ").append(position);
            for (int i = 0; i < position; i++) {
                builder.append("\nMore details information here.");
            }
            return builder.toString();
        }
}
