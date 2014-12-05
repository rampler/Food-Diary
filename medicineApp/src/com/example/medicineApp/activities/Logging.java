package com.example.medicineApp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.medicineApp.helpers.AppController;
import com.example.medicineApp.helpers.DialogControl;
import com.example.medicineApp.helpers.Globals;
import com.example.medicineApp.R;
import com.example.medicineApp.helpers.Validator;
import com.example.medicineApp.utils.Password;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sabina on 2014-11-30.
 */
public class Logging extends Activity {

    private static String TAG = UserProfile.class.getSimpleName();
    private Button logIn;
    private ProgressDialog pDialog;
    private EditText userLogin;
    private EditText userPassword;
    private Globals g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logging_screen);

        logIn = (Button) findViewById(R.id.loggingButton);
        userLogin = (EditText) findViewById(R.id.userIdEdit);
        userPassword = (EditText) findViewById(R.id.passwordEdit);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        logIn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeLoginRequest();
            }
        });
    }

    private void makeLoginRequest() {

        if (Validator.isEmpty(userLogin.getText()) || Validator.isEmpty(userPassword.getText()))  {
            Toast.makeText(getApplicationContext(), "Is anything empty?", Toast.LENGTH_SHORT).show();
            return;
        }

        String hash = Password.HashPassword(userPassword.getText());

        DialogControl.showDialog(pDialog);
        String uri = String.format(g.getServerURL() + "/login?login=%1$s&password=%2$s", userLogin.getText(), hash);


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    String id = response.getString("id");
                    g = Globals.getInstance();
                    g.setSessionId(id);
                    HandleUserLoggingSuccess();
                } catch (JSONException e) {
                    e.printStackTrace();
                    HandleUserLoggingError();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                HandleUserLoggingError();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }


    public void Register(View view) {
        Intent intent = new Intent(getApplicationContext(), Registering.class);
        startActivity(intent);
    }

    private void HandleUserLoggingSuccess() {
        CheckForProfile();
    }

    private void HandleUserLoggingError() {
        Toast.makeText(getApplicationContext(), "Error while logging in", Toast.LENGTH_SHORT).show();
        DialogControl.hideDialog(pDialog);
    }

    private void CheckForProfile() {
        pDialog.show();
        String uri = String.format(g.getServerURL() + "/user/hasProfile?sessionId=%1$s", g.getSessionId());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    String result = response.getString("result");
                    if (result.equals("true")) {
                        g.setHasProfile(true);
                    } else g.setHasProfile(false);
                    DialogControl.hideDialog(pDialog);
                    Toast.makeText(getApplicationContext(), "User logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast t = Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}
