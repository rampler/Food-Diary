package com.example.medicineApp.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.RequestFuture;
import com.example.medicineApp.R;
import com.example.medicineApp.helpers.AppController;
import com.example.medicineApp.helpers.CustomListAdapter;
import com.example.medicineApp.helpers.Globals;
import com.example.medicineApp.objects.Meal;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sabina on 2014-12-05.
 */
public class UserMealsFragment extends ListFragment {

    private Globals g;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        g = Globals.getInstance();
        View rootView = inflater.inflate(R.layout.list_layout, container, false);
        setListAdapter(new CustomListAdapter(getActivity(), g.getMeals()));
        return rootView;
    }

}
