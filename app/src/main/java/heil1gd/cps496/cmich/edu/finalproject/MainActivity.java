package heil1gd.cps496.cmich.edu.finalproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgBtnBeaches:
                Intent beachMapIntent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(beachMapIntent);
                break;
            case R.id.imgBtnBeachFavs:
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
