package com.example.medicineApp.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.medicineApp.R;
import com.example.medicineApp.adapter.ProductListAdapter;
import com.example.medicineApp.helpers.AppController;
import com.example.medicineApp.helpers.DialogControl;
import com.example.medicineApp.helpers.Globals;
import com.example.medicineApp.objects.Product;
import com.example.medicineApp.objects.Workout;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Sabina on 2014-12-07.
 */
public class AddMeal extends Activity {

    private List<Product> products = new ArrayList<Product>();
    private List<Product> checked = new ArrayList<Product>();
    private Globals g = Globals.getInstance();
    private ProgressDialog pDialog;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout_with_button);

        pDialog = new ProgressDialog(this);
        DialogControl.configureNotCancellable(pDialog);
        DialogControl.showDialog(pDialog);


        lv = (ListView) findViewById(R.id.mealList);
        lv.setAdapter(new ProductListAdapter(getApplicationContext(), products ));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = (Product) parent.getItemAtPosition(position);
                if (product.isChecked()) {
                    product.setChecked(false);
                    if (checked.contains(product)) {
                        checked.remove(product);
                    }
                } else {
                    product.setChecked(true);
                    checked.add(product);
                }
                lv.setAdapter(new ProductListAdapter(getApplicationContext(), products ));
            }
        });

        GetProducts();
        Button addNew = (Button) findViewById(R.id.addMealButton);
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddProducts();
            }
        });
    }

    public void GetProducts() {
        String uri = String.format(g.getServerURL() + "/product/list");

        JsonArrayRequest req = new JsonArrayRequest(uri,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("AddMeal", response.toString());

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
                                String category = product.getString("category");

                                if (products.isEmpty()) {
                                    products = new ArrayList<Product>();
                                }

                                Product productObj = new Product(id, name, calories, carbs, protein, fat, category, false);
                                products.add(productObj);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        lv.setAdapter(new ProductListAdapter(getApplicationContext(), products ));
                        DialogControl.hideDialog(pDialog);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("AddMeal", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                DialogControl.hideDialog(pDialog);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    public void AddProducts() {
        g.setProductsForAMeal(checked);
        Intent intent = new Intent(this, SetMealDetails.class);
        startActivity(intent);
    }

}
