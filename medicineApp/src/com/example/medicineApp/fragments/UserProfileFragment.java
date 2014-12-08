package com.example.medicineApp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.medicineApp.R;
import com.example.medicineApp.helpers.Globals;
import org.w3c.dom.Text;

/**
 * Created by Sabina on 2014-12-05.
 */
public class UserProfileFragment extends Fragment {

    private Globals g = Globals.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.profile_grid_layout, container, false);
        if (g.getUser() != null) PrintProfileInfo(rootView);
        return rootView;
    }

    private void PrintProfileInfo(View rootView) {
        TextView text;
        do {
            text = (TextView)rootView.findViewById(R.id.profile_nameEdit);
        } while (text == null);
        text.setText(g.getUser().getFirstName());
        text = (TextView) rootView.findViewById(R.id.profile_surnameEdit);
        text.setText(g.getUser().getLastName());
        text = (TextView) rootView.findViewById(R.id.profile_weightEdit);
        text.setText(g.getUser().getWeight());
        text = (TextView) rootView.findViewById(R.id.profile_ageEdit);
        text.setText(g.getUser().getAge());
        text = (TextView) rootView.findViewById(R.id.profile_caloriesEdit);
        text.setText(g.getUser().getCalories());
        text = (TextView) rootView.findViewById(R.id.textView4);
        text.setText(g.getCounters()[0]);
        text = (TextView) rootView.findViewById(R.id.textView9);
        text.setText(g.getCounters()[1]);
    }
}
