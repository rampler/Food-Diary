package com.example.medicineApp.helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.medicineApp.R;
import com.example.medicineApp.objects.Product;

import java.util.List;

/**
 * Created by Sabina on 2014-11-21.
 */
public class CustomListAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public CustomListAdapter(Context context, List<Product> products) {
        this.context = context;
        this.products = products;
    }

    public int getCount() {
        return products.size();
    }

    public View getView(final int position, View convertView, ViewGroup parent) {


        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        final Product product = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView calories = (TextView) convertView.findViewById(R.id.calories);
        ImageView img = (ImageView) convertView.findViewById(R.id.imageView);

        if (product.getType().equals("MEAT")) {
            img.setImageResource(R.drawable.meat);
        }
        name.setText("Name: " + product.getName());
        calories.setText("Calories: " + product.getCalories());

        return convertView;
    }

    public Product getItem(int position) {
        return products.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

}
