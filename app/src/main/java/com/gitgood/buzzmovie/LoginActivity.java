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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mUsername;
        private final String mPassword;

        UserLoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            SharedPreferences sharedpreferences = getSharedPreferences(
                    getResources().getString(R.string.UserInfo), Context.MODE_PRIVATE);
            String pass = sharedpreferences.getString(mUsername,null);
            return (pass != null) && mPassword.equals(pass);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            if (success) {
                Toast toast = Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT);
                toast.show();
                SharedPreferences sharedpreferences = getSharedPreferences(
                        getResources().getString(R.string.CurrentUser), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(mUsername, mPassword);
                editor.apply();
                goToMain2();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
        }
    }

    private void goToMain2() {
        Intent i = new Intent(getApplicationContext(), Main2Activity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}

