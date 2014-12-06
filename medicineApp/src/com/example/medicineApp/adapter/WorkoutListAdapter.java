package com.example.medicineApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.medicineApp.R;
import com.example.medicineApp.objects.Meal;
import com.example.medicineApp.objects.Product;
import com.example.medicineApp.objects.Workout;

import java.util.List;

/**
 * Created by Sabina on 2014-11-21.
 */
public class WorkoutListAdapter extends BaseAdapter {

    private Context context;
    private List<Workout> workouts;

    public WorkoutListAdapter(Context context, List<Workout> workouts) {
        this.context = context;
        this.workouts = workouts;
    }

    public int getCount() {
        return workouts.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.workout_item, null);
        }

        final Workout workout = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.workout_name);
        TextView date = (TextView) convertView.findViewById(R.id.workout_date);
        TextView repeats = (TextView) convertView.findViewById(R.id.workout_quantity);
        name.setText(workout.getExercise().getName());
        date.setText(workout.getWorkoutDate());
        repeats.setText(workout.getRepeats() + "  (" + workout.getExercise().getUnit() + ")");

        return convertView;
    }

    public Workout getItem(int position) {
        return workouts.get(position);
    }

    public long getItemId(int position) {
        return position;
    }



}
