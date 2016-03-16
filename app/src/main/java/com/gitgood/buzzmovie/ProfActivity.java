package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class ProfActivity extends AppCompatActivity {

    // set all modifiedable widgets in Activity
    private EditText mName;
    private EditText mmMajor;
    private EditText mInterest;
    private TextView mUserType;
    private Button bUpdate;
    private Button bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

        // Set cancel button => Resets the View
        bCancel = (Button) findViewById(R.id.bbCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });

        // Instatiate set all modifiedable widgets
        mName = (EditText) findViewById(R.id.eName);
        mmMajor = (EditText) findViewById(R.id.eMajor);
        mInterest = (EditText) findViewById(R.id.eInterest);
        mUserType = (TextView) findViewById(R.id.userType);

        // Logic to get Current User and all associated fields as well as fill current values into
        // the view
        SharedPreferences currentUser = getSharedPreferences(
                getResources().getString(R.string.CurrentUser), Context.MODE_PRIVATE);

        SharedPreferences userInfo = getSharedPreferences(
                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);

        final String currentUsername = currentUser.getString("username", null);
        mName.setText(userInfo.getString(currentUsername + "_name", "Set Name" ));
        mmMajor.setText(userInfo.getString(currentUsername + "_major", "Set Major"));
        mInterest.setText(userInfo.getString(currentUsername + "_interests", "Set Interests"));
        if (currentUser.getBoolean("isAdmin", false)) {
            mUserType.setText("User Type: Admin");
        } else {
            mUserType.setText("User Type: Student");
        }


        // Set update button and logic. Logic will take all field values and update user fields in
        // the backend
        bUpdate = (Button) findViewById(R.id.bbUpdate);
        final SharedPreferences.Editor editor2 = userInfo.edit();
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor2.putString(currentUsername + "_name", mName.getText().toString());
                editor2.putString(currentUsername + "_major", mmMajor.getText().toString());
                editor2.putString(currentUsername + "_interests", mInterest.getText().toString());
                editor2.apply();
                Intent i = new Intent(getApplicationContext(), SearchActivity.class);
                Toast toast = Toast.makeText(getApplicationContext(), "Profile Succesfully updated", Toast.LENGTH_SHORT);
                toast.show();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }

}
