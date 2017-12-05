package heil1gd.cps496.cmich.edu.readwritetest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView favListView;
    public int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favListView = findViewById(R.id.favoritesListView);
        favListView.setLongClickable(true);

        favListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int pos, long id) {
                position = pos;
                Log.d("Long click", "Position: " + pos);
                startActivityForResult(new Intent(getApplicationContext(), cmtsPopup.class), 999);
                return true;
            }
        });

        // Create some example beach favorites
        Favorites beachFav1In = new Favorites("Sandy Beach", "11/30/2017", "12345 Sandy Beach Dr Fruitport MI 49415", " ");
        Favorites beachFav2In = new Favorites("Crystal Beach", "12/5/2017", "66666 Crystal Beach Dr Hell MI 48666", "Sand was really hot!");

        Favorites barFav1In = new Favorites("Hip Bar", "6/6/6666", "666 Natas Ln Chicago IL 39555", "This place is so fetch.");

        ArrayList<String> test = new ArrayList<>();
        test.add(beachFav1In.toStringForDisplay());

        // Throw them into an arraylist
        ArrayList<Favorites> beachFavoritesArrayList = new ArrayList<>();
        ArrayList<String> beachFavListForDisplay = new ArrayList<>();
        beachFavoritesArrayList.add(beachFav1In);
        beachFavoritesArrayList.add(beachFav2In);
        beachFavListForDisplay.add(beachFav1In.toStringForDisplay());
        beachFavListForDisplay.add(beachFav2In.toStringForDisplay());

        ArrayList<Favorites> barFavoritesArrayList = new ArrayList<>();
        barFavoritesArrayList.add(barFav1In);

        // Declare the ReadWrite class constructor
        ReadWriteForProd readWrite = new ReadWriteForProd();

        // Write the arraylist to the file (would be done at app destruction)
        for(int i = 0; i < beachFavoritesArrayList.size(); i++)
            readWrite.writeToFile("beach", beachFavoritesArrayList.toString(), this);
        for(int i = 0; i < barFavoritesArrayList.size(); i++)
            readWrite.writeToFile("bar", barFavoritesArrayList.toString(), this);

        // Read the file into a string (would be done at app launch)
        // This string has brackets from when the arraylist was written to the file,
        // which probably could be avoided if the arraylist is written differently
        // Example: [Sandy Beach;11/30/2017;12345 Sandy Beach Dr Fruitport MI 49415;Love this beach! Will definitely have to go again.]
        String beachReadString = readWrite.readFromFile("beach", this);
        String barReadString = readWrite.readFromFile("bar", this);

        // Remove the brackets from the string that was read in
        // Sandy Beach;11/30/2017;12345 Sandy Beach Dr Fruitport MI 49415;Love this beach! Will definitely have to go again.
        String beachReadStringNoBrackets = beachReadString.substring(1, beachReadString.length() - 1);
        String barReadStringNoBrackets = barReadString.substring(1, barReadString.length() - 1);

        // Split the string into individual favorites
        // The favorites are separated by commas, so we split based on that
        String[] beachSplitArrayListString = beachReadStringNoBrackets.split(",");
        String[] barSplitArrayListString = barReadStringNoBrackets.split(",");

        // Clear the favorites for this example
        // in order to read into a fresh arraylist
        beachFavoritesArrayList.clear();
        //beachFavListForDisplay.clear();
        barFavoritesArrayList.clear();

        // Call method that places information in proper arraylist
        placeIntoArrayList(beachSplitArrayListString, beachFavoritesArrayList);
        placeIntoArrayList(barSplitArrayListString, barFavoritesArrayList);

        Log.d("beachFavs ArrayList", beachFavoritesArrayList.toString());
        Log.d("barFavs ArrayList", barFavoritesArrayList.toString());

        Log.d("Beach Read String", beachReadString);
        Log.d("Bar Read String", barReadString);

//        ArrayAdapter<Favorites> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, beachFavoritesArrayList);
//        favListView.setAdapter(arrayAdapter);
        ArrayAdapter<String> testAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, beachFavListForDisplay);
        favListView.setAdapter(testAdapter);
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
            arrayList.add(fav);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 999 && resultCode == RESULT_OK) {
            Log.d("Item", favListView.getItemAtPosition(position).toString() + data.getStringExtra("comment"));
        }
    }
}
