package com.gitgood.buzzmovie;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ProfileActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String[] userProfileData = new String[5]; // String Array that holds user info for Listview
        // Instantiate info using current user object getters/setters
        User test = new User("test@gmail.com", "Test", "tester", "CS", "");
        userProfileData[0] = test.getEmail();
        userProfileData[1] = test.getName();
        userProfileData[2] = test.getUserName();
        userProfileData[3] = test.getMajor();
        userProfileData[4] = test.getInterests();
        ListView userProfileListView = getListView();

        // Links the user data to the view through an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.content_profile, R.id.textView4, userProfileData);
        userProfileListView.setAdapter(adapter);
    }

}
