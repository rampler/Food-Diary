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
import com.example.medicineApp.adapter.MealListAdapter;
import com.example.medicineApp.adapter.TabsPagerAdapter;
import com.example.medicineApp.adapter.WorkoutListAdapter;
import com.example.medicineApp.helpers.*;
import com.example.medicineApp.R;
import com.example.medicineApp.objects.Exercise;
import com.example.medicineApp.objects.Meal;
import com.example.medicineApp.objects.User;
import com.example.medicineApp.objects.Workout;
import com.example.medicineApp.utils.DateHandler;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UserProfile extends FragmentActivity implements ActionBar.TabListener {


    private static String TAG = UserProfile.class.getSimpleName();

    private ProgressDialog pDialog;
    private ProgressDialog workoutsDialog;

    private ListView mealListView;
    private MealListAdapter listAdapter;
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


            workoutsDialog = new ProgressDialog(this);
            DialogControl.configureNotCancellable(workoutsDialog);
            DialogControl.showDialog(workoutsDialog);
            GetUserStats();
            GetUserProfile();
            GetUserMeals();
            GetWorkouts();

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


            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    if (position == 1) {
                        actionBar.setSelectedNavigationItem(position);
                        ListView lv = (ListView) findViewById(R.id.mealList);
                        lv.setAdapter(new MealListAdapter(getApplicationContext(), g.getMeals()));
                    } else if (position == 0 && g.getUser() != null) {

                    } else if (position == 2) {
                        actionBar.setSelectedNavigationItem(position);
                        ListView lv = (ListView) findViewById(R.id.workoutList);
                        lv.setAdapter(new WorkoutListAdapter(getApplicationContext(), g.getWorkouts()));
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
                        g.clear();
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
        String uri = String.format(g.getServerURL() + "/meal/getList?sessionId=%1$s&consumptionDay=%2$s",
                g.getSessionId(), DateHandler.GetTodaysDate());

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

                                Meal mealObj = new Meal(id, name, consumptionDay);

                                if (!g.getMeals().contains(mealObj)) g.addMeal(mealObj);
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
        text = (TextView) findViewById(R.id.textView4);
        text.setText(g.getCounters()[0]);
        text = (TextView) findViewById(R.id.textView9);
        text.setText(g.getCounters()[1]);
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

            ListView lv = (ListView) findViewById(R.id.mealList);
            lv.setAdapter(new MealListAdapter(getApplicationContext(), g.getMeals()));
            viewPager.setCurrentItem(tab.getPosition());
        } else if (tab.getPosition() == 0 && g.getUser() != null) {
            viewPager.setCurrentItem(tab.getPosition());
        } else if (tab.getPosition() == 2) {

            viewPager.setCurrentItem(tab.getPosition());
            ListView lv = (ListView) findViewById(R.id.workoutList);
            lv.setAdapter(new WorkoutListAdapter(getApplicationContext(), g.getWorkouts()));
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    private void GetWorkouts() {
        String uri = String.format(g.getServerURL() + "/workout/getList?sessionId=%1$s&workoutDate=%2$s",
                g.getSessionId(), DateHandler.GetTodaysDate());

        JsonArrayRequest req = new JsonArrayRequest(uri,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject workout = (JSONObject) response
                                        .get(i);

                                String id = workout.getString("id");
                                String excerciseId = workout.getString("exercise");
                                String repeats = workout.getString("repeats");
                                String quantity = workout.getString("quantity");
                                String workoutDate = workout.getString("workoutDate");

                                if (g.getWorkouts().isEmpty()) {
                                    g.setWorkouts(new ArrayList<Workout>());
                                }

                                Workout workoutObj = new Workout(id, excerciseId, repeats, quantity, workoutDate);
                                g.addWorkout(workoutObj);
                                getWorkoutDetails(workoutObj);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }

                        DialogControl.hideDialog(workoutsDialog);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                DialogControl.hideDialog(workoutsDialog);
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(req);
    }


    public void getWorkoutDetails(final Workout workout) {
        String uri = String.format(g.getServerURL() + "/exercise/get?id=%1$s", workout.getExerciseId());
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                Exercise exercise = new Exercise(response.getString("id"),
                                        response.getString("name"),
                                        response.getString("unit"));

                                workout.setExercise(exercise);
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

    private void GetUserStats() {
        String date = DateHandler.GetTodaysDate();
        String uri = String.format(g.getServerURL() + "/statistics/counters?sessionId=%1$s&date=%2$s", g.getSessionId(), date);
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, uri, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                        try {
                            for (int i = 0; i < response.length(); i++) {

                                g.updateCounter(0, response.getString("meals"));
                                g.updateCounter(1, response.getString("workouts"));

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
        AppController.getInstance().addToRequestQueue(req);
    }


}
