package com.gitgood.buzzmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gitgood.buzzmovie.model.Callback;
import com.gitgood.buzzmovie.model.ProviderInterface;
import com.gitgood.buzzmovie.model.SharedPreferencesProvider;

import org.json.JSONException;
import org.json.JSONObject;


public class ProfActivity extends AppCompatActivity {

    // set all modifiedable widgets in Activity
    private EditText mName;
    private EditText mMajor;
    private EditText mInterest;
    private JSONObject values;
    private User curr = User.currentUser;
    private ProviderInterface providerInterface;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prof);

        // Set cancel button => Resets the View
        Button bCancel = (Button) findViewById(R.id.bbCancel);
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(getIntent());
            }
        });
        providerInterface = SharedPreferencesProvider.getInstance(this);

        // Instatiate set all modifiedable widgets
        mName = (EditText) findViewById(R.id.eName);


        mMajor = (EditText) findViewById(R.id.eMajor);
        mInterest = (EditText) findViewById(R.id.eInterest);
        TextView mUserType = (TextView) findViewById(R.id.userType);

        // Logic to get Current User and all associated fields as well as fill current values into
        // the view
        mName.setText(curr.getName());
        mMajor.setText(curr.getMajor());
        mInterest.setText(curr.getInterests());
        if (curr.getAdminStatus()) {
            mUserType.setText("User Type: Admin");
        } else {
            mUserType.setText("User Type: Student");
        }


        // Set update button and logic. Logic will take all field values and update user fields in
        // the backend
        Button bUpdate = (Button) findViewById(R.id.bbUpdate);
        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    values = new JSONObject();
                    values.put("name", mName.getText());
                    values.put("major", mMajor.getText());
                    values.put("email", "");
                    values.put("interests", mInterest.getText());
                    providerInterface.updateProfile(curr.getUsername(), curr.getAuthToken(), values, new Callback<JSONObject>() {
                        @Override
                        public void onSuccess(JSONObject result) {
                            onPostExecute(result);
                        }
                    });
                } catch (JSONException e) {
                    Log.d("PostExecute", "JSONException. Wat.");
                }
            }
        });
    }
    private void onPostExecute(JSONObject result) {
        Toast msg = Toast.makeText(this, "Successfully updated!", Toast.LENGTH_SHORT);
        if(result == null) {
            msg.setText("Something failed. Check internet connection and try again.");
        } else if (result.isNull("error")) {
            try {
                User.currentUser.setName(result.getString("name"));
                User.currentUser.setMajor(result.getString("major"));
                User.currentUser.setInterests(result.getString("interests"));
                msg.show();
            } catch (JSONException e) {
                msg.setText("Something failed. Check internet connection and try again.");
            }
        } else {
            msg.setText("Your session has expired. Please log back in.");
        }
    }

}
