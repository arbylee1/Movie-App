package com.gitgood.buzzmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gitgood.buzzmovie.model.Callback;
import com.gitgood.buzzmovie.model.SharedPreferencesProvider;
import com.gitgood.buzzmovie.model.VolleyProvider;
import com.gitgood.buzzmovie.model.ProviderInterface;

import org.json.JSONException;

public class RegistrationActivity extends AppCompatActivity {
    /**
     * Keep track of the registration task to ensure we can cancel it if requested.
     */
    // UI references.
    private EditText mUsernameView;
    private EditText mPasswordView;
    private RadioButton adminCreator;
    private ProviderInterface provider = SharedPreferencesProvider.getInstance(this);

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
            provider.registerUser(username, password, adminCreator.isChecked(), new Callback<String>() {
                public void onSuccess(String result) {
                    completeRegistration(result);
                }
            });
        }
    }
    private void completeRegistration(String result) {
        Toast message = Toast.makeText(getApplicationContext(), "Registration Successful.", Toast.LENGTH_SHORT);
        if (result == null) {
            message.setText("Registration failed. Check Internet Connection");
            message.show();
        } else if (result.equals("duplicate")) {
            mUsernameView.setError(getString(R.string.error_duplicate_email));
            mUsernameView.requestFocus();
        } else {
            message.show();
            finish();
        }
    }
}
