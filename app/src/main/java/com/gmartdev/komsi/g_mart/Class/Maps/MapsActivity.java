package com.gmartdev.komsi.g_mart.Class.Maps;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.gmartdev.komsi.g_mart.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
//import com.here.android.mpa.common.GeoCoordinate;
//import com.here.android.mpa.common.OnEngineInitListener;
//import com.here.android.mpa.mapping.Map;
//import com.here.android.mpa.mapping.AndroidXMapFragment;

import java.io.File;
import java.text.DecimalFormat;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    //private Map map = null;
    //private AndroidXMapFragment mapFragment = null;

    double latitude, longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //initialize();
    }
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        latitude = Double.parseDouble(getIntent().getStringExtra("latitude"));
        longitude = Double.parseDouble(getIntent().getStringExtra("longitude"));

        Log.d(TAG, "onCreate: latitude " + latitude);
        Log.d(TAG, "onCreate: longitude " + longitude);

        LatLng kios = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(kios));
        float zoom=13;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(kios,zoom));

        GroundOverlayOptions kiosOverlay = new GroundOverlayOptions()
                .image(BitmapDescriptorFactory.fromResource(R.drawable.ic_store_name))
                .position(kios,100);
        mMap.addGroundOverlay(kiosOverlay);
    }

//    private void initialize() {
//        setContentView(R.layout.activity_maps);
//
//        // Search for the map fragment to finish setup by calling init().
//        mapFragment = (AndroidXMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapfragment);
//
//        // Set up disk cache path for the map service for this application
//        // It is recommended to use a path under your application folder for storing the disk cache
//        boolean success = com.here.android.mpa.common.MapSettings.setIsolatedDiskCacheRootPath(
//                getApplicationContext().getExternalFilesDir(null) + File.separator + ".here-maps");
//
//        if (!success) {
//            Toast.makeText(getApplicationContext(), "Unable to set isolated disk cache path.", Toast.LENGTH_LONG);
//        } else {
//            mapFragment.init(new OnEngineInitListener() {
//                @Override
//                public void onEngineInitializationCompleted(OnEngineInitListener.Error error) {
//                    if (error == OnEngineInitListener.Error.NONE) {
//                        // retrieve a reference of the map from the map fragment
//                        map = mapFragment.getMap();
//                        // Set the map center to the Vancouver region (no animation)
//                        map.setCenter(new GeoCoordinate(latitude, longitude, 0.0),
//                                Map.Animation.NONE);
//                        // Set the zoom level to the average between min and max
//                        map.setZoomLevel((map.getMaxZoomLevel() + map.getMinZoomLevel()) / 2);
//                    } else {
//                        System.out.println("ERROR: Cannot initialize Map Fragment");
//                    }
//                }
//            });
//        }
//    }

}
