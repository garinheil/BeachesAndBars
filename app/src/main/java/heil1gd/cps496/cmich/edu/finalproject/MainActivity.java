package heil1gd.cps496.cmich.edu.finalproject;

/*
 *
 * Authors: Garin Heil & Cameron Stoel
 *
 * The MainActivity of the app. From here, the user can either go directly to navigation
 * to nearby beaches or bars, or view them and save them to a list for later.
 *
 *
 */

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import java.util.ArrayList;

// MainActivity class
public class MainActivity extends AppCompatActivity {
    private final static int MY_PERMISSION_FINE_LOCATION = 1;
    private final static int PLACE_PICKER_REQUEST = 101;

    ImageButton getBeachBtn,getBarBtn, barFavBtn, beachFavBtn, getFavMap;

    ArrayList<Favorites> beachFavsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //set the variables to buttons
        getBeachBtn = findViewById(R.id.imgBtnBeaches);
        getBarBtn = findViewById(R.id.imgBtnBars);
        barFavBtn = findViewById(R.id.imgBtnBarFavs);
        beachFavBtn = findViewById(R.id.imgBtnBeachFavs);
        getFavMap = findViewById(R.id.imgBtnSaveFavs);

        requestPermissions();

        beachFavsList = new ArrayList<>();

        // Button takes the user to the Google Maps API showing nearby beaches
        getBeachBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=beach near me");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, PLACE_PICKER_REQUEST);
            }
        });

        // Button takes the user to the Beach Favorites ListView Activity
        beachFavBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent beachFavsIntent = new Intent(MainActivity.this, BeachFavsActivity.class);
                startActivity(beachFavsIntent);
            }
        });

        // Button takes the user to the Google Maps API showing nearby bars
        getBarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=bars near me");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, PLACE_PICKER_REQUEST);
            }
        });

        // Button takes the user to the Bar Favorites ListView Activity
        barFavBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent barFavsIntent = new Intent(MainActivity.this, BarFavsActivity.class);
                startActivity(barFavsIntent);
            }
        });

        // Button takes the user to the Favorite activity where they can add favorites
        getFavMap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent favPlaces = new Intent(MainActivity.this,MapsActivity.class);
                startActivity(favPlaces);
            }
        });
    }

    private void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSION_FINE_LOCATION);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case MY_PERMISSION_FINE_LOCATION:
                if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(getApplicationContext(), "This app requires permissions to run.", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Favorites beachFav = new Favorites();
        if(requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, MainActivity.this);
                beachFav.setName((String) place.getName());
                Log.d("GetName", (String) place.getName());
                beachFav.setAddr((String) place.getAddress());
                Log.d("GetAddrs", (String) place.getAddress());
                if (place.getAttributions() == null) {
                   // webTxt.loadData("No attribution", "text/html; charset=utf-8", "UTF-8");
                } else {
                   // webTxt.loadData(place.getAttributions().toString(), "text/html; charset=utf-8", "UTF-8");
                }
            }
        }
    }
}
