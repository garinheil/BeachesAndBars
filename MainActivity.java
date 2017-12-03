package heil1gd.cps496.cmich.edu.readwritetest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create some example beach favorites
        Favorites beachFav1In = new Favorites("Sandy Beach", "11/30/2017", "12345 Sandy Beach Dr Fruitport MI 49415", "Love this beach! Will definitely have to go again.");
        Favorites beachFav2In = new Favorites("Crystal Beach", "12/5/2017", "66666 Crystal Beach Dr Hell MI 48666", "Sand was really hot!");

        Favorites barFav1In = new Favorites("Hip Bar", "6/6/6666", "666 Natas Ln Chicago IL 39555", "This place is so fetch.");

        // Throw them into an arraylist
        ArrayList<Favorites> beachFavoritesArrayList = new ArrayList<>();
        beachFavoritesArrayList.add(beachFav1In);
        beachFavoritesArrayList.add(beachFav2In);
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
        barFavoritesArrayList.clear();

        // Call method that places information in proper arraylist
        placeIntoArrayList(beachSplitArrayListString, beachFavoritesArrayList);
        placeIntoArrayList(barSplitArrayListString, barFavoritesArrayList);

        Log.d("beachFavs ArrayList", beachFavoritesArrayList.toString());
        Log.d("barFavs ArrayList", barFavoritesArrayList.toString());

        Log.d("Beach Read String", beachReadString);
        Log.d("Bar Read String", barReadString);
    }

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
}
