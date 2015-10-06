package com.example.adria.thegang.map;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.example.adria.thegang.model.POI;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import com.example.adria.thegang.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private LocationManager mLocationManager;

    private MyLocationListener mLocationListener;

    private boolean mDataAvailable;

    private double mLatitude;

    private double mLongitude;

    private Button button;

    private Button buttonPlacePicker;

    private SupportMapFragment mapFragment;

    private OnMapReadyCallback mOnMapReadyCallback;

    private Activity activity;

    private int PLACE_PICKER_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        activity = this;

        mOnMapReadyCallback = this;

        button = (Button) findViewById(R.id.button);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(mOnMapReadyCallback);

            }
        });

        buttonPlacePicker = (Button) findViewById(R.id.button_place_picker);
        buttonPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                try {
                    startActivityForResult(builder.build(activity), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        mLocationListener = new MyLocationListener();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                POI poi = new POI();
                poi.setName(place.getName().toString());
                poi.setLatitude(place.getLatLng().latitude);
                poi.setLongitude(place.getLatLng().longitude);


                Log.d("THEGANGPOI", poi.getName()+" "+poi.getLatitude()+""+poi.getLongitude());

                String toastMsg = String.format("Place: %s", place.getName());
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
            }
        }
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        mMap = googleMap;

        mMap.clear();

        Log.d("longitudeRECEIVED", "" + mLongitude);
        Log.d("latitudeRECEIVED", "" + mLatitude);

        LatLng myLocation = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(myLocation).title("Current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.5f));

    }

    protected void onResume() {
        super.onResume();
        if ((android.provider.Settings.Secure.LOCATION_MODE == "LOCATION_MODE_HIGH_ACCURACY")||(android.provider.Settings.Secure.LOCATION_MODE=="LOCATION_MODE_SENSORS_ONLY")) {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, mLocationListener);
        }else if (android.provider.Settings.Secure.LOCATION_MODE == "LOCATION_MODE_BATTERY_SAVING")
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 10, mLocationListener);
        if (mapFragment != null) mapFragment.getMapAsync(mOnMapReadyCallback);
    }

    ;

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(mLocationListener);
    }

    private class MyLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {

            if (!mDataAvailable) {
                mDataAvailable = true;
                button.setEnabled(true);
            }

            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            Log.d("longitudeREAD", "" + mLongitude);
            Log.d("latitudeREAD", "" + mLatitude);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}


