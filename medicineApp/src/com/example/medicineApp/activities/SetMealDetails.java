package com.example.medicineApp.activities;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import com.example.medicineApp.R;
import com.example.medicineApp.helpers.Globals;

/**
 * Created by Sabina on 2014-12-07.
 */
public class SetMealDetails extends Activity {

    private Globals g = Globals.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_layout);

        TextView num = (TextView) findViewById(R.id.numOfProducts);
        num.setText(Integer.toString(g.getProductsForAMeal().size()));
    }

}
