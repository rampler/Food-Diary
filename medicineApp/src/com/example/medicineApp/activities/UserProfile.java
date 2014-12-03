package com.example.medicineApp.activities;

import android.content.Intent;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.medicineApp.helpers.*;
import com.example.medicineApp.R;
import com.example.medicineApp.objects.Product;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import java.io.IOException;

public class UserProfile extends Activity {


    private static String TAG = UserProfile.class.getSimpleName();
    private Button btnMakeObjectRequest, btnMakeArrayRequest;
    private Button createNewProfile;

    // Progress dialog
    private ProgressDialog pDialog;

    private ListView productListView;
    private CustomListAdapter listAdapter;
    private Globals g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        g = Globals.getInstance();

        btnMakeArrayRequest = (Button) findViewById(R.id.download_products);
        createNewProfile = (Button) findViewById(R.id.createProfileButton);

        productListView = (ListView) findViewById(R.id.productListView);
        listAdapter = new CustomListAdapter(this, g.getProducts());
        productListView.setAdapter(listAdapter);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnMakeArrayRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                makeJsonArrayRequest();
            }
        });
        createNewProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CreateNewProfile();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                try {
                    if (Session.LogOut(this)) {
                        Toast.makeText(getApplicationContext(), "User no longer logged in", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, Logging.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void makeJsonArrayRequest() {
        showpDialog();
        String urlJsonArry = g.getServerURL() + "/product/list.json";
        JsonArrayRequest req = new JsonArrayRequest(urlJsonArry,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject product = (JSONObject) response
                                        .get(i);

                                String id = product.getString("id");
                                String name = product.getString("name");
                                String calories = product.getString("calories");
                                String carbs = product.getString("carbs");
                                String protein = product.getString("protein");
                                String fat = product.getString("fat");
                                String type = product.getString("category");

                                Product productObject = new Product(id, name, calories, carbs, protein, fat, type);
                                if (!g.getProducts().contains(productObject)) {
                                    g.addProduct(productObject);
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        hidepDialog();
                        productListView = (ListView) findViewById(R.id.productListView);
                        productListView.setAdapter(listAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void CreateNewProfile() {
        String uri = String.format(g.getServerURL() + "user/hasProfile?sessionId=%1$s", g.getSessionId());

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                uri, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());
                try {
                    String result = response.getString("result");
                    if (result.equals("false")) {
                        Intent intent = new Intent(getApplicationContext(), CreatingUserProfile.class);
                        startActivity(intent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast t = Toast.makeText(getApplicationContext(), "A profile already exists", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }



}
