package heil1gd.cps496.cmich.edu.finalproject;

/*
 *
 * Authors: Garin Heil & Cameron Stoel
 *
 *
 *
 */

import android.*;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final static int MY_PERMISSION_FINE_LOCATION = 1;
    private final static int PLACE_PICKER_REQUEST = 101;

    private final static LatLngBounds bounds = new LatLngBounds(new LatLng(41.79,-87.03),(new LatLng(45.6,-84.30)));
    ImageButton getBeachBtn,getBarBtn, barFavBtn, beachFavBtn;

    ArrayList<Favorites> beachFavsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getBeachBtn = (ImageButton) findViewById(R.id.imgBtnBeaches);
        getBarBtn = (ImageButton) findViewById(R.id.imgBtnBars);
        barFavBtn = (ImageButton) findViewById(R.id.imgBtnBarFavs);
        beachFavBtn = (ImageButton) findViewById(R.id.imgBtnBeachFavs);
        requestPermissions();

        beachFavsList = new ArrayList<>();

        getBeachBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try{
                    Intent intent = builder.build(MainActivity.this);
                    builder.setLatLngBounds(bounds);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

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
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try{
                    Intent intent = builder.build(MainActivity.this);
                    builder.setLatLngBounds(bounds);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });
        barFavBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent barFavsIntent = new Intent(MainActivity.this, BarFavsActivity.class);
                startActivity(barFavsIntent);

            }
        });

                }




    /*
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnBeaches:
               // Intent beachMapIntent = new Intent(MainActivity.this, MapsActivity.class);
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try{
                    Intent intent = builder.build(MainActivity.this);
                    builder.setLatLngBounds(bounds);
                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                //startActivity(beachMapIntent);
                break;
            case R.id.imgBtnBeachFavs:
                Favorites beachFav = new Favorites();
                beachFav.setName("Beach 1");
                beachFav.setDate("11/20/2017");
                beachFav.setAddr("1234 Beach Ave, Beach City MI, 54321");
                beachFav.setCmts("Love this beach! Will definitely go again and bring the whole family!");
                beachFavsList.add(beachFav);
                beachFavsList.get(0);
                Log.d("Beach 0", beachFavsList.get(0).toString());
//                Log.d("Date", beachFav.getDate());
//                Log.d("Beach address", beachFav.getAddr());
//                Log.d("Personal Comments", beachFav.getCmts());
                Intent beachFavsIntent = new Intent(MainActivity.this, BeachFavsActivity.class);
                startActivity(beachFavsIntent);
                break;
            case R.id.imgBtnBars:
                Intent barMapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(barMapIntent);
                break;
            case R.id.imgBtnBarFavs:
                Intent barFavsIntent = new Intent(MainActivity.this, BarFavsActivity.class);
                startActivity(barFavsIntent);
                break;
        }
    }
    */

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
                Log.d("GetAddr", (String) place.getAddress());

                if (place.getAttributions() == null) {
                   // webTxt.loadData("No attribution", "text/html; charset=utf-8", "UTF-8");
                } else {
                   // webTxt.loadData(place.getAttributions().toString(), "text/html; charset=utf-8", "UTF-8");
                }
            }
        }
    }
}
