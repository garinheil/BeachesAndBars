package heil1gd.cps496.cmich.edu.finalproject;

/*
 *
 * Authors: Garin Heil & Cameron Stoel
 *
 *
 *
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Favorites> beachFavsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        beachFavsList = new ArrayList<>();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnBeaches:
                Intent beachMapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(beachMapIntent);
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
}
