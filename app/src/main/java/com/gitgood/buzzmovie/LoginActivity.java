package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Boolean isAdmin = false;

    @Override
    final protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mUsernameView.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        mPasswordView = (EditText) findViewById(R.id.password);
        Button mUsernameSignInButton = (Button) findViewById(R.id.signin);
        Button back = (Button) findViewById(R.id.logintomain);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mUsernameSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (missing fields), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        // Reset errors.
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        SharedPreferences sharedpreferences = getSharedPreferences(
                getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
        Boolean isbanned = sharedpreferences.getBoolean(username + "_ban",false);

        if (isbanned) {
            mUsernameView.setError("USER IS LOCKED BY ADMIN");
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Perform the user login attempt.
            mAuthTask = new UserLoginTask(username, password);
            mAuthTask.execute((Void) null);
        }
    }

    private void goToMain2() {
        Intent i = new Intent(getApplicationContext(), SearchActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        final protected Boolean doInBackground(Void... params) {
            SharedPreferences sharedpreferences = getSharedPreferences(
                    getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
            String hash = sharedpreferences.getString(mUsername + "hash",null);
            String salt = sharedpreferences.getString(mUsername + "salt",null);
            isAdmin = sharedpreferences.getBoolean(mUsername + "isAdmin", false);
            if(hash != null && salt != null) {
                try {
                    MessageDigest digest = MessageDigest.getInstance("SHA-256");
                    mPassword = new String(digest.digest((mPassword + salt).getBytes()));
                    return hash.equals(mPassword);
                } catch (NoSuchAlgorithmException e) {
                    return false;
                }
            }
            return false;
        }

        @Override
        final protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                Toast toast = Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT);
                toast.show();
                SharedPreferences sharedpreferences = getSharedPreferences(
                        getResources().getString(R.string.CurrentUser), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString("username", mUsername);
                editor.putString("passwordHash", mPassword);
                editor.putBoolean("isAdmin",isAdmin);
                CurrentUser currentUser = CurrentUser.getInstance();
                currentUser.setUsername(mUsername);
                currentUser.setPasswordHash(mPassword);
                currentUser.setUserIsAdmin(isAdmin);
                editor.apply();
                goToMain2();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        final protected void onCancelled() {
            mAuthTask = null;
        }
    }

}

