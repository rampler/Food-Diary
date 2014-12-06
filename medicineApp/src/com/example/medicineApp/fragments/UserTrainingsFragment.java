package com.example.medicineApp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.medicineApp.R;
import com.example.medicineApp.adapter.MealListAdapter;
import com.example.medicineApp.adapter.WorkoutListAdapter;
import com.example.medicineApp.helpers.Globals;

/**
 * Created by Sabina on 2014-12-05.
 */
public class UserTrainingsFragment extends Fragment {

    private Globals g;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        g = Globals.getInstance();
        View rootView = inflater.inflate(R.layout.workout_list_layout, container, false);
        ListView lv = (ListView) rootView.findViewById(R.id.workoutList);
        lv.setAdapter(new WorkoutListAdapter(getActivity(), g.getWorkouts()));
        return rootView;
    }


}
