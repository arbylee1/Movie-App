package com.gitgood.buzzmovie;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gitgood.buzzmovie.model.Callback;
import com.gitgood.buzzmovie.model.Provider;
import com.gitgood.buzzmovie.model.ProviderInterface;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class RegistrationActivity extends AppCompatActivity {
    /**
     * Keep track of the registration task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private RadioButton adminCreator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mUsernameView = (EditText) findViewById(R.id.usernameText);
        mPasswordView = (EditText) findViewById(R.id.passwordText);
        Button registrationButton = (Button) findViewById(R.id.RegSubmitButton);
        Button cancelButton = (Button) findViewById(R.id.RegCancelButton);
        adminCreator = (RadioButton) findViewById(R.id.AdminButton);
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

        // Check for a valid username
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
            ProviderInterface provider = Provider.getInstance(this);
            try {
                provider.registerUser(username, password, adminCreator.isChecked(), new Callback<String>() {
                    public void onSuccess(String result) {
                        completeRegistration(result);
                    }
                    public void onFailure(String result) {
                        completeRegistration(result);
                    }
                });
            } catch (JSONException e) {
                completeRegistration(null);
            }
        }
    }
    private void completeRegistration(String result) {
        Toast message = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);
        if (result == null) {
            message.setText("Registration failed. Check Internet Connection");
            message.show();
        } else if (result.equals("duplicate")) {
            mUsernameView.setError(getString(R.string.error_duplicate_email));
            mUsernameView.requestFocus();
        } else {
            message.setText("Registration Successful.");
            message.show();
            finish();
        }
    }
}
