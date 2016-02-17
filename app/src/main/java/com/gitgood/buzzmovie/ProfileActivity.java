package com.gitgood.buzzmovie;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
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
        SharedPreferences currentUser = getSharedPreferences(getResources().getString(R.string.CurrentUser), Context.MODE_PRIVATE);
        SharedPreferences userInfo = getSharedPreferences(getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
        String currentUsername = currentUser.getString("username", null);
        SharedPreferences.Editor editor = userInfo.edit();
        String[] userProfileData = new String[5]; // String Array that holds user info for Listview
        // Instantiate info using current user object getters/setters
        User user = new User(userInfo.getString(currentUsername + "_email", ""),
                userInfo.getString(currentUsername + "_name", ""),
                currentUsername, userInfo.getString(currentUsername + "_major", ""),
                userInfo.getString(currentUsername + "_interests", ""));
        userProfileData[0] = user.getEmail();
        userProfileData[1] = user.getName();
        userProfileData[2] = user.getUserName();
        userProfileData[3] = user.getMajor();
        userProfileData[4] = user.getInterests();
        ListView userProfileListView = getListView();

        // Links the user data to the view through an ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.content_profile, R.id.textView4, userProfileData);
        userProfileListView.setAdapter(adapter);
    }

}
