package com.example.medicineApp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.example.medicineApp.adapter.MealListAdapter;
import com.example.medicineApp.helpers.Globals;

public class MyActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.example.medicineApp.MESSAGE";
    MealListAdapter listAdapter;
    ListView listView;
    Globals g;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.medicineListView);
        /*SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        Set<String> set = sharedPref.getStringSet("headers", new HashSet<String>());
        g = Globals.getInstance();*/
        //g.setListDataHeader(new ArrayList<String>(set));

        // preparing list data
        //prepareListData();
        /*try {
            recreateLists();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        //listAdapter = new CustomListAdapter(this, g.getMedicines());

        // setting list adapter
        listView.setAdapter(listAdapter);
    }

    public void sendMessage(View view) {
        TextView textView = (TextView) findViewById(R.id.textCheck);
        textView.setText("Txt");
                /*Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_user_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        /*switch (item.getItemId()) {
            case R.id.action_search:
                //openSearch();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            case R.id.action_add:
                addNew();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*@Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("medicineHeaderList", g.getListDataHeader());
        savedInstanceState.putSerializable("medicineHashMap", g.getListDataChild());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Globals g = Globals.getInstance();
        g.setListDataHeader(savedInstanceState.getStringArrayList("medicineHeaderList"));
        g.setListDataChild((HashMap<String, Medicine>) savedInstanceState.getSerializable("medicineHashMap"));
    }*/


    /*private void recreateLists() throws IOException, ClassNotFoundException {
        SharedPreferences prefs = getSharedPreferences(PREFERENCE_FILE, 0);
        Set<String> set = prefs.getStringSet("headers", new HashSet<String>());
        g = Globals.getInstance();
        g.setListDataHeader(new ArrayList<String>(set));

        File file = new File(getFilesDir(), "map");
        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file));
        HashMap<String, Medicine> map = (HashMap<String, Medicine>) inputStream.readObject();
        inputStream.close();
        g.setListDataChild(map);
    }*/

}

