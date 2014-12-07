package com.example.medicineApp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import com.example.medicineApp.R;
import com.example.medicineApp.activities.AddMeal;
import com.example.medicineApp.adapter.MealListAdapter;
import com.example.medicineApp.helpers.Globals;

/**
 * Created by Sabina on 2014-12-05.
 */
public class UserMealsFragment extends Fragment implements View.OnClickListener {

    private Globals g;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        g = Globals.getInstance();
        View rootView = inflater.inflate(R.layout.list_layout_with_button, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.mealList);
        lv.setAdapter(new MealListAdapter(getActivity(), g.getMeals()));

        Button b = (Button) rootView.findViewById(R.id.addMealButton);
        b.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addMealButton:
                Intent intent = new Intent(getActivity(), AddMeal.class);
                startActivity(intent);
                getActivity().finish();
        }
    }

}
