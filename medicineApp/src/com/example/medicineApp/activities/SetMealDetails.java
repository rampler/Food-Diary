package com.example.medicineApp.activities;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.medicineApp.R;
import com.example.medicineApp.helpers.AppController;
import com.example.medicineApp.helpers.DialogControl;
import com.example.medicineApp.helpers.Globals;
import com.example.medicineApp.utils.DateHandler;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sabina on 2014-12-07.
 */
public class SetMealDetails extends Activity {

    private Globals g = Globals.getInstance();
    private EditText mealName;
    private ProgressDialog addNewMealDialog;
    private ProgressDialog addIngredientsDialog;
    private String mealId = null;
    private Integer ingCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        TextView num = (TextView) findViewById(R.id.textView2);
        num.setText(Integer.toString(g.getProductsForAMeal().size()));

        mealName = (EditText) findViewById(R.id.editText);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateMeal();
            }
        });

    }

    private void CreateMeal() {

        addNewMealDialog = new ProgressDialog(this);
        DialogControl.configureNotCancellable(addNewMealDialog);
        DialogControl.showDialog(addNewMealDialog);
        AddNewMeal();
    }

    private void AddNewMeal() {
        String date = DateHandler.GetTodaysDate();
        String meal = mealName.getText().toString();

        String uri = String.format(g.getServerURL() + "/meal/add?sessionId=%1$s&name=%2$s&consumptionDay=%3$s", g.getSessionId(), meal, date);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Add new meal", response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                mealId = response.getString("id");
                                DialogControl.hideDialog(addNewMealDialog);
                                AddIngredients();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Add new meal", "Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                DialogControl.hideDialog(addNewMealDialog);
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

    private void AddIngredients() {

        addIngredientsDialog = new ProgressDialog(this);
        DialogControl.configureNotCancellable(addIngredientsDialog);
        DialogControl.showDialog(addIngredientsDialog);

        for (int i = 0; i < g.getProductsForAMeal().size(); i++ ) {
            SendIngredientRequest(i);
        }



        DialogControl.hideDialog(addIngredientsDialog);
        Toast.makeText(getApplicationContext(),
                "Meal added!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
        finish();

    }

    private void SendIngredientRequest(int i) {
        String uri = String.format(g.getServerURL() + "/ingredient/add?sessionId=%1$s&productId=%2$s&weight=%3$s&mealId=%4$s",
                g.getSessionId(), g.getProductsForAMeal().get(i).getId(), "250", mealId);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Ingredient request", response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                response.getString("id");
                                ingCounter++;
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("Ingredient request", "Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        AppController.getInstance().addToRequestQueue(req);
    }

}
