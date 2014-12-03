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
import com.example.medicineApp.R;
import com.example.medicineApp.helpers.DialogControl;
import com.example.medicineApp.helpers.Globals;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sabina on 2014-11-30.
 */
public class Registering extends Activity {

    private static String TAG = Registering.class.getSimpleName();
    private Button registerButton;
    private EditText username;
    private EditText mail;
    private EditText password;
    private ProgressDialog pDialog;
    private Globals g;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        g = Globals.getInstance();
        SetControls();

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RegisterNewUser();
            }
        });
    }

    private void RegisterNewUser() {
        DialogControl.showDialog(pDialog);
        String uri = String.format(g.getServerURL() + "/register?login=%1$s&password=%2$s&mailAddress=%3$s",
                username.getText(), password.getText(), mail.getText());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    String result = response.getString("result");
                    if (result.equals("true")) {
                        HandleUserRegisteringSuccess();
                    } else {
                        HandleUserRegisteringError();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    HandleUserRegisteringError();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                HandleUserRegisteringError();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    public void ClearUsernameText(View view) {
        EditText userId = (EditText) findViewById(R.id.registerUsername);
        userId.setText("");
    }

    public void ClearRegisterPasswordText(View view) {
        EditText password = (EditText) findViewById(R.id.registerPassword);
        password.setText("");
    }

    public void ClearEmailText(View view) {
        EditText mail = (EditText) findViewById(R.id.registerEmail);
        mail.setText("");
    }

    private void SetControls() {
        registerButton = (Button) findViewById(R.id.registerButton);
        username = (EditText) findViewById(R.id.registerUsername);
        mail = (EditText) findViewById(R.id.registerEmail);
        password = (EditText) findViewById(R.id.registerPassword);
    }

    private void HandleUserRegisteringError() {
        Toast.makeText(getApplicationContext(), "Error while registering a user", Toast.LENGTH_SHORT).show();
        DialogControl.hideDialog(pDialog);
    }

    private void HandleUserRegisteringSuccess() {
        DialogControl.hideDialog(pDialog);
        Toast.makeText(getApplicationContext(), "User registered", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getApplicationContext(), Logging.class);
        startActivity(intent);
        finish();
    }

}

