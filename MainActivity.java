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
        Favorites fav1In = new Favorites("Sandy Beach", "11/30/2017", "12345 Sandy Beach Dr Fruitport MI 49415", "Love this beach! Will definitely have to go again.");
        Favorites fav2In = new Favorites("Crystal Beach", "12/5/2017", "66666 Crystal Beach Dr Hell MI 48666", "Sand was really hot!");

        // Throw them into an arraylist
        ArrayList<Favorites> favoritesArrayList = new ArrayList<>();
        favoritesArrayList.add(fav1In);
        favoritesArrayList.add(fav2In);

        // Declare the ReadWrite class constructor
        ReadWriteForProd readWrite = new ReadWriteForProd();

        // Write the arraylist to the file (would be done at app destruction)
        for(int i = 0; i < favoritesArrayList.size(); i++)
            readWrite.writeToFile("beach", favoritesArrayList.toString(), this);

        // Read the file into a string (would be done at app launch)
        // This string has brackets from when the arraylist was written to the file,
        // which probably could be avoided if the arraylist is written differently
        // Example: [Sandy Beach;11/30/2017;12345 Sandy Beach Dr Fruitport MI 49415;Love this beach! Will definitely have to go again.]
        String readString = readWrite.readFromFile("beach", this);

        // Remove the brackets from the string that was read in
        // Sandy Beach;11/30/2017;12345 Sandy Beach Dr Fruitport MI 49415;Love this beach! Will definitely have to go again.
        String readStringNoBrackets = readString.substring(1, readString.length() - 1);

        // Split the string into individual favorites
        // The favorites are separated by commas, so we split based on that
        String[] splitArrayListString = readStringNoBrackets.split(",");

        // Clear the favorites for this example
        // in order to read into a fresh arraylist
        favoritesArrayList.clear();

        // Iterate through each favorite string
        for(int i = 0; i < splitArrayListString.length; i++) {

            // Trim any leading or trailing whitespace from the string
            String beachFavString = splitArrayListString[i].trim();

            // Split the favorite based a ';' since the string is delimited by that
            String[] beachFavSplit = beachFavString.split(";");

            // Parse the split data into their respective variables
            String name = beachFavSplit[0];
            String date = beachFavSplit[1];
            String addrs = beachFavSplit[2];
            String cmts = beachFavSplit[3];

            // Add the information to an individual favorite
            Favorites beachFav = new Favorites(name, date, addrs, cmts);

            // Add the favorite to the Favorites ArrayList
            favoritesArrayList.add(beachFav);
            Log.d("ArrayList After Read", favoritesArrayList.toString());
        }

        Log.d("Read String", readString);
    }
}
