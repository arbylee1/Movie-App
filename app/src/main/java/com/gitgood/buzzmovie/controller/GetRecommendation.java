package com.gitgood.buzzmovie.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;

import com.gitgood.buzzmovie.R;
import com.gitgood.buzzmovie.model.Rating;
import com.gitgood.buzzmovie.model.Ratings;

import java.util.List;
import java.util.Set;

// Controller for Recomendation Activity.
public class GetRecommendation extends AppCompatActivity {

    @Override
     protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recommendation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // reload all Ratings data to make sure its updated and accurate
        Ratings.getInstance().setSharedPreference(getSharedPreferences("RatingData", Context.MODE_PRIVATE));
        Ratings.getInstance().reloadMapFromMemory();

        // get all Majors represented and display to user. This lets the User know what he can sort by
        Set<String> majorSet = Ratings.getInstance().getMajorSet();
        String majorList = "  ";
        if (majorSet.size() > 0) {
            for(String major : majorSet) {
                // Ratings without majors are filled with "!", we will be ignoring those
                if (!major.equals("!")) {
                    majorList += major + " ";
                }
            }
            ((TextView) findViewById(R.id.MajorList)).setText(majorList);
        }

        Button getRecButton = (Button) findViewById(R.id.button3);
        // once the criteria is set we get our ratings collection and query it using its methods and
        // the criteria given the user
        getRecButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText searchEditText = (EditText) findViewById(R.id.editText);
                String findme = searchEditText.getText().toString();
                TextView response = (TextView) findViewById(R.id.resultsView);
                String result = " ";
                if (Ratings.getInstance().getMajorSet().contains(findme)) {
                    List<Rating> ratingsInorder = Ratings.getInstance().getRatingsByMajorInOrder(findme);
                    // make it into an easily displayed string
                    for (int i = 0; (i < ratingsInorder.size() && i < 3); i++) {
                        result += (ratingsInorder.get(i).getTitle() + "   " + ratingsInorder.get(i).getStars() + "/5.0    ");
                    }
                    response.setText(result);
                } else {
                    // if nothing is found
                    response.setText("No Ratings have been added by that major");
                }
            }
        });

    }

}
