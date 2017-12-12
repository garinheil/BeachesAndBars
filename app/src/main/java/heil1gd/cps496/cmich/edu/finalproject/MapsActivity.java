package heil1gd.cps496.cmich.edu.finalproject;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 *
 * MapsActivity allows a user to display nearby beaches or bars, and then if
 * they so desire, add them to their favorites list.
 *
 */


// MapsActivity class
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private GoogleMap mMap;
    private GoogleApiClient client;
    private LocationRequest locationRequest;
    private Location lastlocation;
    private Marker currentLocationmMarker;
    public static final int REQUEST_LOCATION_CODE = 99;
    int PROXIMITY_RADIUS = 10000;
    double latitude,longitude;
    public TextView tvPlaceMarker;
    public String favType, favName, favAddrs;
    public Favorites beachFav;
    public Favorites barFav;
    public static ArrayList<Favorites> beachFavArrayList = new ArrayList<>();
    public static ArrayList<Favorites> barFavArrayList = new ArrayList<>();
    public static ArrayList<String> beachFavDisplay = new ArrayList<>();
    public static ArrayList<String> barFavDisplay = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        tvPlaceMarker = findViewById(R.id.tv_PinDetail);
        tvPlaceMarker.setText(getString(R.string.nameAddrPH));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkLocationPermission();

        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode)
        {
            case REQUEST_LOCATION_CODE:
                if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=  PackageManager.PERMISSION_GRANTED)
                    {
                        if(client == null)
                        {
                            bulidGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                }
                else
                {
                    Toast.makeText(this,"Permission Denied" , Toast.LENGTH_LONG).show();
                }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            bulidGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }


    protected synchronized void bulidGoogleApiClient() {
        client = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        client.connect();

    }


    // Get the user's location when they request beaches or bars
    @Override
    public void onLocationChanged(Location location) {
        //get the current location of the user
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        lastlocation = location;
        //remove location if not null
        if(currentLocationmMarker != null)
        {
            currentLocationmMarker.remove();

        }
        Log.d("lat = ",""+latitude);
        //set lat and long and set the current location
        LatLng latLng = new LatLng(location.getLatitude() , location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        //color each marker blue
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
        // move the map to the locations and zoom
        currentLocationmMarker = mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(10));


    }

    // Called when the user clicks one of three buttons:
    // B_bars, B_beaches, or btn_AddFav
    public void onClick(View v)
    {
        Object dataTransfer[] = new Object[2];
        GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();


        switch(v.getId())
        {
            case R.id.B_bars: // Case when a user requests nearby bars
                mMap.clear();
                String bars = "bar";
                favType = bars;
                String url = getUrl(latitude, longitude, bars);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Bars", Toast.LENGTH_SHORT).show();
                break;
            case R.id.B_beaches: // Case when a user requests nearby bars
                mMap.clear();
                String beach = "beach";
                favType = beach;
                url = getUrl(latitude, longitude, beach);
                dataTransfer[0] = mMap;
                dataTransfer[1] = url;
                getNearbyPlacesData.execute(dataTransfer);
                Toast.makeText(MapsActivity.this, "Showing Nearby Beaches", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_AddFav: // Case when a user wants to add a location to his/her favorites list
                Date date = new Date();
                ReadWrite readWrite = new ReadWrite();
                String favDate = DateFormat.getDateInstance(DateFormat.SHORT).format(date);
                String favCmt = "Default comment.";
                if(favType.equals("beach")) {
                    beachFav = new Favorites(favName, favDate, favAddrs, favCmt);
                    Log.d("BeachFavorite added", beachFav.toString());
                    beachFavArrayList.add(beachFav);
                    beachFavDisplay.add(beachFav.toStringForDisplay());
                    for(int i = 0; i < beachFavArrayList.size(); i++)
                        readWrite.writeToFile("beach", beachFavArrayList.toString(), this);
                } else if(favType.equals("bar")) {
                    barFav = new Favorites(favName, favDate, favAddrs, favCmt);
                    Log.d("BarFavorite added", barFav.toString());
                    barFavArrayList.add(barFav);
                    barFavDisplay.add(barFav.toStringForDisplay());
                    for(int i = 0; i < barFavArrayList.size(); i++)
                        readWrite.writeToFile("bar", barFavArrayList.toString(), this);
                }
                Toast.makeText(MapsActivity.this, "Favorite Added to List", Toast.LENGTH_SHORT).show();
                break;

        }

        // ClickListener that checks for actions on the map markers
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //format the response and eliminate the comma
                String[] favInfo = marker.getTitle().split(":");
                favName = favInfo[0].trim();
                favAddrs = favInfo[1].trim();
                String[] delComma = favAddrs.split(",");
                String before = delComma[0];
                String after = delComma[1];
                favAddrs = before + after;
                Log.d(("favName"), favName);
                Log.d(("favAddrs"), favAddrs);

                Log.d("MarkerTitle", marker.getTitle());

                tvPlaceMarker.setText(marker.getTitle());

                return true;
            }
        });
    }


    private String getUrl(double latitude , double longitude , String nearbyPlace)
    {

        StringBuilder googlePlaceUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+"43.5978"+","+"-84.7675");
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&key="+"AIzaSyCehaMJbymeGOzdOjfs-ycB-p9ILEwOaOM");

        Log.d("MapsActivity", "url = "+googlePlaceUrl.toString());

        return googlePlaceUrl.toString();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED)
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(client, locationRequest, this);
        }
    }


    public boolean checkLocationPermission()
    {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)  != PackageManager.PERMISSION_GRANTED )
        {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
            {
                ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[] {android.Manifest.permission.ACCESS_FINE_LOCATION },REQUEST_LOCATION_CODE);
            }
            return false;

        }
        else
            return true;
    }


    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }


}