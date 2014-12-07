package com.example.medicineApp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.medicineApp.R;
import com.example.medicineApp.objects.Meal;
import com.example.medicineApp.objects.Product;

import java.util.List;

/**
 * Created by Sabina on 2014-12-07.
 */
public class ProductListAdapter extends BaseAdapter {

    private Context context;
    private List<Product> products;

    public ProductListAdapter(Context context, List<Product> products) {
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
            convertView = infalInflater.inflate(R.layout.product_item, null);
        }

        final Product product = getItem(position);
        TextView name = (TextView) convertView.findViewById(R.id.product_name);
        CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkBox);
        ImageView img = (ImageView) convertView.findViewById(R.id.productImageView);
        if (product.getType().equals("Mięso")) {
            img.setImageResource(R.drawable.meat_small);
        } else {
            img.setImageResource(R.drawable.meal_small);
        }
        name.setText(product.getName());
        if (product.isChecked()) {
            checkbox.setChecked(true);
        } else {
            checkbox.setChecked(false);
        }

        return convertView;
    }

    public Product getItem(int position) {
        return products.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

}
