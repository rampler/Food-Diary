package com.example.medicineApp;

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
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sabina on 2014-11-30.
 */
public class Logging extends Activity {

    private static String TAG = MainActivity.class.getSimpleName();
    private Button logIn;
    private ProgressDialog pDialog;
    private EditText userLogin;
    private EditText userPassword;
    private Globals g;
    //http://foodiary.ddns.net:8080/user/getId?login=testowy

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

        showpDialog();
        String uri = String.format("http://foodiary.ddns.net:8080/user/getId?login=%1$s&password=%2$s", userLogin.getText(), userPassword.getText());


        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    String id = response.getString("id");
                    g = Globals.getInstance();
                    g.setSessionId(id);
                    hidepDialog();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Logowanie nie powiodło się", Toast.LENGTH_SHORT).show();
                    hidepDialog();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Logowanie nie powiodło się", Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void ClearIdText(View view) {
        EditText userId = (EditText) findViewById(R.id.userIdEdit);
        userId.setText("");
    }

    public void ClearPasswordText(View view) {
        EditText password = (EditText) findViewById(R.id.passwordEdit);
        password.setText("");
    }

}
