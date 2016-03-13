package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;

public class RegistrationActivity extends AppCompatActivity {
    /**
     * Keep track of the registration task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private RadioButton adminCreator;
    private SharedPreferences sharedpreferences;
    private String username;
    private String password;
    private boolean isAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sharedpreferences = getSharedPreferences(getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
        mUsernameView = (EditText) findViewById(R.id.usernameText);
        mUsernameView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mPasswordView = (EditText) findViewById(R.id.passwordText);
        Button registrationButton = (Button) findViewById(R.id.RegSubmitButton);
        Button cancelButton = (Button) findViewById(R.id.RegCancelButton);
        final RadioButton adminCreator = (RadioButton) findViewById(R.id.AdminButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegistration();
            }
        });
    }

    private void attemptRegistration() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        username = mUsernameView.getText().toString();
        password = mPasswordView.getText().toString();


        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (sharedpreferences.contains(username + "hash")) {
            mUsernameView.setError(getString(R.string.error_duplicate_email));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            Provider provider = new Provider(getApplicationContext());
            try {
                provider.getRandomString(new Callback<String>() {
                    public void onSuccess(String result) {
                        completeRegistration(result);
                    }

                    public void onFailure(String result) {
                        completeRegistration(result);
                    }
                });
            } catch (JSONException e) {
            }
        }
    }
    private void completeRegistration(String result) {
        Toast message = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        if (result == null) {
            message.setText("Registration failed. Check Internet Connection");
        } else {
            try {
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                password += result;
                password = new String(digest.digest(password.getBytes()));
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(username + "hash", password);
                editor.putString(username + "salt", result);
                editor.apply();
                message.setText("Registration Successful.");
                finish();
            } catch (NoSuchAlgorithmException e) {
            }
        }
        message.show();
    }
}
