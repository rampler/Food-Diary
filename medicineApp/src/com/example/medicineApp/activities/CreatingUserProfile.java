package com.example.medicineApp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.medicineApp.R;
import com.example.medicineApp.helpers.AppController;
import com.example.medicineApp.helpers.DialogControl;
import com.example.medicineApp.helpers.Globals;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sabina on 2014-12-01.
 */
public class CreatingUserProfile extends Activity {

    private static String TAG = CreatingUserProfile.class.getSimpleName();
    private Button newProfileButton;
    private EditText firstname;
    private EditText surname;
    private EditText weight;
    private EditText calories;
    private EditText age;
    private ProgressDialog pDialog;
    private Globals g;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creating_user_profile);
        g = Globals.getInstance();
        SetControls();


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        newProfileButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CreateNewProfile();
            }
        });

    }

    private void CreateNewProfile() {

        DialogControl.showDialog(pDialog);




        String uri2 = String.format(g.getServerURL() + "/profile/new?sessionId=%1$s&firstName=%2$s&lastName=%3$s&weight=%4$s&caloriesCounter=%5$s&age=%6$s",
                g.getSessionId(),
                firstname.getText().toString(),
                surname.getText().toString(),
                weight.getText().toString(),
                calories.getText().toString(),
                age.getText().toString());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri2, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    String result = response.getString("id");
                    if (result!= null) {
                        HandleProfileCreatingSuccess();
                    } else {
                        HandleProfileCreatingError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    HandleProfileCreatingError();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                HandleProfileCreatingError();
            }
        }) {

            protected Map<String, String> getParams() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sessionId", g.getSessionId());
                params.put("firstName", firstname.getText().toString());
                params.put("lastName", surname.getText().toString());
                params.put("weight", weight.getText().toString());
                params.put("caloriesCounter", calories.getText().toString());
                params.put("age", age.getText().toString());
                return params;
            };
        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



    private void SetControls() {
        newProfileButton = (Button) findViewById(R.id.profile_create_button);
        firstname = (EditText) findViewById(R.id.profile_user_name);
        surname = (EditText) findViewById(R.id.profile_user_surname);
        weight = (EditText) findViewById(R.id.profile_user_weight);
        calories = (EditText) findViewById(R.id.profile_user_weight);
        age = (EditText) findViewById(R.id.profile_user_age);
    }

    private void HandleProfileCreatingSuccess() {
        DialogControl.hideDialog(pDialog);
        Toast.makeText(getApplicationContext(), "New profile created", Toast.LENGTH_SHORT);
        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
        startActivity(intent);
        finish();
    }

    private void HandleProfileCreatingError() {
        DialogControl.hideDialog(pDialog);
        Toast.makeText(getApplicationContext(), "Error creating a new profile", Toast.LENGTH_SHORT);
    }
}
