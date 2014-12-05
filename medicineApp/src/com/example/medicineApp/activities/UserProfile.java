package com.example.medicineApp.activities;

import android.app.*;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.medicineApp.adapter.TabsPagerAdapter;
import com.example.medicineApp.helpers.*;
import com.example.medicineApp.R;
import com.example.medicineApp.objects.Meal;
import com.example.medicineApp.objects.Product;
import com.example.medicineApp.objects.User;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import java.io.IOException;
import java.util.ArrayList;

public class UserProfile extends FragmentActivity implements ActionBar.TabListener {


    private static String TAG = UserProfile.class.getSimpleName();

    private ProgressDialog pDialog;

    private ListView mealListView;
    private CustomListAdapter listAdapter;
    private Globals g;

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Profile", "Meals", "Trainings" };


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_act);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        g = Globals.getInstance();

        if (!g.isHasProfile()) CreateNewProfile();
        else {

            GetUserProfile();
            GetUserMeals();

            viewPager = (ViewPager) findViewById(R.id.pager);
            actionBar = getActionBar();
            mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

            viewPager.setAdapter(mAdapter);
            actionBar.setHomeButtonEnabled(false);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

            for (String tab_name : tabs) {
                actionBar.addTab(actionBar.newTab().setText(tab_name)
                        .setTabListener(this));
            }


        }



        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    ListView lv = (ListView) findViewById(android.R.id.list);
                    lv.setAdapter(new CustomListAdapter(getApplicationContext(), g.getMeals()));
                } else if (position == 0 && g.getUser() != null) {
                    PrintProfileInfo();
                }
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (!g.isHasProfile())
            inflater.inflate(R.menu.new_user_actions, menu);
        else inflater.inflate(R.menu.user_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
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

    private void GetUserMeals() {
        String uri = String.format(g.getServerURL() + "/meal/getList?sessionId=%1$s", g.getSessionId());

        JsonArrayRequest req = new JsonArrayRequest(uri,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject meal = (JSONObject) response
                                        .get(i);

                                String id = meal.getString("id");
                                String name  = meal.getString("name");
                                String consumptionDay = meal.getString("consumptionDay");

                                if (g.getMeals().isEmpty()) {
                                    g.setMeals(new ArrayList<Meal>());
                                }

                                g.addMeal(new Meal(id, name, consumptionDay));
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }

    private void CreateNewProfile() {
        Intent intent = new Intent(getApplicationContext(), CreatingUserProfile.class);
        startActivity(intent);
        finish();
    }

    private void PrintProfileInfo() {
        TextView text;
        text = (TextView) findViewById(R.id.profile_nameEdit);
        text.setText(g.getUser().getFirstName());
        text = (TextView) findViewById(R.id.profile_surnameEdit);
        text.setText(g.getUser().getLastName());
        text = (TextView) findViewById(R.id.profile_weightEdit);
        text.setText(g.getUser().getWeight());
        text = (TextView) findViewById(R.id.profile_ageEdit);
        text.setText(g.getUser().getAge());
        text = (TextView) findViewById(R.id.profile_caloriesEdit);
        text.setText(g.getUser().getCalories());
    }

    private void GetUserProfile() {
        CreateAndShowDialog();

        String uri = String.format(g.getServerURL() + "/profile/get?sessionId=%1$s", g.getSessionId());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {
                                                                        
                                String id = response.getString("id");
                                User user = new User(response.getString("firstName"),
                                        response.getString("lastName"),
                                        response.getString("weight"),
                                        response.getString("age"),
                                        response.getString("caloriesCounter"));

                                g.setUser(user);
                                DialogControl.hideDialog(pDialog);
                                PrintProfileInfo();
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
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        DialogControl.hideDialog(pDialog);
        AppController.getInstance().addToRequestQueue(req);
    }

    private void CreateAndShowDialog() {
        pDialog = new ProgressDialog(this);
        DialogControl.configureNotCancellable(pDialog);
        DialogControl.showDialog(pDialog);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 1) {
            ListView lv = (ListView) findViewById(android.R.id.list);
            lv.setAdapter(new CustomListAdapter(getApplicationContext(), g.getMeals()));
            viewPager.setCurrentItem(tab.getPosition());
        } else if (tab.getPosition() == 0 && g.getUser() != null) {
            viewPager.setCurrentItem(tab.getPosition());
            PrintProfileInfo();
        } else if (tab.getPosition() == 2) {
            viewPager.setCurrentItem(tab.getPosition());
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
        if (tab.getPosition() == 0) {
            PrintProfileInfo();
        }
    }


}
