package com.example.medicineApp.helpers;

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

import java.util.List;

/**
 * Created by Sabina on 2014-11-21.
 */
public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private List<Meal> meals;

    public CustomListAdapter(Context context, List<Meal> meals) {
        this.context = context;
        this.meals = meals;
    }

    public int getCount() {
        return meals.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.meal_item, null);
        }

        final Meal meal = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.meal_name);
        TextView date = (TextView) convertView.findViewById(R.id.meal_date);

        name.setText("Name: " + meal.getName());
        date.setText("Consumption Day: " + meal.getConsumptionDay());

        return convertView;
    }

    public Meal getItem(int position) {
        return meals.get(position);
    }

    public long getItemId(int position) {
        return position;
    }



}
