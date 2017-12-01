package heil1gd.cps496.cmich.edu.finalproject;

/*
 *
 * Authors: Garin Heil & Cameron Stoel
 *
 *
 *
 */

import android.*;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.awareness.snapshot.PlacesResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Response;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.nearby.connection.Payload;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private final static int MY_PERMISSION_FINE_LOCATION = 1;
    private final static int PLACE_PICKER_REQUEST = 101;

    private final static LatLngBounds bounds = new LatLngBounds(new LatLng(41.79,-87.03),(new LatLng(45.6,-84.30)));

    ImageButton getBeachBtn,getBarBtn, barFavBtn, beachFavBtn;
    Button getFavMap;

    ArrayList<Favorites> beachFavsList;
    protected GeoDataClient mGeoDataClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBeachBtn = (ImageButton) findViewById(R.id.imgBtnBeaches);
        getBarBtn = (ImageButton) findViewById(R.id.imgBtnBars);
        barFavBtn = (ImageButton) findViewById(R.id.imgBtnBarFavs);
        beachFavBtn = (ImageButton) findViewById(R.id.imgBtnBeachFavs);
        getFavMap = (Button) findViewById(R.id.btnSaveforLater);
        requestPermissions();


        beachFavsList = new ArrayList<>();

        getBeachBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=beach");


                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, PLACE_PICKER_REQUEST);

               // Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
               // mapIntent.setPackage("com.google.android.apps.maps");
               // startActivityForResult(mapIntent, PLACE_PICKER_REQUEST);


            }
        });
        beachFavBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Favorites beachFav = new Favorites();
                beachFav.setName("Beach 1");
                beachFav.setDate("11/20/2017");
                beachFav.setAddr("1234 Beach Ave, Beach City MI, 54321");
                beachFav.setCmts("Love this beach! Will definitely go again and bring the whole family!");
                beachFavsList.add(beachFav);
                beachFavsList.get(0);
                Log.d("Beach 0", beachFavsList.get(0).toString());
                Intent beachFavsIntent = new Intent(MainActivity.this, BeachFavsActivity.class);
                startActivity(beachFavsIntent);
                //Log.d("Date", beachFav.getDate());
//                Log.d("Beach address", beachFav.getAddr());
//                Log.d("Personal Comments", beachFav.getCmts());

            }
        });

        getBarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Uri gmmIntentUri = Uri.parse("geo:0,0?q=bars");


                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivityForResult(mapIntent, PLACE_PICKER_REQUEST);

            }
        });
        barFavBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent barFavsIntent = new Intent(MainActivity.this, BarFavsActivity.class);
                startActivity(barFavsIntent);

            }
        });

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
