package heil1gd.cps496.cmich.edu.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class BarFavsActivity extends Activity implements AdapterView.OnItemClickListener {

    ListView listFavorites;
    TextView header;
    String[] bar_favorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        header = findViewById(R.id.textFavsHeader);
        header.setText(getString(R.string.bar_favs_header));


        ReadWrite readWrite = new ReadWrite();
        String beachString = readWrite.readFromFile("beach", this);
        String beachReadStringNoBrackets = beachString.substring(1, beachString.length() - 1);
        String[] beachSplitArrayListString = beachReadStringNoBrackets.split(",");

        placeIntoArrayList(beachSplitArrayListString, MapsActivity.beachFavArrayList);

        bar_favorites = getResources().getStringArray(R.array.bar_favorites);
        listFavorites = findViewById(R.id.listFavorites);
        ArrayAdapter<Favorites> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, MapsActivity.barFavArrayList);
        listFavorites.setAdapter(arrayAdapter);
        listFavorites.setOnItemClickListener(this);

        listFavorites.setLongClickable(true);

        listFavorites.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                startActivityForResult(new Intent(getApplicationContext(), cmtsPopup.class), 999);
                return true;
            }
        });

    }

    // Generic method that places favorites into the proper arraylists
    private void placeIntoArrayList(String [] splitString, ArrayList<Favorites> arrayList) {
        for(int i = 0; i < splitString.length; i++) {
            // Trim any leading or trailing whitespace from the string
            String favString = splitString[i].trim();

            // Split the favorite based a ';' since the string is delimited by that
            String[] favSplit = favString.split(";");

            // Parse the split data into their respective variables
            String name = favSplit[0];
            String date = favSplit[1];
            String addrs = favSplit[2];
            String cmts = favSplit[3];

            // Add the information to an individual favorite
            Favorites fav = new Favorites(name, date, addrs, cmts);

            // Add the favorite to the Favorites ArrayList
            MapsActivity.barFavArrayList.add(fav);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 999 && resultCode == RESULT_OK) {
            Log.d("Item",  data.getStringExtra("comment"));

        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
