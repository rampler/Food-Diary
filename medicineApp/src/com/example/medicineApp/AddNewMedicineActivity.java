package com.example.medicineApp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by Sabina on 2014-11-20.
 */
public class AddNewMedicineActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_medicine);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        SetUpNumberPicker();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void AddNewMedicine(View view) {
        EditText editText = (EditText) findViewById(R.id.editMedicine);
        String medicineName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editCompany);
        String companyName = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editPrice);
        BigDecimal price = new BigDecimal(editText.getText().toString());

        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.editDosage);
        int dosage = numberPicker.getValue();

        Globals g = Globals.getInstance();

        //g.addMedicine(new Medicine(medicineName, companyName, dosage, price));

        Toast.makeText(getApplicationContext(), "New medicine added", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MyActivity.class);
        startActivity(intent);
    }

    private void SetUpNumberPicker() {
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.editDosage);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(10);
        numberPicker.setWrapSelectorWheel(true);
    }

}
