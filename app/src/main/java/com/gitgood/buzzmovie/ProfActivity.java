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
import android.widget.Toast;


public class ProfActivity extends AppCompatActivity {

    private EditText mName;
    private EditText mmMajor;
    private EditText mInterest;
    private Button bUpdate;
    private Button bCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_prof);
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

        bCancel = (Button) findViewById(R.id.bbCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        mName = (EditText) findViewById(R.id.eName);
        mmMajor = (EditText) findViewById(R.id.eMajor);
        mInterest = (EditText) findViewById(R.id.eInterest);

        SharedPreferences sharedpreferences = getSharedPreferences(
                getResources().getString(R.string.CurrentUser), Context.MODE_PRIVATE);


        SharedPreferences sharedpreferences2 = getSharedPreferences(
                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);

        mName.setText(sharedpreferences2.getString("username" + "1", "Set NAme" ));
        mmMajor.setText(sharedpreferences2.getString("username" + "2", "Set Major"));
        mInterest.setText(sharedpreferences2.getString("username" + "3", "Set Interests"));

//        SharedPreferences sharedpreferences2 = getSharedPreferences(
//                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);

        Log.v("||||DAN||1||", sharedpreferences2.getString("username" + "1", "1"));
        Log.v("||||DAN||2||", sharedpreferences2.getString("username" + "2", "2"));
        Log.v("||||DAN||3||", sharedpreferences2.getString("username" + "3", "3"));

        bUpdate = (Button) findViewById(R.id.bbUpdate);
        final SharedPreferences.Editor editor2 = sharedpreferences2.edit();
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("||||DAN||||", "IN CLICK");
                editor2.putString("username" + "1", mName.getText().toString());
                editor2.putString("username" + "2", mmMajor.getText().toString());
                editor2.putString("username" + "3", mInterest.getText().toString());
                editor2.apply();
                Intent i = new Intent(getApplicationContext(), Main2Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

//        SharedPreferences sharedpreferences = getSharedPreferences(
//                getResources().getString(R.string.CurrentUser), Context.MODE_PRIVATE);

        Log.v("||||DAN||||", sharedpreferences.getString("username", "1"));
//        Log.v("||||DAN||||", sharedpreferences.getString("test", "3"));

    }

}
