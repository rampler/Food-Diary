package com.example.medicineApp.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.medicineApp.R;
import com.example.medicineApp.adapter.MealListAdapter;
import com.example.medicineApp.helpers.Globals;

/**
 * Created by Sabina on 2014-12-05.
 */
public class UserMealsFragment extends ListFragment {

    private Globals g;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        g = Globals.getInstance();
        View rootView = inflater.inflate(R.layout.meal_list_layout, container, false);
        setListAdapter(new MealListAdapter(getActivity(), g.getMeals()));
        return rootView;
    }

}
